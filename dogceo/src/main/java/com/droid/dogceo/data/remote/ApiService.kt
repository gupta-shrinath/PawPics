package com.droid.dogceo.data.remote

import com.droid.dogceo.data.models.DogImage
import com.droid.dogceo.data.models.DogImages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET(APIConstants.IMAGES_ENDPOINT)
    suspend fun getDogImage(): Response<DogImage>

    @GET
    suspend fun getDogImages(@Url url:String):Response<DogImages>
}
