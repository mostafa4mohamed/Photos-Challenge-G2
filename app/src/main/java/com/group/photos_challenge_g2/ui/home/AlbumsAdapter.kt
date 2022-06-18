package com.group.photos_challenge_g2.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.group.photos_challenge_g2.databinding.ItemAlbumBinding
import com.group.photos_challenge_g2.pojo.response.Album
import javax.inject.Inject


class AlbumsAdapter @Inject constructor() :
    ListAdapter<Album, AlbumsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Album) {

            binding.tvId.text = data.id.toString()
            binding.tvTitle.text = data.title

            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }

            binding.executePendingBindings()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(
            oldItem: Album, newItem: Album
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: Album, newItem: Album
        ): Boolean = newItem == oldItem
    }

    private var onItemClickListener: ((Album) -> Unit)? = null

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        onItemClickListener = listener
    }

    fun clear() {
        submitList(emptyList())
    }
}