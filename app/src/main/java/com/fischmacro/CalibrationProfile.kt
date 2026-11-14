package com.fischmacro

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class CalibrationProfile(
    val name: String,
    val targetColor: Int,
    val threshold: Int,
    val screenWidth: Int,
    val screenHeight: Int,
    val orientation: String,
    val detectionWindow: Int = 120
) {
    fun isColorMatch(sample: Int): Boolean {
        val redDistance = kotlin.math.abs(Color.red(sample) - Color.red(targetColor))
        val greenDistance = kotlin.math.abs(Color.green(sample) - Color.green(targetColor))
        val blueDistance = kotlin.math.abs(Color.blue(sample) - Color.blue(targetColor))
        return redDistance + greenDistance + blueDistance <= threshold
    }
}
