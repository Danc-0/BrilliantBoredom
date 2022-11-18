package com.danc.brilliantboredom.data.remote.dto

import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity

data class BoredomActivity(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)
