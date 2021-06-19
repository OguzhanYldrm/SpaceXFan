package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Height (

   @SerializedName("meters") var meters : Double

) : Serializable