package com.example.hotspot_f2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class HotSpotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("hotspotname")
        val description = intent.getStringExtra("hotspotinfo")
        var checkins = intent.getIntExtra("hotspotcheckins",0)
        val image = intent.getIntExtra("hotspotimage",0)
        setContent {
            HotspotScreen(title, description, checkins, image)
        }
    }
}
@Composable
fun HotspotScreen(title : String?, description: String?, checkins : Int,image : Int) {
    var x by rememberSaveable{mutableStateOf(checkins)}
    val context = LocalContext.current
    Column(
        
        modifier = Modifier
        .verticalScroll(rememberScrollState())
    ){
    Card(){

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 5.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop

            )
        IconButton(
            onClick = { context.startActivity(Intent(context, HomeActivity::class.java)) }) {
            Icon(
                Icons.Outlined.ArrowBack,
            contentDescription = "LOL",
                tint = Color.White

            )}}
        Row() {
            Text(
                modifier = Modifier
                    .padding(vertical = 3.dp, horizontal = 5.dp),
                text = title.toString(),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold

            )
            Button(onClick = { x += 1 }) {
                Text("Checkin")

            }
        }
            Row(
                modifier = Modifier

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_people_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(vertical = 4.dp, horizontal = 3.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    text = x.toString() + " " + "indtjekninger",
                    fontSize = 17.sp
                )


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
                    .padding(vertical = 8.dp)
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 3.dp),
                text = description.toString(),
                fontSize = 18.sp
            )

        }}
