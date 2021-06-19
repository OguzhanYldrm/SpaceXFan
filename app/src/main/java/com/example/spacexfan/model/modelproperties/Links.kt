package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName

   
data class Links (

    @SerializedName("patch") var patch : Patch,
    @SerializedName("reddit") var reddit : Reddit,
    @SerializedName("flickr") var flickr : Flickr,
    @SerializedName("webcast") var webcast : String,
    @SerializedName("article") var article : String,
    @SerializedName("wikipedia") var wikipedia : String

)