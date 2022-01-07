package com.example.hotspot_f2

import androidx.lifecycle.MutableLiveData

class ProfileViewModel {
    private var _name = MutableLiveData("")
    var name: MutableLiveData<String> = _name

    private var _age = MutableLiveData("")
    var age: MutableLiveData<String> = _age

    private var _description = MutableLiveData("")
    var description: MutableLiveData<String> = _description
}