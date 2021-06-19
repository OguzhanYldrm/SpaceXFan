package com.example.spacexfan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.modelproperties.Diameter
import com.example.spacexfan.model.modelproperties.Height
import com.example.spacexfan.model.modelproperties.Mass

class RocketDetailViewModel : ViewModel() {

    val rocketDetailLiveData = MutableLiveData<RocketModel>()

    fun getData(rocket : RocketModel){
        rocketDetailLiveData.value = rocket
    }
}