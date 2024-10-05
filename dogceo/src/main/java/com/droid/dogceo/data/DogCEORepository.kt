package com.droid.dogceo.data

interface DogCEORepository {

    suspend fun getDogImage(): String?

    suspend fun getDogImages(count:Int): List<String>?

    suspend fun fetchDogImages()

}