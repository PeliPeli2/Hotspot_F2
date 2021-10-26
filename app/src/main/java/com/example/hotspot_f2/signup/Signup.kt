package com.example.hotspot_f2.signup


import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hotspot_f2.Appbar
import com.example.hotspot_f2.Buttons
import com.example.hotspot_f2.TextFormField

/**
 * The Signup view which will be helpful for the user to register themselves into
 * our database and go to the home screen to see and send messages.
 */

@Composable
fun SignupView(
    home: () -> Unit,
    back: () -> Unit,
    signupViewModel: SignupViewModel = viewModel()
) {
    val email: String by signupViewModel.email.observeAsState("")
    val password: String by signupViewModel.password.observeAsState("")
    val loading: Boolean by signupViewModel.loading.observeAsState(initial = false)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Appbar(
                title = "Sign Up",
                action = back
            )
            TextFormField(
                value = email,
                onValueChange = { signupViewModel.updateEmail(it) },
                label = "Email",
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None
            )
            TextFormField(
                value = password,
                onValueChange = { signupViewModel.updatePassword(it) },
                label = "Password",
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Buttons(
                title = "Register",
                onClick = { signupViewModel.registerUser(home = home) },
                backgroundColor = Color.Blue
            )
        }
    }
}