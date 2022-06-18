package com.group.photos_challenge_g2.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import java.net.InetAddress

object Utils {

    fun showToast(mContext: Context, msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun ImageView.loadImage(url: String, listener: PhotoLoaderListener) {

        this.load(url) {
            listener.onLoadFinished()
        }
    }

    fun getVerticalLayoutManager(mContext: Context): LinearLayoutManager =
        object : LinearLayoutManager(mContext, VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

}