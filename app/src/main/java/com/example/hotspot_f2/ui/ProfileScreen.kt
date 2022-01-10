package com.example.hotspot_f2.ui

import android.content.Context
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
import com.example.hotspot_f2.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(name:String, age:String, description:String, image:Painter) {
    val model: ProfileViewModel
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileSection(name, age, description, image)
    }
}


@Composable
fun ProfileSection(
    name:String,
    age:String,
    description:String,
    image:Painter,
    modifier: Modifier = Modifier,
    model: ProfileViewModel = ProfileViewModel(),
) {
    var name by rememberSaveable { model.name }
    var age by rememberSaveable { model.age }
    var description by rememberSaveable { model.description }
    var edit by rememberSaveable { mutableStateOf(false) }
    var editText by rememberSaveable { mutableStateOf("Edit profile") }

    Column(modifier = modifier
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
            if (!edit){
                Spacer(modifier = Modifier.width(16.dp))
                StatSection(name, age, modifier = Modifier.weight(7f))
            }
            else{
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") }
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
                label = { Text("Description") }
            )
        }
        Button(onClick = {
            if(!edit)
                editText= "Done"
            else
                editText= "Edit profile"
            edit =!edit}
        ) { Text(text = editText) }
        LogoutButton()
    }
}

@Composable
fun LogoutButton(context: Context = LocalContext.current){
    Button(onClick = {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        context.startActivity(Intent(context, MainActivity::class.java))
    }) { Text(text = "Logout") }

    Button(onClick = { Database().writeTestData()}
    ) { Text(text = "Write test data") }
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
fun StatSection(name:String, age:String,
    modifier: Modifier = Modifier) {
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
            Text(text = age,
                fontSize = 18.sp
            )
        }    }
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
    ProfileScreen("Lars LArsen",
        "24",
        "For instance, on the planet Earth, man had always assumed that he was more intelligent than dolphins because he had achieved so much—the wheel, New York, wars and so on—whilst all the dolphins had ever done was muck about in the water having a good time. But conversely, the dolphins had always believed that they were far more intelligent than man—for precisely the same reasons."
        ,painterResource(id = R.drawable.lars)
        )
}