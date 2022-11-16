package com.danc.brilliantboredom.di

import android.app.Application
import androidx.room.Room
import com.danc.brilliantboredom.data.local.BoredActivityDatabase
import com.danc.brilliantboredom.data.local.dao.BoredomActivityDao
import com.danc.brilliantboredom.data.remote.BoredomAPI
import com.danc.brilliantboredom.data.repositories.BoredomActivityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): BoredomAPI {
        return Retrofit
            .Builder()
            .baseUrl("https://www.boredapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoredomAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideBoredomActivityDatabase(application: Application): BoredActivityDatabase {
        return Room.databaseBuilder(
            application,
            BoredActivityDatabase::class.java,
        "bored_activity").build()
    }

    @Provides
    @Singleton
    fun provideRepository(boredomAPI: BoredomAPI, boredomActivityDao: BoredomActivityDao): BoredomActivityRepositoryImpl {
        return BoredomActivityRepositoryImpl(
            boredomAPI,
            boredomActivityDao
        )
    }

}