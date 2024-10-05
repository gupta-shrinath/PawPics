package com.droid.pawpics.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.droid.dogceo.core.DogCEO
import kotlinx.coroutines.flow.flow

class PawPicsViewModel : ViewModel() {


    fun getImages(count:Int = DogCEO.MAX_IMAGE_COUNT) = flow {
        emit(Async.Loading)
        val images = DogCEO.getImages(count)
        if (images != null) {
            emit(Async.Success(images))
        } else {
            emit(Async.Error("Error fetching dog pics. Please try again!"))
        }
    }

}

sealed class Async<out T : Any> {
    data object Loading : Async<Nothing>()

    data class Error(val errorMessage: String) : Async<Nothing>()

    data class Success<out T : Any>(val data: T) : Async<T>()
}