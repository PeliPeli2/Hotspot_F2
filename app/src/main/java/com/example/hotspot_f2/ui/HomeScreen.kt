package com.example.hotspot_f2.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.AddMarker
import com.example.hotspot_f2.Hotspotmap
import com.example.hotspot_f2.R
import com.google.firebase.auth.FirebaseAuth


private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun HomeScreen(context: Context = LocalContext.current) {

    Box(modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) { Hotspotmap(modifier = Modifier){} }
    AddMarker(modifier = Modifier)
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

