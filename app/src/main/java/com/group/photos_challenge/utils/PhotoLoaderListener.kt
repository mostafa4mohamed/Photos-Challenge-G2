package com.group.photos_challenge.utils

import android.graphics.Bitmap

interface PhotoLoaderListener {

    fun onLoadFinished(bitmap: Bitmap)

}