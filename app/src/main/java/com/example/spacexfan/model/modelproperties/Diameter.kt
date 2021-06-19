package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Diameter (

   @SerializedName("meters") var meters : Double

) : Serializable