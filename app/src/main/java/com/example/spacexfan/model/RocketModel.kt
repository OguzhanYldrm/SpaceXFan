package com.example.spacexfan.model

import com.example.spacexfan.model.modelproperties.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
 * https://api.spacexdata.com/v4/rockets
 */
data class RocketModel(

    @SerializedName("height") var height : Height,
    @SerializedName("diameter") var diameter : Diameter,
    @SerializedName("mass") var mass : Mass,
    @SerializedName("flickr_images") var flickrImages : List<String>,
    @SerializedName("name") var name : String,
    @SerializedName("type") var type : String,
    @SerializedName("active") var active : Boolean,
    @SerializedName("cost_per_launch") var costPerLaunch : Int,
    @SerializedName("success_rate_pct") var successRatePct : Int,
    @SerializedName("first_flight") var firstFlight : String,
    @SerializedName("company") var company : String,
    @SerializedName("wikipedia") var wikipedia : String,
    @SerializedName("description") var description : String,
    @SerializedName("id") var id : String

) : Serializable