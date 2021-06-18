package com.example.spacexfan.modelproperties

import com.google.gson.annotations.SerializedName

   
data class Flickr (

   @SerializedName("small") var small : List<String>,
   @SerializedName("original") var original : List<String>

)