package com.droid.dogceo.core

import java.util.LinkedList

class DogImages : LinkedList<String>() {

    private var currentIndex: Int = -1

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

fun List<String>.convertToDogImages():DogImages {
    val dogImages = DogImages()
    dogImages.addAll(this)
    return dogImages
}