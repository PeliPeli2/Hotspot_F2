package com.example.hotspot_f2.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import com.example.hotspot_f2.CheckedInUser
import com.example.hotspot_f2.HomeActivity
import com.example.hotspot_f2.LobbyViewModel
import com.example.hotspot_f2.ProfileViewModel
import com.example.hotspot_f2.R.drawable
import com.example.hotspot_f2.nav.NavigationItem

@Composable
fun LobbyScreen(navController: NavController, lobbyViewModel: LobbyViewModel, profileViewModel: ProfileViewModel) {

    HotspotScreen(lobbyViewModel = lobbyViewModel, profileViewModel = profileViewModel, navController = navController)

/*
    Column {
        Text(text = "Velkommen til lobbyen")
    }
     */
}


@Composable
fun HotspotScreen(lobbyViewModel: LobbyViewModel, profileViewModel: ProfileViewModel, navController: NavController) {
    var viewUsers by remember { mutableStateOf(false) }
    var x by rememberSaveable{ mutableStateOf(lobbyViewModel.checkins.value) }

    Column{
        Card(){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 5.dp),
                painter = painterResource(id = lobbyViewModel.image.value),
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
                text = lobbyViewModel.hotspot.value?.title.toString(),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = if (lobbyViewModel.isCheckedIn.value) Color.Red else Color.Green),
                onClick = {
                    if(lobbyViewModel.isCheckedIn.value) {
                        // if user is checking out
                            lobbyViewModel.checkOut()
                        /*x -= 1
                        lobbyViewModel.isCheckedIn.value = false*/
                    } else {
                        // if user is checking in
                        lobbyViewModel.checkIn(profileViewModel = profileViewModel)
                        /*x += 1
                        lobbyViewModel.isCheckedIn.value = true*/

                    }

                }
            ) { Text(if(lobbyViewModel.isCheckedIn.value) "Check ud" else "Check in") }
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
                text = lobbyViewModel.hotspot.value?.checkins.toString() + " " + "indtjekninger",
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { viewUsers = !viewUsers })
            {
                Icon(
                    Icons.Outlined.Info,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Expand/Hide",
                    tint = Color.Gray
                )
            }
        }

        if(viewUsers) {
            if(lobbyViewModel.isCheckedIn.value) {
                ListOfCheckedInUsers(lobbyViewModel = lobbyViewModel)
            }
        } else {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 3.dp),
                text = lobbyViewModel.hotspot.value?.description.toString(),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ListOfCheckedInUsers(lobbyViewModel: LobbyViewModel) {

    LazyColumn{
        items(items = lobbyViewModel.checkedInUsers) { user ->
            CheckedInUserElement(user = user)
        }
    }
}

@Composable
fun CheckedInUserElement(user: CheckedInUser) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Text(text = user.name, fontSize = 16.sp)
                Text(text = ", " + user.age, fontSize = 16.sp)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 10.dp)
        )
    }
}