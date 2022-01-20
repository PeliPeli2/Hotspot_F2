package com.example.hotspot_f2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class HotspotViewModel: ViewModel() {

    var hotspots = mutableStateListOf<Hotspot>()

    fun addNewHotspot(hotspot: Hotspot) {
        Database().addHotspot(hotspot = hotspot)
    }

    init {
        Database().getHotspots(this)
    }

}