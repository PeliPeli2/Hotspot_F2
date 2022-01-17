package com.example.hotspot_f2

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotspot_f2.nav.NavigationItem
import com.example.hotspot_f2.ui.DisplayList
import com.example.hotspot_f2.ui.HomeScreen
//import com.example.hotspot_f2.ui.HotSpotListScreen
import com.example.hotspot_f2.ui.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var  firebaseAuth: FirebaseAuth
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val hotspotViewModel by viewModels<HotspotViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        setContent {
            NavComposeApp(profileViewModel, hotspotViewModel)
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
fun MainScreen(profileViewModel: ProfileViewModel, hotspotViewModel: HotspotViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier.padding(innerPadding)) { Navigation(navController, profileViewModel, hotspotViewModel)}
    }}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(ProfileViewModel(), HotspotViewModel())
}


@Composable
fun Navigation(navController: NavHostController, profileViewModel: ProfileViewModel, hotspotViewModel: HotspotViewModel) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(hotspotViewModel, navController)
        }
        composable(NavigationItem.Music.route) {
            ProfileScreen(profileViewModel)
        }
        composable(NavigationItem.Movies.route) {
            DisplayList(hotspotViewModel)
        }
        /*
        composable(NavigationItem.Books.route) {
            BooksScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }*/
    }
}
