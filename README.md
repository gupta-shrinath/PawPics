## Problem Statement

## Library

Create an Android library to fetch Dog images on the initialization of the library. The library
should have a few helper methods listed below

- getImage() - Gets one image of a dog from the library.
- getImages(int number) - Gets the number of dog images mentioned in the method
- getNextImage() - Gets the next image of a dog
- getPreviousImage() - Gets the previous image of a dog

## App

The app should have two buttons, “Next” and “Previous”. The “Previous” button
should be inactive on the first image. Clicking on the “Next” button should randomly get an image of
a dog from the library.Clicking on “Previous” should get the previous image

Add an input box to input a number between 1 & 10. Add a “Submit” button. The button should
fetch the number of dog images from the API mentioned in the input box and show it on a new
Activity as a List or RecyclerView.

## Solution

- DogCEO - Android library to fetch Dog images.
- PawPics - Android app that uses the above library to demonstrate the helper
  methods.
- Click on [app](https://github.com/gupta-shrinath/PawPics/raw/refs/heads/main/apk/pawpics.apk) to
  get the apk.

## PawPics

- Built using Jetpack Compose.
- Built with Single Activity.
- Uses Typesafe compose navigation.
- Follows MVVM architecture.
- Uses Coil to display and cache images.
- It uses DogCEOAPI.getImages(int number) primarily because the usecases of the app are better
  fulfilled with it as the usecases requires list of dog images which the mentioned method returns.
  It uses DogCEOAPI.getImage() in case of one image to be shown.
- Manages state across screen rotation.

## DogCEO

- Written in Kotlin.
- Requires app module to call DogCEOAPI.init() in Application class.
- DogCEOAPI.init() fetches MAX_IMAGE_COUNT images stores in database
- Provides DogCEOAPI object to use above mentioned helper methods.
- DogCEO.getImages(int number) returns a DogImages object which provides the getNextImage() and
  getPreviousImage() and other methods.
- Stores dog image urls in Room database
- Network calls are implemented using Retrofit2
- Json parsing using moshi.
- Unit test cases are written using JUnit and Mockito
- Exposes only required APIs to other modules
