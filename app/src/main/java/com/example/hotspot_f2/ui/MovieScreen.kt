package com.example.hotspot_f2.ui



import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.Database
import com.example.hotspot_f2.MainActivity
import com.example.hotspot_f2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun MoviesScreen(context: Context = LocalContext.current) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimaryDark))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Movies View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Button(
            onClick = {
                //gets firebase user and logs out
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
                context.startActivity(Intent(context, MainActivity::class.java))

            },
        )
        {
            Text(
                text = "Logout"
            )
        }

        Button(onClick = {Database().writeTestData()})
        {
            Text(
                text = "Write test data"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MoviesScreen()
}
