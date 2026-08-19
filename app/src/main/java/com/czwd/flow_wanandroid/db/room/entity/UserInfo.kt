package com.czwd.flow_wanandroid.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName : String,
    val userPassword : String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long=0
}