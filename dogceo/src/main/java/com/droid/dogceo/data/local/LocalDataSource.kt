package com.droid.dogceo.data.local

import android.util.Log
import androidx.room.Room
import com.droid.dogceo.core.DogCEO
import com.droid.dogceo.data.local.database.DogCEODatabase
import com.droid.dogceo.data.local.database.entities.DogImage

private const val TAG = "LocalDataSource"

class LocalDataSource {

    private val database = Room.databaseBuilder(
        DogCEO.getApplication(),
        DogCEODatabase::class.java, DogCEODatabase.DATABASE_NAME
    ).build()

    suspend fun getDogImages(count:Int): List<DogImage>? {
        return try {
            database.imageDao().getImages(count).filter { it.imageUrl.isNotBlank() }
        } catch (e: Exception) {
            Log.e(TAG, "insertDogImage: ", e)
            null
        }
    }

    suspend fun insertDogImage(imageUrl: String) {
        try {
            database.imageDao().insertImage(DogImage(id = 0, imageUrl = imageUrl))
        } catch (e: Exception) {
            Log.e(TAG, "insertDogImage: ", e)
        }
    }

    suspend fun getDogImageCount():Int {
        return try {
            database.imageDao().getImageCount()
        } catch (e: Exception) {
            Log.e(TAG, "insertDogImage: ", e)
            0
        }
    }

}