package com.czwd.flow_wanandroid.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.db.datastore.cookieDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

object CookieDataStoreManager {



    private val dataStore: DataStore<Preferences>
        get() = FlowApplication.context.cookieDataStore

    private val memoryCache = ConcurrentHashMap<String, MutableSet<String>>()
    @Volatile
    private var loadedFromDisk = false

    private fun encodeCookie(cookie: Cookie): String {
        return listOf(
            cookie.name,
            cookie.value,
            cookie.domain,
            cookie.path,
            cookie.expiresAt.toString(),
            cookie.secure.toString(),
            cookie.httpOnly.toString()
        ).joinToString("||")
    }

    private fun decodeCookie(encoded: String): Cookie? {
        return try {
            val parts = encoded.split("||")
            if (parts.size < 7) return null
            Cookie.Builder()
                .name(parts[0])
                .value(parts[1])
                .domain(parts[2])
                .path(parts[3])
                .expiresAt(parts[4].toLong())
                .apply {
                    if (parts[5].toBoolean()) secure()
                    if (parts[6].toBoolean()) httpOnly()
                }
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun ensureLoaded() {
        if (loadedFromDisk) return
        synchronized(this) {
            if (loadedFromDisk) return
            runBlocking {
                dataStore.data.first().asMap().forEach { (key, value) ->
                    @Suppress("UNCHECKED_CAST")
                    memoryCache[key.name] = (value as Set<String>).toMutableSet()
                }
            }
            loadedFromDisk = true
        }
    }

    fun saveCookies(url: HttpUrl, cookies: List<Cookie>) {
        ensureLoaded()
        val host = url.host
        val cached = memoryCache.getOrPut(host) { mutableSetOf() }
        cookies.forEach { newCookie ->
            cached.removeAll { existing ->
                decodeCookie(existing)?.name == newCookie.name
            }
            cached.add(encodeCookie(newCookie))
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                dataStore.edit { prefs ->
                    prefs[stringSetPreferencesKey(host)] = cached
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCookies(url: HttpUrl): List<Cookie> {
        ensureLoaded()
        val encodedSet = memoryCache[url.host] ?: return emptyList()
        return encodedSet.mapNotNull { decodeCookie(it) }
    }

    fun clearCookies() {
        memoryCache.clear()
        loadedFromDisk = false
        GlobalScope.launch(Dispatchers.IO) {
            try {
                dataStore.edit { it.clear() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}