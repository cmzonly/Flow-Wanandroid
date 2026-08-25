package com.czwd.flow_wanandroid.db.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Insert
    fun insert(history : HistoryEntity) : Long

    @Delete
    fun deleteHistoryEntity(historyEntity: HistoryEntity)

    @Query("DELETE FROM historyentity")
    fun deleteAll()

    @Query("SELECT * FROM historyentity")
    fun getAllHistory() : List<HistoryEntity>
}