package com.example.hotspot_f2.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hotspot_f2.HomeActivity
import com.example.hotspot_f2.LobbyViewModel
import com.example.hotspot_f2.R.drawable
import com.example.hotspot_f2.nav.NavigationItem

@Composable
fun LobbyScreen(navController: NavController, lobbyViewModel: LobbyViewModel) {

    HotspotScreen(lobbyViewModel = lobbyViewModel, navController = navController)

/*
    Column {
        Text(text = "Velkommen til lobbyen")
    }
     */
}


@Composable
fun HotspotScreen(lobbyViewModel: LobbyViewModel, navController: NavController) {
    val title = lobbyViewModel.title.value
    val description = lobbyViewModel.description.value
    val checkins = lobbyViewModel.checkins.value
    val image = lobbyViewModel.image.value

    var buttontext by rememberSaveable{ mutableStateOf("checkin") }
    var buttoncolor by remember{ mutableStateOf(Color.Green) }
    var x by rememberSaveable{ mutableStateOf(checkins) }


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
            IconButton( onClick = {
                //navController.popBackStack()
                navController.navigate(NavigationItem.Map.route)
            })
            {
                Icon(
                    Icons.Outlined.ArrowBack,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "LOL",
                    tint = Color.White
                )
            }
        }
        Row() {
            Text(
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 5.dp),
                text = title.toString(),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttoncolor),
                onClick = {
                    if (buttontext == "checkin") {
                        buttontext = "checkud"
                        buttoncolor = Color.Red
                        x += 1
                    } else {
                        buttontext = "checkin"
                        buttoncolor = Color.Green
                        x += -1
                    }
                }
            ) { Text(buttontext) }
        }
        Row{
            Icon(
                painter = painterResource(id = com.example.hotspot_f2.R.drawable.ic_baseline_people_24),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .padding(vertical = 4.dp, horizontal = 3.dp)
            )
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
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
    }
}