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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
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
    var state by rememberSaveable(stateSaver = inputStateSaver()) {
        mutableStateOf(InputState())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.count,
                onValueChange = { state = state.copy(count = it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = state.isCountValid.not(),
                supportingText = {
                    if (state.isCountValid.not()) {
                        Text(text = "Invalid count")
                    }
                }
            )
            state.errorMessage?.takeIf { it.isNotBlank() }?.also {
                Text(text = it)
            }
            Button(
                onClick = {
                    try {
                        if (state.count.isDigitsOnly()
                                .not() || state.count.toInt() > DogCEO.MAX_IMAGE_COUNT || state.count.toInt() <= 0
                        ) {
                            state = state.copy(isCountValid = false)
                            return@Button
                        } else {
                            state = state.copy(isCountValid = true)
                        }
                    } catch (e: NumberFormatException) {
                        state = state.copy(isCountValid = false)
                        return@Button
                    }
                    coroutine.launch(Dispatchers.IO) {
                        fetchImages(state.count.toInt()).collect {
                            when (it) {
                                is Async.Loading -> {
                                    state = state.copy(
                                        isImageLoading = true,
                                        errorMessage = null
                                    )
                                }

                                is Async.Success -> {
                                    state = state.copy(
                                        isImageLoading = true,
                                        errorMessage = null
                                    )
                                    withContext(Dispatchers.Main) {
                                        goToScreen(Screens.List(images = (it.data.toList())))
                                    }
                                }

                                is Async.Error -> {
                                    state = state.copy(
                                        isImageLoading = true,
                                        errorMessage = it.errorMessage
                                    )
                                }
                            }


                        }
                    }

                },
            ) {
                if (state.isImageLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Fetch")
                }
            }

        }
    }

}


data class InputState(
    val count: String = "",
    val isCountValid: Boolean = true,
    val isImageLoading: Boolean = false,
    val errorMessage: String? = null
)

private fun inputStateSaver() = Saver<InputState, Map<String, Any?>>(
    save = { state ->
        mapOf(
            "count" to state.count,
            "isCountValid" to state.isCountValid,
            "isImageLoading" to state.isImageLoading,
            "errorMessage" to state.errorMessage
        )
    },
    restore = { restoredState ->
        InputState(
            count = restoredState["count"] as String,
            isCountValid = restoredState["isCountValid"] as Boolean,
            isImageLoading = restoredState["isImageLoading"] as Boolean,
            errorMessage = restoredState["errorMessage"] as String?
        )
    }
)