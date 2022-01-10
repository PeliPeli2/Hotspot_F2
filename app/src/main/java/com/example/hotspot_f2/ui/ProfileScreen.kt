package com.example.hotspot_f2.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.Database
import com.example.hotspot_f2.MainActivity
import com.example.hotspot_f2.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth



@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileSection(
            painterResource(id = profileViewModel.imageID.value),
            profileViewModel
        )
    }
}

@Composable
fun TopButtons(profileViewModel: ProfileViewModel) {
    val context = LocalContext.current
    var edit by rememberSaveable { mutableStateOf(false) }
    var editText by rememberSaveable { mutableStateOf("Edit profile") }
    Row() {
        Button(onClick = {
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            context.startActivity(Intent(context, MainActivity::class.java))
        })
        { Text(text = "Logout") }
        Button(onClick = {
            if(edit)
                editText= "Done"
            else
                editText= "Edit profile"
            edit =!edit
        }) { Text(text = editText) }
        Button(onClick = { Database().testGetUser("Adam Abel", profileViewModel) }) { Text(text = "Adam") }
        Button(onClick = { Database().getCurrentUser( profileViewModel) }) { Text(text = "Current") }
        Button(onClick = { Database().updateCurrentUser(profileViewModel) }) { Text(text = "Write current to DB") }
    }
}



@Composable
fun ProfileSection(
    image: Painter,
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { profileViewModel.name }
    var age by rememberSaveable { profileViewModel.age }
    var description by rememberSaveable { profileViewModel.description }

    TopButtons(profileViewModel)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // Profile picture
            RoundImage(
                image = image,
                modifier = Modifier
                    .weight(3f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(name, age.toString(), modifier = Modifier.weight(7f))
            // LogoutButton(profileViewModel)

        }
        ProfileDescription(
            description = description,
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(
    name: String, age: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = name,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = age,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ProfileDescription(
    description: String,
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        ProfileViewModel()
    )
}