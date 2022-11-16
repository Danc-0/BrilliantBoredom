package com.danc.brilliantboredom.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity
import com.danc.brilliantboredom.data.remote.dto.BoredomActivity

interface BoredomActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoredomActivity(boredomActivity: BoredomActivity)

    @Query("DELETE FROM boredomactivityentity WHERE primaryKey IN(:keys)")
    suspend fun deleteBoredomActivity(keys: String)

    @Query("SELECT * FROM boredomactivityentity")
    suspend fun getAllBoredomActivities(): List<BoredomActivityEntity>

}