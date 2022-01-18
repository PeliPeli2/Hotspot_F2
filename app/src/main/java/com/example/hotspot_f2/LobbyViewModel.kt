package com.example.hotspot_f2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint

class LobbyViewModel: ViewModel() {

    var title = mutableStateOf("")
    var description =  mutableStateOf("")
    var type =  mutableStateOf("")
    var checkins =  mutableStateOf(0)
    var image =  mutableStateOf(R.drawable.lars)
    var location = mutableStateOf(GeoPoint(0.0,0.0))


    // not used yet
    //var hotspot = mutableStateOf(Hotspot("","","",0,R.drawable.lars, GeoPoint(0.0,0.0)))

}