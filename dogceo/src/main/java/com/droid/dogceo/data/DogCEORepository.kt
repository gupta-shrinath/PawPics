package com.droid.dogceo.data

internal interface DogCEORepository {

    suspend fun getDogImage(): String?

    suspend fun getDogImages(count: Int): List<String>?

    suspend fun fetchDogImages()

}