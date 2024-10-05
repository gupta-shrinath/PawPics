package com.droid.pawpics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droid.pawpics.ui.components.AppBar
import com.droid.pawpics.ui.components.DogImageView

@Composable
fun List(images: List<String>, onBackPress: () -> Unit) {
    Scaffold(topBar = { AppBar(onBackPress = onBackPress) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(images.size) { index ->
                DogImageView(imageUrl = images[index])
            }

        }
    }
}