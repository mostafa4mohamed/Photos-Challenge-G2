package com.group.photos_challenge_g2.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.group.photos_challenge_g2.databinding.ItemPhotoBinding
import com.group.photos_challenge_g2.pojo.response.Photo
import com.group.photos_challenge_g2.utils.PhotoLoaderListener
import com.group.photos_challenge_g2.utils.Utils.loadImage
import javax.inject.Inject


class PhotosAdapter @Inject constructor() :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Photo) {

            binding.img.loadImage(data.thumbnailUrl, object : PhotoLoaderListener {
                override fun onLoadFinished() {

                    binding.progressBar.visibility = View.GONE

                }
            })

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }

            binding.executePendingBindings()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(
            oldItem: Photo, newItem: Photo
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: Photo, newItem: Photo
        ): Boolean = newItem == oldItem
    }

    private var onItemClickListener: ((Photo) -> Unit)? = null

    fun setOnItemClickListener(listener: (Photo) -> Unit) {
        onItemClickListener = listener
    }

    fun clear() {
        submitList(emptyList())
    }
}