package com.droid.pawpics.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.droid.dogceo.core.DogCEO
import com.droid.dogceo.core.DogCEOAPI
import com.droid.dogceo.exceptions.InvalidCountException
import kotlinx.coroutines.flow.flow

class PawPicsViewModel : ViewModel() {


    fun getImages(count: Int = DogCEO.MAX_IMAGE_COUNT) =
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