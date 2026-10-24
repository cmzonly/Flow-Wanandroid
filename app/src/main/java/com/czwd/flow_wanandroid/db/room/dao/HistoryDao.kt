package com.czwd.flow_wanandroid.db.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity

@Dao
interface HistoryDao {

    /**添加某条数据*/
    @Insert
    fun insert(history : HistoryEntity) : Long

    /**删除某条数据*/
    @Delete
    fun deleteHistoryEntity(historyEntity: HistoryEntity)

    /**删除所有数据*/
    @Query("DELETE FROM historyentity")
    fun deleteAll()

    /**查询所有数据*/
    @Query("SELECT * FROM historyentity")
    fun getAllHistory() : List<HistoryEntity>

    /**根据name删除某条数据*/
    @Query("DELETE FROM historyentity WHERE historyName = :name")
    fun deleteByName(name: String)
}