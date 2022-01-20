package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.GeoPoint


data class Hotspot(
    val id: String = "",
    val title: String,
    val description: String,
    val type: String = "Bar",
    var checkins: MutableState<Int> = mutableStateOf(0),
    val imageID: Int = 0,
    val location: GeoPoint)


data class CheckedInUser(
    val id: String = "",
    val name: String = "",
    val age: Int = 0)

