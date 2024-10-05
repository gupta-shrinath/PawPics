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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.droid.pawpics.ui.screens.Carousel
import com.droid.pawpics.ui.screens.Main
import com.droid.pawpics.ui.screens.Screens
import com.droid.pawpics.ui.theme.PawPicsTheme
import com.droid.pawpics.ui.viewmodel.PawPicsViewModel


class MainActivity : ComponentActivity() {
    private val viewmodel by viewModels<PawPicsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PawPicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screens.Main
                        ) {
                            composable<Screens.Main> {
                                Main(navController::navigate)
                            }

                            composable<Screens.Carousel> {
                                Carousel(
                                    flow = viewmodel.getImages(),
                                )
                            }

                            composable<Screens.Input> {
//                                Input(viewmodel::getImages, navController::navigate)
                            }

                            composable<Screens.List> {
                                val args = it.toRoute<Screens.List>()
                                com.droid.pawpics.ui.screens.List(args.images)
                            }

                        }
                    }
                }
            }
        }
    }
}