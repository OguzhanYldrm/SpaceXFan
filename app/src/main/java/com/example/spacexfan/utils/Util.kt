package com.example.spacexfan.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.spacexfan.R

fun ImageView.loadImage(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions()
        .centerInside()
        .placeholder(placeholder)
        .error(R.drawable.rocket_placeholder)

    Glide
        .with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun ImageView.loadGif(){
    Glide.with(context)
        .load(R.raw.loading)
        .placeholder(R.drawable.loading_1)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

fun notFoundPlaceholder(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 6f
        centerRadius = 35f
        start()
    }
}

