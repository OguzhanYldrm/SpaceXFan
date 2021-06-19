package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName

   
data class LandingLegs (

   @SerializedName("number") var number : Int,
   @SerializedName("material") var material : String

)