package com.droid.dogceo.core

import RetrofitClient
import android.app.Application
import androidx.room.Room
import com.droid.dogceo.data.DogCEORepositoryImpl
import com.droid.dogceo.data.local.LocalDataSource
import com.droid.dogceo.data.local.database.DogCEODatabase
import com.droid.dogceo.data.remote.NetworkDataSource

object DogCEOAPI {

    private lateinit var dogCEO: DogCEO

    fun init(application: Application) {
        dogCEO = DogCEO(
            DogCEORepositoryImpl(
                NetworkDataSource(RetrofitClient.getApiService()), LocalDataSource(
                    Room.databaseBuilder(
                        application,
                        DogCEODatabase::class.java, DogCEODatabase.DATABASE_NAME
                    ).build()
                )
            )
        )
        dogCEO.init(application)
    }

    suspend fun getImage(): String? {
        if (!::dogCEO.isInitialized) {
            throw IllegalStateException("DogCEO is not initialized. Call DogCEO.init(application) in your Application class.")
        }
        return dogCEO.getImage()
    }

    suspend fun getImages(number: Int = DogCEO.MAX_IMAGE_COUNT): DogImages? {
        if (!::dogCEO.isInitialized) {
            throw IllegalStateException("DogCEO is not initialized. Call DogCEO.init(application) in your Application class.")
        }
        return dogCEO.getImages(number)
    }


}