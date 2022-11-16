package com.danc.brilliantboredom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danc.brilliantboredom.domain.models.BoredActivity

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
            type = type
        )
    }
}
