package com.example.spacexfan.model

import com.example.spacexfan.model.modelproperties.Links
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
 * https://api.spacexdata.com/v4/launches/upcoming
 */
data class UpcomingModel (

    @SerializedName("links") var links : Links,
    @SerializedName("date_local") var fireDate : String,
    @SerializedName("rocket") var rocket : String,
    @SerializedName("details") var details : String,
    @SerializedName("flight_number") var flightNumber : Int,
    @SerializedName("name") var name : String,
    @SerializedName("id") var id : String

)  : Serializable