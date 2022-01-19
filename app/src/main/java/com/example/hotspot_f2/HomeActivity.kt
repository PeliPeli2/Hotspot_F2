package com.example.hotspot_f2

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot_f2.nav.NavigationItem
import com.example.hotspot_f2.ui.DisplayList
import com.example.hotspot_f2.ui.HomeScreen
import com.example.hotspot_f2.ui.LobbyScreen
//import com.example.hotspot_f2.ui.HotSpotListScreen
import com.example.hotspot_f2.ui.ProfileScreen
import com.google.firebase.auth.FirebaseAuth
import com.example.hotspot_f2.Hotspot

class HomeActivity : AppCompatActivity() {

    private lateinit var  firebaseAuth: FirebaseAuth
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val hotspotViewModel by viewModels<HotspotViewModel>()
    private val lobbyViewModel   by viewModels<LobbyViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        setContent {
            NavComposeApp(profileViewModel, hotspotViewModel, lobbyViewModel)
        }
    }
    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            val email = firebaseUser.email
        }
    }
}

@Composable
fun MainScreen(profileViewModel: ProfileViewModel, hotspotViewModel: HotspotViewModel, lobbyViewModel: LobbyViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) { Navigation(navController, profileViewModel, hotspotViewModel, lobbyViewModel)}
    }}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(ProfileViewModel(), HotspotViewModel(), LobbyViewModel())
}


@Composable
fun Navigation(navController: NavHostController, profileViewModel: ProfileViewModel, hotspotViewModel: HotspotViewModel, lobbyViewModel: LobbyViewModel) {
    NavHost(navController, startDestination = NavigationItem.Map.route) {
        composable(NavigationItem.Map.route) {
            HomeScreen(hotspotViewModel = hotspotViewModel, lobbyViewModel = lobbyViewModel, profileViewModel = profileViewModel, navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(profileViewModel)
        }
        composable(NavigationItem.List.route) {
            DisplayList(hotspotViewModel)
        }
        composable(NavigationItem.Lobby.route) {
            LobbyScreen(navController = navController, lobbyViewModel = lobbyViewModel)
        }
        /*
        composable(NavigationItem.Books.route) {
            BooksScreen()
        }
      */

    }}

