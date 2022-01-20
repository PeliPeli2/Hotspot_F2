package com.example.hotspot_f2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.hotspot_f2.*

@Composable
fun HomeScreen(hotspotViewModel: HotspotViewModel, lobbyViewModel: LobbyViewModel, navController: NavHostController) {

    Box(modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) { Hotspotmap(hotspotViewModel = hotspotViewModel, lobbyViewModel = lobbyViewModel,navController = navController, modifier = Modifier){}
    }
    AddMarker(hotspotViewModel = hotspotViewModel)
}
@Preview(showBackground = true)

@Composable
fun HomeScreenPreview() {
    //HomeScreen(HotspotViewModel())
}

