package com.czwd.flow_wanandroid.db.room.ext

import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.db.room.database.AppDataBase

val roomDb = AppDataBase.getDataBase(FlowApplication.context)
val roomDao = roomDb.historyDao()
