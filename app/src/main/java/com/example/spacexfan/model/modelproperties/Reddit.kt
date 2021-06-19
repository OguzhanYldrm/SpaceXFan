package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Reddit (

   @SerializedName("campaign") var campaign : String,
   @SerializedName("launch") var launch : String

)  : Serializable