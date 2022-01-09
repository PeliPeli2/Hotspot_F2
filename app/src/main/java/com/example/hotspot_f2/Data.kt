package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.google.firebase.firestore.GeoPoint


data class Hotspot(
    val title: String,
    val description: String,
    val type: String,
    val checkins: Int,
    val imageID: Int,
    val location: GeoPoint)
