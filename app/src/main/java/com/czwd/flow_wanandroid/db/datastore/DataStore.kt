package com.czwd.flow_wanandroid.db.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


val Context.cookieDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "cookie_store"
)

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_store"
)