package com.group.photos_challenge.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import coil.load

object Utils {

    fun showToast(mContext: Context, msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun imgUrl(farm: Int, server: String, id: String, secret: String): String =
        "http://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"

    fun loadImage(imgView: ImageView, url: String, listener: PhotoLoaderListener) {

        imgView.load(url) {
            listener.onLoadFinished()
        }
    }

}