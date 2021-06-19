package com.example.spacexfan.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.model.modelproperties.*

class UpcomingDetailViewModel : ViewModel() {

    val upcomingDetailLiveData = MutableLiveData<UpcomingModel>()

    fun getData(upcoming : UpcomingModel){
        upcomingDetailLiveData.value = upcoming
    }
}