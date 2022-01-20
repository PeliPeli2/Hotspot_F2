package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint

class HotspotViewModel: ViewModel() {

    var hotspots = mutableStateListOf<Hotspot>()

    fun addNewHotspot(hotspot: Hotspot) {
        Database().addHotspot(hotspot = hotspot)
    }

    fun removeItem(item: Hotspot) { hotspots.remove(item) }

    init {
        Database().getHotspots(this)
    }

}