package com.droid.pawpics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droid.dogceo.core.DogImages
import com.droid.pawpics.ui.screens.CarouselView
import com.droid.pawpics.ui.theme.PawPicsTheme
import com.droid.pawpics.ui.viewmodel.Async
import com.droid.pawpics.ui.viewmodel.PawPicsViewModel
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel by viewModels<PawPicsViewModel>()
        enableEdgeToEdge()
        setContent {
            PawPicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        flow = viewmodel.getImages(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(flow: Flow<Async<DogImages>>, modifier: Modifier = Modifier) {
    val state by flow.collectAsState(initial = Async.Loading)

    when (state) {
        is Async.Loading -> {
            Log.d("Loading", "Something")
        }

        is Async.Success -> {
            val images = (state as Async.Success<DogImages>).data
            Log.d("TAG", "Greeting: " + images.size.toString())
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CarouselView(
                       images
                    )
                }
            }


        }

        is Async.Error -> {
            Log.d("Error", "Something")

        }
    }
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PawPicsTheme {
//        Greeting("Android")
    }
}