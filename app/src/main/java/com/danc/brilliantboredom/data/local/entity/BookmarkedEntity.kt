package com.danc.brilliantboredom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danc.brilliantboredom.domain.models.Bookmarked

@Entity
data class BookmarkedEntity(
    @PrimaryKey val key: String,
    val activity: String,
    val price: Double,
    val type: String
)
