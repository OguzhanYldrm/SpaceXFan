package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName

   
data class Patch (

   @SerializedName("small") var small : String,
   @SerializedName("large") var large : String

)