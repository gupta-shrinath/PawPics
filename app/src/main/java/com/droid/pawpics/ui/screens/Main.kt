package com.droid.pawpics.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Main(
    goToScreen: (Any) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Button(
                onClick = {
                    goToScreen(Screens.Carousel)
                },
            ) {
                Text(text = "Carousel")
            }
            Button(
                onClick = {
                    goToScreen(Screens.Input)
                },
            ) {
                Text(text = "Input")
            }
        }
    }

}