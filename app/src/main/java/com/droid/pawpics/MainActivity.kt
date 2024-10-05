package com.droid.pawpics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droid.pawpics.ui.screens.CarouselView
import com.droid.pawpics.ui.theme.PawPicsTheme
import com.droid.pawpics.ui.viewmodel.PawPicsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel by viewModels<PawPicsViewModel>()
        enableEdgeToEdge()
        setContent {
            PawPicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(modifier = Modifier.padding(innerPadding), viewmodel = viewmodel)
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier, viewmodel: PawPicsViewModel) {
    Box(modifier = modifier) {
        CarouselView(
            flow = viewmodel.getImages(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PawPicsPreview() {
    PawPicsTheme {
        MainView(modifier = Modifier, viewmodel = PawPicsViewModel())
    }
}