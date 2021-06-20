package com.example.spacexfan.model.modelproperties

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Mass (

   @SerializedName("kg") var kg : Long

) : Serializable