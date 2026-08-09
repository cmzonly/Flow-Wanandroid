package com.czwd.flow_wanandroid.db.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.czwd.flow_wanandroid.db.room.entity.User

@Dao
interface UserDao {

    @Insert
    fun insert(user : User) : Long

    @Delete
    fun deleteUser()
}