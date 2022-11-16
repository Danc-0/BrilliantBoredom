package com.danc.brilliantboredom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danc.brilliantboredom.data.local.dao.BoredomActivityDao
import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity

@Database(
    entities = [BoredomActivityEntity::class],
    version = 1
)
abstract class BoredActivityDatabase(): RoomDatabase() {

    abstract val boredomActivityDao: BoredomActivityDao

}