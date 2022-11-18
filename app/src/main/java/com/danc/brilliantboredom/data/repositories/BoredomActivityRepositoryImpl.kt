package com.danc.brilliantboredom.data.repositories

import android.util.Log
import com.danc.brilliantboredom.data.local.dao.BoredomActivityDao
import com.danc.brilliantboredom.data.local.entity.BookmarkedEntity
import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity
import com.danc.brilliantboredom.data.remote.BoredomAPI
import com.danc.brilliantboredom.data.remote.dto.BoredomActivity
import com.danc.brilliantboredom.domain.models.Bookmarked
import com.danc.brilliantboredom.domain.models.BoredActivity
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository
import com.danc.brilliantboredom.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log

class BoredomActivityRepositoryImpl(
    private val boredomAPI: BoredomAPI,
    private val boredomActivityDao: BoredomActivityDao
) : BoredomActivityRepository {

    override fun getBoredActivity(): Flow<Resource<List<BoredActivity>>> = flow {
        emit(Resource.Loading())
        val boredActivities = boredomActivityDao.getAllBoredomActivities().map {
            it.toBoredActivity()
        }

        emit(Resource.Loading(boredActivities))

        try {
            val remoteBoredActivity = boredomAPI.getBoredomActivity()
//            boredomActivityDao.deleteBoredomActivity(boredActivities.map { it.key })
            boredomActivityDao.insertBoredomActivity(remoteBoredActivity.toSingleBoredActivity())

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops. Something went wrong. Try Again Later",
                    data = boredActivities
                )
            )

        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Check your internet connection",
                    data = boredActivities
                )
            )
        }
    }

    override fun bookmarkActivity(bookmarked: Bookmarked): String = runBlocking {
        launch {
            boredomActivityDao.insertBookmarkedActivity(bookmarked.toBookmarked())
        }
        return@runBlocking "Successfully Inserted"
    }

    override fun deleteBookmarkedActivity(key: String) = runBlocking {
        launch {
            boredomActivityDao.deleteFromActiveActivities(key = key)
        }
        return@runBlocking
    }

    private fun BoredomActivity.toSingleBoredActivity(): BoredomActivityEntity {
        return BoredomActivityEntity(
            activity = activity,
            price = price,
            primaryKey = key,
            type = type,
            participants = participants,
            link = link,
            accessibility = accessibility
        )
    }

    private fun Bookmarked.toBookmarked(): BookmarkedEntity {
        return BookmarkedEntity(
            activity = activity,
            key = key,
            price = price,
            type = type
        )
    }

}