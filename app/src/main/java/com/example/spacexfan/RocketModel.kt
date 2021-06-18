package com.example.spacexfan

import com.example.spacexfan.modelproperties.*
import com.google.gson.annotations.SerializedName

/*
 * https://api.spacexdata.com/v4/rockets
 */
data class RocketModel(

    @SerializedName("height") var height : Height,
    @SerializedName("diameter") var diameter : Diameter,
    @SerializedName("mass") var mass : Mass,
    @SerializedName("engines") var engines : Engines,
    @SerializedName("landing_legs") var landingLegs : LandingLegs,
    @SerializedName("flickr_images") var flickrImages : List<String>,
    @SerializedName("name") var name : String,
    @SerializedName("type") var type : String,
    @SerializedName("active") var active : Boolean,
    @SerializedName("stages") var stages : Int,
    @SerializedName("boosters") var boosters : Int,
    @SerializedName("cost_per_launch") var costPerLaunch : Int,
    @SerializedName("success_rate_pct") var successRatePct : Int,
    @SerializedName("first_flight") var firstFlight : String,
    @SerializedName("company") var company : String,
    @SerializedName("wikipedia") var wikipedia : String,
    @SerializedName("description") var description : String,
    @SerializedName("id") var id : String

)