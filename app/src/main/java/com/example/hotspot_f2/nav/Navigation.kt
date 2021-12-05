package com.example.hotspot_f2.nav

import androidx.navigation.NavHostController
import com.example.hotspot_f2.nav.Destination.Home
import com.example.hotspot_f2.nav.Destination.Login
import com.example.hotspot_f2.nav.Destination.Signup
import com.google.firebase.ktx.Firebase


/**
 * A set of destination used in the whole application
 */
object Destination {
    const val AuthenticationOption = "authenticationOption"
    const val Signup = "signup"
    const val Login = "login"
    const val Home = "home"
    const val Firebase = "FIrebase"
}

/**
 * Set of routes which will be passed to different composable so that
 * the routes which are required can be taken.
 */
class Action(navController: NavHostController) {
    val home: () -> Unit = {
        navController.navigate(Home) {
            popUpTo(Login) {
                inclusive = true
            }
            popUpTo(Signup) {
                inclusive = true
            }
        }
    }
    val login: () -> Unit = { navController.navigate(Login) }
    val signup: () -> Unit = { navController.navigate(Signup) }
    val navigateBack: () -> Unit = { navController.popBackStack() }
}