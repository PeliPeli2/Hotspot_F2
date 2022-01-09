package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    private var _name: MutableState<String> = mutableStateOf("")
    var name: MutableState<String> = _name

    private var _age: MutableState<Int> = mutableStateOf(0)
    var age: MutableState<Int> = _age

    private var _description: MutableState<String> = mutableStateOf("")
    var description: MutableState<String> = _description

    private var _image: MutableState<Int> = mutableStateOf(0)
    var imageID: MutableState<Int> = _image

    init {
        name.value = "Lars LArsen"
        age.value = 24
        description.value = "For instance, on the planet Earth, man had always assumed that he was more intelligent than dolphins because he had achieved so much—the wheel, New York, wars and so on—whilst all the dolphins had ever done was muck about in the water having a good time. But conversely, the dolphins had always believed that they were far more intelligent than man—for precisely the same reasons."
        imageID.value = R.drawable.lars
    }
}