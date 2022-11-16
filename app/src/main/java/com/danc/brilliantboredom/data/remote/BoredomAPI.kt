package com.danc.brilliantboredom.data.remote

import com.danc.brilliantboredom.data.remote.dto.BoredomActivity
import retrofit2.http.GET

interface BoredomAPI {

    @GET("/api/activity")
    suspend fun getBoredomActivity(): BoredomActivity

}