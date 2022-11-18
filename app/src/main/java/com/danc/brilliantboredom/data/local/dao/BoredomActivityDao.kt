package com.danc.brilliantboredom.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danc.brilliantboredom.data.local.entity.BookmarkedEntity
import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity
import com.danc.brilliantboredom.data.remote.dto.BoredomActivity

/**
 * Entails all the methods/function responsible for interaction with the Database
 * Can be either an abstract class or an interface
 * Mainly the methods it provides are for the rest of the Application to use.
 * The methods are of 2 Types
 * 1. Convenience method(Insert, Update, Delete) which does not contain complex SQL statements
 * 2. Query methods this are the SQL statements manually written to interact with the Database */

@Dao
interface BoredomActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoredomActivity(boredomActivity: BoredomActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedActivity(bookmarkedEntity: BookmarkedEntity)

    @Query("DELETE FROM boredomactivityentity WHERE primaryKey = :key")
    suspend fun deleteFromActiveActivities(key: String)

    @Query("DELETE FROM boredomactivityentity WHERE primaryKey IN(:keys)")
    suspend fun deleteBoredomActivity(keys: List<String>)

    @Query("SELECT * FROM boredomactivityentity ORDER BY primaryKey ASC")
    suspend fun getAllBoredomActivities(): List<BoredomActivityEntity>

}