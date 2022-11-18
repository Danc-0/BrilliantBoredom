package com.danc.brilliantboredom.domain.models

data class BoredActivity(
    val activity: String,
    val key: String,
    val price: Double,
    val type: String,
    val link: String
) {
    fun toBookmarked(): Bookmarked {
        return Bookmarked(
            price = price,
            activity = activity,
            key = key,
            type = type,
            link = link
        )
    }
}