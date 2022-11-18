package com.danc.brilliantboredom.domain.repositories

import com.danc.brilliantboredom.domain.models.BoredActivity
import com.danc.brilliantboredom.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Model Repository entails all the methods that entail with interacting with the remote Data */

interface BoredomActivityRepository {

    fun getBoredActivity(): Flow<Resource<List<BoredActivity>>>

}