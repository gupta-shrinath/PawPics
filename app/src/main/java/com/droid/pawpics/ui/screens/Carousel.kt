package com.droid.pawpics.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.droid.dogceo.core.DogImages
import com.droid.pawpics.ui.SampleDataInjector.dogImages
import com.droid.pawpics.ui.components.ShimmerEffect
import com.droid.pawpics.ui.viewmodel.Async
import kotlinx.coroutines.flow.Flow

@Composable
fun Carousel(
    flow: Flow<Async<DogImages>>,
) {
    val state by flow.collectAsState(initial = Async.Loading)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
            Modifier
                .align(Alignment.Center)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

                    val context = LocalContext.current
                    var image by remember {
                        mutableStateOf(images.first())
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxHeight(0.4f),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier.aspectRatio(1F),
                                model = ImageRequest.Builder(context)
                                    .data(image)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                error = {
                                    Box(modifier = Modifier.matchParentSize()) {
                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                modifier =
                                                Modifier.size(80.dp),
                                                imageVector = Icons.Default.Warning,
                                                contentDescription = null
                                            )
                                            Text(
                                                text = "Error loading image",
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                        }

                                    }
                                },
                                loading = {
                                    ShimmerEffect(modifier = Modifier.size(200.dp))
                                }
                            )
                        }
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

@Preview
@Composable
fun CarouselViewPreview() {
    Carousel(dogImages)
}