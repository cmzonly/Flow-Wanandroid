package com.czwd.flow_wanandroid.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    val historyName : String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long=0
}