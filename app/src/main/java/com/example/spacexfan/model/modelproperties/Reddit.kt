package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName

   
data class Reddit (

   @SerializedName("campaign") var campaign : String,
   @SerializedName("launch") var launch : String

)