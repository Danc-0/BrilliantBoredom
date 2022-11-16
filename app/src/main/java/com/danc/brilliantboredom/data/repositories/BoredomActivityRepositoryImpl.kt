package com.danc.brilliantboredom.data.repositories

import com.danc.brilliantboredom.data.local.dao.BoredomActivityDao
import com.danc.brilliantboredom.data.remote.BoredomAPI
import com.danc.brilliantboredom.domain.models.BoredActivity
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository
import com.danc.brilliantboredom.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BoredomActivityRepositoryImpl(
    private val boredomAPI: BoredomAPI,
    private val boredomActivityDao: BoredomActivityDao
): BoredomActivityRepository {

    override fun getBoredActivity(): Flow<Resource<List<BoredActivity>>> = flow {
        emit(Resource.Loading())
        val boredActivities = boredomActivityDao.getAllBoredomActivities().map {
            it.toBoredActivity()
        }
        emit(Resource.Loading(boredActivities))

        try {
            val remoteBoredActivity = boredomAPI.getBoredomActivity()
            boredomActivityDao.deleteBoredomActivity(remoteBoredActivity.key)
            boredomActivityDao.insertBoredomActivity(remoteBoredActivity)


        } catch (e: HttpException){
            emit(Resource.Error(
                message = "Oops. Something went wrong. Try Again Later",
                data = boredActivities
            ))

        } catch (e: IOException){
            emit(Resource.Error(
                message = "Check your internet connection",
                data = boredActivities
            ))
        }
    }
}