package com.example.hotspot_f2

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun AddMarker(
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
) {
    val showInputForm = remember { mutableStateOf(false) }
    val textState1 = remember { mutableStateOf(TextFieldValue()) }
    val textState2 = remember { mutableStateOf(TextFieldValue()) }
    val textState3 = remember { mutableStateOf(TextFieldValue()) }
   // val textState4 = remember { mutableStateOf(TextFieldValue()) }

    Column(Modifier.padding(16.dp)) {
        if (showInputForm.value) {
            Row() {
                // Cancel adding a hotspot
                Button(modifier = Modifier.padding(end = 4.dp), onClick = { showInputForm.value = false }) {
                    Text("Cancel")
                }

                Button(onClick = {
                    testAddingAHotspot(textState1.value.text, textState2.value.text,textState3.value.text)
                    Toast.makeText(context, "Hotspot created", Toast.LENGTH_LONG).show()
                    showInputForm.value = true //hide after adding hotspot //TODO: Skal rettes til false
                }
                ) {
                    Text("Add")
                }
            }

            //  Text("The text: " + textState1.value.text)
            TextField(
                value = textState1.value,
                textStyle = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                onValueChange = { textState1.value = it },
                label = { Text("Address or Area") }
            )

            TextField(
                value = textState2.value,
                textStyle = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                onValueChange = { textState2.value = it },
                label = { Text("Name") }
            )

            TextField(
                value = textState3.value,
                textStyle = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                onValueChange = { textState3.value = it },
                label = { Text("Description") }
            )
/*
            TextField(
                value = textState4.value,
                textStyle = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                onValueChange = { textState4.value = it },
                label = { Text("Snippet") }
            ) */
        }
        else {
            // show input form when button is clicked
            Button(onClick = { showInputForm.value = true }) {
                Text("Add Hotspot")
            }
        }
    }
}