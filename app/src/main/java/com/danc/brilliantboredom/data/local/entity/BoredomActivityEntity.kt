package com.danc.brilliantboredom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danc.brilliantboredom.domain.models.BoredActivity

/**
 * Represents a single table within the Database.
 * Each individual item on the class will be a column in the Database.
 * Every instance of the class represents a single ROW within the Database. */

@Entity
data class BoredomActivityEntity(
    @PrimaryKey val primaryKey: String,
    val accessibility: Double,
    val activity: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
) {
    fun toBoredActivity(): BoredActivity {
        return BoredActivity(
            price = price,
            activity = activity,
            key = primaryKey,
            type = type,
            link = link
        )
    }
}
