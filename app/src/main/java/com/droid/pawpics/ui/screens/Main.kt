package com.droid.pawpics.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.droid.pawpics.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    goToScreen: (Any) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text("PawPics", style = MaterialTheme.typography.headlineLarge)
                }, navigationIcon = {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = null
                    )
                })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .then(
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    goToScreen(Screens.Carousel)
                },
            ) {
                Text(text = "Carousel View", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    goToScreen(Screens.Input)
                },
            ) {
                Text(text = "List View", style = MaterialTheme.typography.titleMedium)
            }
        }

    }

}