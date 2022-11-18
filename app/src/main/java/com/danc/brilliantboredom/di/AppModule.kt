package com.danc.brilliantboredom.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.danc.brilliantboredom.data.local.BoredActivityDatabase
import com.danc.brilliantboredom.data.remote.BoredomAPI
import com.danc.brilliantboredom.data.repositories.BoredomActivityRepositoryImpl
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository
import com.danc.brilliantboredom.domain.usecases.AddActivityToBookmarks
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
        return GetBoredActivities(repository)
    }

    @Provides
    @Singleton
    fun provideBookmarkedActivityUseCase(repository: BoredomActivityRepository): AddActivityToBookmarks {
        return AddActivityToBookmarks(repository)
    }

    @Provides
    @Singleton
    fun provideRepository(
        boredomAPI: BoredomAPI,
        boredActivityDatabase: BoredActivityDatabase
    ): BoredomActivityRepository {
        return BoredomActivityRepositoryImpl(
            boredomAPI = boredomAPI,
            boredomActivityDao = boredActivityDatabase.boredomActivityDao
        )
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `BookmarkedEntity` (`key` TEXT NOT NULL, `activity` TEXT NOT NULL, `type` TEXT NOT NULL, `price` FLOAT NOT NULL, PRIMARY KEY(`key`))"
            )
        }
    }

    @Provides
    @Singleton
    fun provideBoredomActivityDatabase(@ApplicationContext application: Context): BoredActivityDatabase {
        return Room.databaseBuilder(
            application,
            BoredActivityDatabase::class.java,
            "bored_activity"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
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