package com.example.hotspot_f2.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

data class Hotspots2(
    val name: String,
    val type: String,
    val checkins: Int,
    val image: Painter)
