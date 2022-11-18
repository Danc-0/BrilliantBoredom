package com.danc.brilliantboredom.di

import android.content.Context
import androidx.room.Room
import com.danc.brilliantboredom.data.local.BoredActivityDatabase
import com.danc.brilliantboredom.data.remote.BoredomAPI
import com.danc.brilliantboredom.data.repositories.BoredomActivityRepositoryImpl
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository
import com.danc.brilliantboredom.domain.usecases.GetBoredActivities
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBoredActivitiesUseCase(repository: BoredomActivityRepository): GetBoredActivities {
        return GetBoredActivities(
            repository = repository
        )
    }

    @Provides
    @Singleton
    fun provideRepository(boredomAPI: BoredomAPI, boredActivityDatabase: BoredActivityDatabase): BoredomActivityRepository {
        return BoredomActivityRepositoryImpl(
            boredomAPI = boredomAPI,
            boredomActivityDao = boredActivityDatabase.boredomActivityDao
        )
    }

    @Provides
    @Singleton
    fun provideBoredomActivityDatabase(@ApplicationContext application: Context): BoredActivityDatabase {
        return Room.databaseBuilder(
            application,
            BoredActivityDatabase::class.java,
            "bored_activity"
        ).build()
    }

    private val loggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(
        loggingInterceptor
    ).build()

    @Provides
    @Singleton
    fun provideRetrofit(): BoredomAPI {
        return Retrofit
            .Builder()
            .baseUrl("https://www.boredapi.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoredomAPI::class.java)
    }

}