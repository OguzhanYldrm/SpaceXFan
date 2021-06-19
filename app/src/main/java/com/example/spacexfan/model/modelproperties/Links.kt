package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Links (

    @SerializedName("patch") var patch : Patch,
    @SerializedName("reddit") var reddit : Reddit,
    @SerializedName("webcast") var webcast : String,
    @SerializedName("article") var article : String,
    @SerializedName("wikipedia") var wikipedia : String

)  : Serializable