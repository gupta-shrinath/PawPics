package com.droid.pawpics.ui.screens

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    object Main

    @Serializable
    object Carousel

    @Serializable
    object Input

    @Serializable
    data class List(@Serializable val images: kotlin.collections.List<String>)

}