package com.danc.brilliantboredom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danc.brilliantboredom.data.local.dao.BoredomActivityDao
import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity

/**
 * Is a class responsible for HOLDING THE DATABASE
 * This class defines the DB configurations and serves as the main access point to the persisted Data
 * CONDITIONS TO SATISFY
 * 1. Must be annotated with the @Database which includes the entities as an array and also the version
 * 2. Must be an abstract class that extends the RoomDatabase
 * 3. For each DAO that is associated with this class it must be an abstract method that has no arguments and returns an instance of the DAO class */
@Database(
    entities = [BoredomActivityEntity::class],
    version = 1
)
abstract class BoredActivityDatabase(): RoomDatabase() {

    abstract val boredomActivityDao: BoredomActivityDao

}