package com.danc.brilliantboredom.domain.usecases

import com.danc.brilliantboredom.domain.models.BoredActivity
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository
import com.danc.brilliantboredom.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoredActivities (private val repository: BoredomActivityRepository) {

    operator fun invoke(): Flow<Resource<List<BoredActivity>>> {
        return repository.getBoredActivity()
    }

}