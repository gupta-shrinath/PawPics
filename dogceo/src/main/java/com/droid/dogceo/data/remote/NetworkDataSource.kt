package com.droid.dogceo.data.remote

import android.util.Log
import com.droid.dogceo.data.models.DogImage
import com.droid.dogceo.data.models.DogImages


internal class NetworkDataSource(private val service: ApiService) {

    suspend fun getDogImage(): DogImage? {
        return try {
            val response = service.getDogImage()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }

    suspend fun getDogImages(count: Int): DogImages? {
        return try {
            val response =
                service.getDogImages(APIConstants.BASE_URL + APIConstants.IMAGES_ENDPOINT + count)
            if (response.isSuccessful) {
                (response.body() as DogImages).copy(message = (response.body() as DogImages).message.filter { it.isNotBlank() })
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("NetworkDataSource", "getDogImages: ", e)
            null
        }
    }


}