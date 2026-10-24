package com.czwd.flow_wanandroid.db.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.czwd.flow_wanandroid.FlowApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object UserInfoManager {
    private val ds get() = FlowApplication.context.userDataStore

    private val KEY_ID = intPreferencesKey("user_id")
    private val KEY_USERNAME= stringPreferencesKey("username")
    private val KEY_NICKNAME = stringPreferencesKey("nickname")
    private val KEY_EMAIL = stringPreferencesKey("email")
    private val KEY_ICON = stringPreferencesKey("icon")
    private val KEY_TOKEN = stringPreferencesKey("token")

   suspend fun saveUserInfo(
        id : Int ,
        username : String,
        nickname : String,
        email : String,
        icon : String,
        token : String
    ){
       ds.edit {
           it[KEY_ID] = id
           it[KEY_USERNAME] = username
           it[KEY_NICKNAME] = nickname
           it[KEY_EMAIL] = email
           it[KEY_ICON] = icon
           it[KEY_TOKEN] = token
       }
   }

    suspend fun clearUserInfo(){
        ds.edit { it.clear() }
    }

    val userInfoFlow : Flow<UserInfo?> = ds.data.map {
        val id = it[KEY_ID] ?: return@map null
        UserInfo(
            id = id,
            username = it[KEY_USERNAME] ?: "",
            nickname = it[KEY_NICKNAME] ?: "",
            email = it[KEY_EMAIL] ?: "",
            icon = it[KEY_ICON] ?: "",
            token = it[KEY_TOKEN] ?: ""
        )
    }
}
data class UserInfo(
    val id: Int,
    val username: String,
    val nickname: String,
    val email: String,
    val icon: String,
    val token: String
)