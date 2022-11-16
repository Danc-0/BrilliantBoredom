package com.danc.brilliantboredom.data.remote.dto

import com.danc.brilliantboredom.domain.models.BoredActivity

data class BoredomActivity(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
) {
   fun toSingleBoredActivity(): BoredActivity {
       return BoredActivity(
           activity = activity,
           price = price,
           key = key,
           type = type
       )
   }
}