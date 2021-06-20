package com.example.spacexfan.utils


import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.spacexfan.R


object Alerts {
    fun authError(context: Context?, message: String?) {
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setIcon(R.drawable.splash_logo)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.setCancelable(false)
        builder.show()
    }
}
