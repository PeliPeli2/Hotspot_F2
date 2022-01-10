package com.example.hotspot_f2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    var name = mutableStateOf("Lars Larsen")
    var age = mutableStateOf(24)
    var description = mutableStateOf("Very fancy description for this profile")
    var imageID = mutableStateOf(0)

    init {
        Database().getCurrentUser(this)
    }

}