package com.group.photos_challenge.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

object Utils {

    fun showToast(mContext: Context, msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun imgUrl(farm: Int, server: String, id: String, secret: String): String =
        "http://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"

    fun loadImage(url: String, listener: PhotoLoaderListener) {

        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {

                listener.onLoadFinished(bitmap)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })

    }

}