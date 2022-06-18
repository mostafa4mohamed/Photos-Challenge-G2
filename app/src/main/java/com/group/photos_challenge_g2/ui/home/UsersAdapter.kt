package com.group.photos_challenge_g2.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.group.photos_challenge_g2.databinding.ItemUserBinding
import com.group.photos_challenge_g2.pojo.response.User
import javax.inject.Inject


class UsersAdapter @Inject constructor() :
    ListAdapter<User, UsersAdapter.ViewHolder>(DiffCallback()) {

    private var lastBinding: ItemUserBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User, position: Int) {

            binding.tvName.text = data.name
            binding.tvCity.text = data.address.city

            if (position == 0 && lastBinding == null) {
                select()
                onItemClickListener?.let { click ->
                    click(data)
                }
            }

            binding.root.setOnClickListener {

                select()

                onItemClickListener?.let { click ->
                    click(data)
                }
            }

            binding.executePendingBindings()
        }

        private fun select() {

            if (lastBinding != null)
                deSelect()

            lastBinding = binding

            binding.root.isSelected = true
        }

        private fun deSelect() {
            lastBinding!!.root.isSelected = false

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User, newItem: User
        ): Boolean = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: User, newItem: User
        ): Boolean = newItem == oldItem
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }
}