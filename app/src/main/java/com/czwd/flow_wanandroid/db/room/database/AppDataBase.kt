package com.czwd.flow_wanandroid.db.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.czwd.flow_wanandroid.db.room.dao.HistoryDao
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity

@Database(version = 1, entities = [HistoryEntity::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        private var instance : AppDataBase? = null

        fun getDataBase(context: Context) : AppDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "app_database")
                .build().apply { instance = this }
        }
    }
}