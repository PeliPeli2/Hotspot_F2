package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint

class HotspotViewModel: ViewModel() {

    var title = ""
    var description = ""
    var checkins = 0
    var imageID = ""

    var hotspots = mutableStateListOf<Hotspot>()

    fun addItem(item: Hotspot) { hotspots.add(item) }

    fun removeItem(item: Hotspot) { hotspots.remove(item) }

    init {
        Database().getHotspots(this)
    }

}