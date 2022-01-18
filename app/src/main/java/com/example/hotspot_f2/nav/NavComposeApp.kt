package com.example.hotspot_f2


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot_f2.nav.NavigationItem
import com.example.hotspot_f2.ui.HotSpotTheme
/**
 * The main Navigation composable which will handle all the navigation stack.
 */

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun NavComposeApp(profileViewModel: ProfileViewModel, hotspotViewModel: HotspotViewModel, lobbyViewModel: LobbyViewModel) {
    val navController = rememberNavController()
    HotSpotTheme {
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Map.route
        ) {
            composable(NavigationItem.Map.route) {
                MainScreen(profileViewModel, hotspotViewModel, lobbyViewModel)
            }
        }
    }
}