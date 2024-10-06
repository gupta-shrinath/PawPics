package com.droid.pawpics.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.droid.dogceo.core.DogCEOAPI
import com.droid.dogceo.core.DogImages
import com.droid.dogceo.exceptions.InvalidCountException
import kotlinx.coroutines.flow.flow

class PawPicsViewModel : ViewModel() {

    fun getImage() = flow {
        emit(Async.Loading)
        Log.d("TAG", "getImage flow started")
        try {
            val image = DogCEOAPI.getImage()
            if (image != null) {
                val dogImage = DogImages()
                dogImage.add(image)
                emit(Async.Success(dogImage))
            } else {
                emit(Async.Error("Error fetching dog pic. Please try again!"))
            }
        } catch (e: InvalidCountException) {
            emit(Async.Error(e.message.toString()))
        }
    }

    fun getImages(count: Int = DogCEOAPI.MAX_IMAGE_COUNT) =
        flow {
            emit(Async.Loading)
            Log.d("TAG", "getImages flow started")
            try {
                val images = DogCEOAPI.getImages(count)
                if (images != null) {
                    emit(Async.Success(images))
                } else {
                    emit(Async.Error("Error fetching dog pics. Please try again!"))
                }
            } catch (e: InvalidCountException) {
                emit(Async.Error(e.message.toString()))
            }
        }

}

sealed class Async<out T : Any> {
    data object Loading : Async<Nothing>()

    data class Error(val errorMessage: String) : Async<Nothing>()

    data class Success<out T : Any>(val data: T) : Async<T>()
}