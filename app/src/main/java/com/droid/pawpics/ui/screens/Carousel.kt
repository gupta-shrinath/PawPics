package com.droid.pawpics.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droid.dogceo.core.DogImages
import com.droid.pawpics.ui.components.AppBar
import com.droid.pawpics.ui.components.DogImageView
import com.droid.pawpics.ui.components.ShimmerEffect
import com.droid.pawpics.ui.viewmodel.Async
import kotlinx.coroutines.flow.Flow

private fun AsyncSaver(): Saver<Async<*>, Any> = Saver(
    save = { async ->
        when (async) {
            is Async.Loading -> "Loading"
            is Async.Error -> async.errorMessage
            is Async.Success<*> -> async.data
        }
    },
    restore = { value ->
        when (value) {
            "Loading" -> Async.Loading
            is String -> Async.Error(value)
            else -> Async.Success(value as DogImages)
        }
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    flow: Flow<Async<DogImages>>,
    onBackPress: () -> Unit
) {
    Log.d("TAG", "Carousel View Composed!!")
    var state by rememberSaveable(stateSaver = AsyncSaver()) { mutableStateOf(Async.Loading) }
    LaunchedEffect(Unit) {
        flow.collect {
            Log.d("TAG", "Carousel: ${it}")
            when (it) {
                is Async.Loading -> {
                    if ((state is Async.Loading).not()) {
                        state = it
                    }
                }

                is Async.Success -> {
                    if ((state is Async.Success).not()) {
                        state = it
                    }
                }

                is Async.Error -> {
                    if ((state is Async.Error).not()) {
                        state = it
                    }
                }


            }

        }

    }
    Scaffold(
        topBar = { AppBar(onBackPress = onBackPress) }
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is Async.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ShimmerEffect(modifier = Modifier.size(200.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                            repeat(2) {
                                ShimmerEffect(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(50.dp)
                                        .background(
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(50)
                                        )

                                )
                            }
                        }
                    }


                }

                is Async.Success -> {
                    val images = (state as Async.Success<DogImages>).data
                    var image by rememberSaveable {
                        mutableStateOf(images.first())
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DogImageView(imageUrl = image)
                        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                            Button(
                                enabled = image.isNotBlank() && images.hasPreviousImage(),
                                onClick = {
                                    val previousImage = images.getPreviousImage()
                                    if (previousImage != null) {
                                        image = previousImage
                                    }
                                },
                            ) {
                                Text(text = "Previous")
                            }
                            Button(
                                enabled = image.isNotBlank() && images.hasNextImage(),
                                onClick = {
                                    val nextImage = images.getNextImage()
                                    if (nextImage != null) {
                                        image = nextImage
                                    }
                                },
                            ) {
                                Text(text = "Next")
                            }


                        }
                    }
                }

                is Async.Error -> {
                    val error = (state as Async.Error).errorMessage
                    Text(
                        text = error,
                        style = MaterialTheme.typography.displaySmall
                    )

                }
            }

        }
    }
}