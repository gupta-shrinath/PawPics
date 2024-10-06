package com.droid.dogceo.data

import android.util.Log
import com.droid.dogceo.core.DogCEOAPI
import com.droid.dogceo.data.local.LocalDataSource
import com.droid.dogceo.data.remote.NetworkDataSource

private const val TAG = "DogCEORepositoryImpl"

internal class DogCEORepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : DogCEORepository {

    override suspend fun getDogImage(): String? {
        try {
            val localImages = localDataSource.getDogImages(1)
            if (localImages.isNullOrEmpty()) {
                return networkDataSource.getDogImage().takeIf { it != null }?.message?.also {
                    localDataSource.insertDogImage(it)
                }
            }
            return localImages.firstOrNull()?.imageUrl
        } catch (e: Exception) {
            Log.e(TAG, "getDogImage", e)
            return null
        }
    }

    override suspend fun getDogImages(count: Int): List<String>? {
        try {
            val localImages = localDataSource.getDogImages(count)
            if (localImages.isNullOrEmpty()) {
                val images = networkDataSource.getDogImages(count)
                images?.let { dogImages ->
                    dogImages.message.forEach {
                        localDataSource.insertDogImage(it)
                    }
                }
                return images?.message
            }
            if (localImages.size < count) {
                val images = networkDataSource.getDogImages(count - localImages.size)
                images?.let { dogImages ->
                    dogImages.message.forEach {
                        localDataSource.insertDogImage(it)
                    }
                    return localDataSource.getDogImages(count)?.map { it.imageUrl }?.shuffled()
                }
            }
            return localImages.map { it.imageUrl }.shuffled()
        } catch (e: Exception) {
            Log.e(TAG, "getDogImage", e)
            return null
        }
    }

    override suspend fun fetchDogImages() {
        try {
            val imagesCount = localDataSource.getDogImageCount()
            if (imagesCount < DogCEOAPI.MAX_IMAGE_COUNT) {
                val images = networkDataSource.getDogImages(DogCEOAPI.MAX_IMAGE_COUNT - imagesCount)
                images?.let { dogImages ->
                    dogImages.message.forEach {
                        localDataSource.insertDogImage(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchDogImages", e)
        }
    }

}