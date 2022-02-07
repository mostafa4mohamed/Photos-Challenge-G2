package com.group.photos_challenge.ui.photos

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.group.photos_challenge.R
import com.group.photos_challenge.databinding.ItemAdBannerBinding
import com.group.photos_challenge.databinding.ItemPhotoBinding
import com.group.photos_challenge.pojo.response.Photo
import com.group.photos_challenge.utils.Constants
import com.group.photos_challenge.utils.PhotoLoaderListener
import com.group.photos_challenge.utils.RecyclerViewOnClickListener
import com.group.photos_challenge.utils.Utils

class PhotosAdapter(private val listener: RecyclerViewOnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<Photo?>()

    override fun getItemViewType(position: Int): Int =
        if ((position + 1) % 6 == 0)
            Constants.BANNER_TYPE
        else
            Constants.PHOTO_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        when (viewType) {
            Constants.PHOTO_TYPE -> PhotoHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_photo,
                    parent, false
                )
            )
            Constants.BANNER_TYPE -> BannerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_ad_banner,
                    parent, false
                )
            )
            else -> PhotoHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_photo,
                    parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is BannerHolder -> holder.bind(position)
            is PhotoHolder -> holder.bind(list[position] as Photo, position)
        }

    }

    override fun getItemCount(): Int = list.size

    inner class BannerHolder(private val binding: ItemAdBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {

            val mContext = binding.root.context

            binding.tvTitle.text =
                "${mContext.getString(R.string.example_for_ad_banner)} ${(position + 1) / 6}"

        }

    }

    inner class PhotoHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo, position: Int) {

            Log.e(TAG, "bind: ${item.id} p $position")

            binding.tvTitle.text = item.title

            val url = Utils.imgUrl(item.farm, item.server, item.id, item.secret)

            Utils.loadImage(binding.img, url,
                object : PhotoLoaderListener {
                    override fun onLoadFinished() {

                        binding.progressBar.visibility = View.GONE

                    }
                })

            binding.img.setOnClickListener { listener.onRootClickListener(url) }

            binding.executePendingBindings()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: ArrayList<Photo>) {

        repeat(data.size / 5) {

            this.list.addAll(data.subList(it * 5, it * 5 + 5))

            this.list.add(null)
        }

        notifyDataSetChanged()
    }

    companion object {
        private val TAG = PhotosAdapter::class.java.simpleName
    }

}