package com.example.hotspot_f2

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


class HotSpotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("hotspotname")
        val description = intent.getStringExtra("hotspotinfo")
        val checkins = intent.getStringExtra("hotspotcheckins")
        val image = intent.getStringExtra("hotspotimage")
        setContent {
            HotspotScreen(title, description, checkins, image)
        }
    }
}
@Composable
fun HotspotScreen(title : String?, description: String?, checkins : String?,image : String? ) {
    Column() {

        Text(
            text = title.toString(),
            fontSize = 20.sp
        )
        Text(
            text = description.toString(),
            fontSize = 20.sp
        )
        Text(
            text = checkins.toString(),
            fontSize = 20.sp
        )
        Text(
            text = image.toString(),
            fontSize = 20.sp
        )
    }
}
