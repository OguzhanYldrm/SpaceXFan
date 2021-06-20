package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Patch (

   @SerializedName("small") var small : String?,

)  : Serializable