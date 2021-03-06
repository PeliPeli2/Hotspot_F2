package com.example.hotspot_f2.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.Database
import com.example.hotspot_f2.MainActivity
import com.example.hotspot_f2.ProfileViewModel
import com.example.hotspot_f2.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var stat by rememberSaveable { profileViewModel.firstLogin}
        if(stat=="FIRST_LOGIN"){
            SetName(profileViewModel)
        }
        else if(stat=="AGE"){
            SetAge(profileViewModel)
        }
        else if(stat=="DESCRIPTION"){
            SetDescription(profileViewModel)
        }
        else if(stat=="CONFIRM"){
            CreateProfile(profileViewModel)
        }
        else {
            ProfileSection(
                painterResource(R.drawable.face), //TODO: Replace placeholder with somehting else
                profileViewModel
            )
        }
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

    val context = LocalContext.current
    var edit by rememberSaveable { mutableStateOf(false) }
    var editText by rememberSaveable { mutableStateOf("Edit profile") }
    Spacer(modifier = Modifier.height(4.dp))
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

        ) {
            if (!edit){
                StatSection(name, age.toString(), modifier = Modifier.weight(7f))
            }
            else{
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Navn") }
                )
                OutlinedTextField(
                    value = age.toString(),
                    onValueChange = {
                    if(it.toIntOrNull()!=null)
                        age = it.toInt() },
                    label = { Text("Alder") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next),
                )
            }
        }
        if (!edit) {
            ProfileDescription(
                description = description,
            )
        }
        else
        {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Beskrivelse") }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            Button(onClick = {
                val firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
                context.startActivity(Intent(context, MainActivity::class.java))
            })
            {
                Icon(Icons.Rounded.ExitToApp,contentDescription = "Localized description" )
                Text(text = "Logout")
            }

            Button(onClick = {
                if(!edit)
                    editText= "Done"
                else {
                    Database().updateCurrentUser(profileViewModel)
                    editText = "Edit profile"
                }
                edit =!edit
            }) {
                Icon(Icons.Rounded.Settings,contentDescription = "Localized description" )
                Text(text = editText)
            }
        }

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
fun SetName(
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { profileViewModel.name }
    var stat by rememberSaveable { profileViewModel.firstLogin}
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        )
    {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Navn") },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            if (name != "") {
                stat = "AGE"
            }
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            enabled = name != ""
        ){ Text(text = "Videre") }
    }
}

@Composable
fun SetAge(
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var age by rememberSaveable { profileViewModel.age }
    var stat by rememberSaveable { profileViewModel.firstLogin}
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        OutlinedTextField(
            value = age.toString(),
            onValueChange = {
                if(it.toIntOrNull()!=null)
                    age = it.toInt() },
            label = { Text("Alder") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Button(onClick = {
            if (age != 0) {
                stat = "DESCRIPTION"
            }
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            enabled = age != 0
        ){ Text(text = "Videre") }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        ) {
            IconButton(
                onClick = { stat = "FIRST_LOGIN"},

            ){
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}

@Composable
fun SetDescription(
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var description by rememberSaveable { profileViewModel.description }
    var stat by rememberSaveable { profileViewModel.firstLogin}
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beskrivelse") },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            if (description != "") {
                stat = "CONFIRM"
            }
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            enabled = description != ""
        ){ Text(text = "Videre") }
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        ) {
            IconButton(
                onClick = { stat = "AGE"},

                ){
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}

@Composable
fun CreateProfile(
    profileViewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    var confirm by rememberSaveable { mutableStateOf(false) }
    var stat by rememberSaveable { profileViewModel.firstLogin}
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {

        Text("Jeg giver mit udtrykkelige samtykke til, at Hotspot ApS m?? behandle mine oplysninger,"+
            " til oprettelse og drift af min profil. Samtykket kan altid tr??kkes tilbage.",
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
        Checkbox(
            checked = confirm,
            onCheckedChange = { confirm = it },
            enabled = true,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            if (confirm) {
                Database().updateCurrentUser(profileViewModel)
                stat = "NORMAL"
            }
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            enabled = confirm
        ){ Text(text = "Opret Profil") }
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        ) {
            IconButton(
                onClick = { stat = "DESCRIPTION"},
                ){
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}




@Composable
fun StatSection(
    name: String, age: String,
    modifier: Modifier = Modifier
) {
    Column() {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = modifier
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = ", " + age,
                    fontSize = 20.sp,
                )
            }}
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
                    .padding(vertical = 20.dp)
            )
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
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 15.dp),
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 10.dp)
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