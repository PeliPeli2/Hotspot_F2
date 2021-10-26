package com.example.hotspot_f2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.ui.HotSpotTheme

/**
 * LoginScreen for HotSpot with prompt to Login or Register
 */

@Composable
fun AuthenticationView(signup: () -> Unit, login: () -> Unit){
    HotSpotTheme {
        // Container with background color for the theme
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.colorPrimaryDark))
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Title(title = "HotSpot F2")
                Buttons(title = "Sign Up", onClick = signup, backgroundColor = Color.Black)
                Buttons(title = "Login", onClick = login, backgroundColor = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoinScreenPreview() {
    AuthenticationView(signup = { /*TODO*/ }) {
    }
}

