package com.droid.pawpics.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.droid.dogceo.core.DogCEO
import com.droid.dogceo.core.DogImages
import com.droid.pawpics.ui.viewmodel.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Input(fetchImages: (count: Int) -> Flow<Async<DogImages>>, goToScreen: (Any) -> Unit) {
    val coroutine = rememberCoroutineScope()
    var count by remember {
        mutableStateOf("")
    }
    var isValidInput by remember {
        mutableStateOf(true)
    }
    var isImageLoading by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            OutlinedTextField(
                value = count,
                onValueChange = { count = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = isValidInput.not(),
                supportingText = {
                    if (isValidInput.not()) {
                        Text(text = "Invalid count")
                    }
                }
            )
            Button(
                onClick = {
                    if (count.isDigitsOnly()
                            .not() || count.toInt() > DogCEO.MAX_IMAGE_COUNT || count.toInt() < 0
                    ) {
                        isValidInput = false
                        return@Button
                    }
                    isValidInput = true
                    coroutine.launch(Dispatchers.IO) {
                        fetchImages(count.toInt()).collect {
                            when (it) {
                                is Async.Loading -> {
                                    isImageLoading = true
                                }

                                is Async.Success -> {
                                    isImageLoading = false
                                    withContext(Dispatchers.Main) {
                                        goToScreen(Screens.List(images = (it.data.toList())))
                                    }
                                }

                                is Async.Error -> {
                                    isImageLoading = false

                                }
                            }


                        }
                    }

                },
            ) {
                if (isImageLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Fetch")
                }
            }

        }
    }

}