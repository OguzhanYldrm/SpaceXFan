package com.example.spacexfan

import com.example.spacexfan.modelproperties.Links
import com.google.gson.annotations.SerializedName

/*
 * https://api.spacexdata.com/v4/launches/upcoming
 */
data class UpcommingModel (

    @SerializedName("links") var links : Links,
    @SerializedName("static_fire_date_utc") var staticFireDateUtc : String,
    @SerializedName("static_fire_date_unix") var staticFireDateUnix : Int,
    @SerializedName("rocket") var rocket : String,
    @SerializedName("success") var success : Boolean,
    @SerializedName("failures") var failures : List<String>,
    @SerializedName("details") var details : String,
    @SerializedName("crew") var crew : List<String>,
    @SerializedName("flight_number") var flightNumber : Int,
    @SerializedName("name") var name : String,
    @SerializedName("upcoming") var upcoming : Boolean,
    @SerializedName("id") var id : String

)