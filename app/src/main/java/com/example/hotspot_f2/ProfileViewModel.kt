package com.example.hotspot_f2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    var name = mutableStateOf("")
    var age = mutableStateOf(0)
    var description = mutableStateOf("")
    var firstLogin = mutableStateOf("")

    var imageID = mutableStateOf(R.drawable.lars)

    init {
        Database().getCurrentUser(this)
    }

}