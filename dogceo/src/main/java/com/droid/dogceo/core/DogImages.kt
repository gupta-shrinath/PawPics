package com.droid.dogceo.core

import android.util.Log
import kotlinx.serialization.Serializable
import java.util.LinkedList

@Serializable
class DogImages(private val index: Int = 0) : LinkedList<String>() {

    var currentIndex: Int = index

    fun hasNextImage(): Boolean {
        return currentIndex < this.size - 1
    }

    fun hasPreviousImage(): Boolean {
        Log.d("TAG", "hasPreviousImage: $currentIndex")
        return currentIndex > 0
    }

    fun getNextImage(): String? {
        if (isEmpty()) {
            currentIndex = -1
            return null
        }
        if (currentIndex + 1 >= size) {
            return null
        }
        currentIndex += 1
        return this[currentIndex]
    }

    fun getPreviousImage(): String? {
        if (isEmpty()) {
            currentIndex = -1
            return null
        }
        if (currentIndex - 1 < 0) {
            return null
        }
        currentIndex -= 1
        return this[currentIndex]
    }
}

fun List<String>.convertToDogImages(): DogImages {
    val dogImages = DogImages()
    dogImages.addAll(this)
    return dogImages
}