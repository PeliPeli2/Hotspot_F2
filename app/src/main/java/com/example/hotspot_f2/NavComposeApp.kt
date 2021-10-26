package com.example.hotspot_f2


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.example.hotspot_f2.nav.Action
import com.example.hotspot_f2.nav.Destination.AuthenticationOption
import com.example.hotspot_f2.nav.Destination.Home
import com.example.hotspot_f2.nav.Destination.Login
import com.example.hotspot_f2.nav.Destination.Signup
import com.example.hotspot_f2.ui.HotSpotTheme
import com.example.hotspot_f2.login.LoginView
import com.example.hotspot_f2.signup.SignupView
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
            if (FirebaseAuth.getInstance().currentUser != null)
                Home
            else
                AuthenticationOption
        ) {
            composable(AuthenticationOption) {
                AuthenticationView(
                    signup = actions.signup,
                    login = actions.login
                )
            }
            composable(Signup) {
                SignupView(
                    home = actions.home,
                    back = actions.navigateBack
                )
            }
            composable(Login) {
                LoginView(
                    home = actions.home,
                    back = actions.navigateBack
                )
            }
            composable(Home) {
                MainScreen()
            }
        }
    }
}