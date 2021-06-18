package com.example.spacexfan.modelproperties

import com.google.gson.annotations.SerializedName

   
data class Engines (

   @SerializedName("type") var type : String,
   @SerializedName("version") var version : String,
   @SerializedName("propellant_1") var propellant1 : String,
   @SerializedName("propellant_2") var propellant2 : String,
   @SerializedName("thrust_to_weight") var thrustToWeight : Double

)