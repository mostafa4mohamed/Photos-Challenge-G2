package com.group.koinandcoroutiens.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.ItemMainBinding
import com.group.koinandcoroutiens.pojo.response.PhotosResponseItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object {
//            private val TAG = MainAdapter::class.java.simpleName
    }

    private var list = ArrayList<PhotosResponseItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_main,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(responseItem: PhotosResponseItem) {

            binding.tvTitle.text = responseItem.title
            binding.tvDetails.text = responseItem.thumbnailUrl

            Picasso.get().load(responseItem.url).into(binding.img, object : Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.img.setImageResource(R.drawable.ic_baseline_warning_24)
                }
            })

            binding.executePendingBindings()

        }
    }

    fun submitData(data: ArrayList<PhotosResponseItem>) {
        this.list = data
        notifyDataSetChanged()
    }

}