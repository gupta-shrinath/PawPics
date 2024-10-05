package com.droid.pawpics.ui

import com.droid.dogceo.core.DogImages
import com.droid.dogceo.core.convertToDogImages
import com.droid.pawpics.ui.viewmodel.Async
import kotlinx.coroutines.flow.flow

object SampleDataInjector {

    val dogImages =
        flow<Async<DogImages>> { Async.Success(listOf("https://images.dog.ceo/breeds/wolfhound-irish/n02090721_5518.jpg").convertToDogImages()) }
}