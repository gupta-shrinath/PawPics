package com.droid.dogceo.core

import android.app.Application
import com.droid.dogceo.core.DogCEOAPI.MAX_IMAGE_COUNT
import com.droid.dogceo.data.DogCEORepository
import com.droid.dogceo.exceptions.InvalidCountException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class DogCEO(private val repository: DogCEORepository) {

    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
        CoroutineScope(Dispatchers.IO).launch {
            repository.fetchDogImages()
        }
    }

    fun getApplication(): Application {
        if (!::application.isInitialized) {
            throw IllegalStateException("DogCEO is not initialized. Call DogCEO.init(application) in your Application class.")
        }
        return application
    }

    suspend fun getImage() = repository.getDogImage()

    /**
     * @throws InvalidCountException if count is not in the range of 1 to MAX_IMAGE_COUNT
     */
    suspend fun getImages(count: Int): DogImages? {
        when {
            count <= 0 -> throw InvalidCountException("Count should be greater than 0")
            count > MAX_IMAGE_COUNT -> throw InvalidCountException("Count should be less than $MAX_IMAGE_COUNT")
            else -> {
                return repository.getDogImages(count)?.convertToDogImages()
            }
        }
    }
}