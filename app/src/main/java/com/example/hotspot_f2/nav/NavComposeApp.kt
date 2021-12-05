package com.example.hotspot_f2


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot_f2.nav.Action
import com.example.hotspot_f2.nav.Destination.Home
import com.example.hotspot_f2.ui.HotSpotTheme
/**
 * The main Navigation composable which will handle all the navigation stack.
 */

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Action(navController) }
    HotSpotTheme {
        NavHost(
            navController = navController,
            startDestination =
                Home
        ) {
            composable(Home) {
                MainScreen()
            }
        }
    }
}