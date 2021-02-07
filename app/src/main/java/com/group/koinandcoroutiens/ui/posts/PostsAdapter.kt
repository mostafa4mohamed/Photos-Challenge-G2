package com.group.koinandcoroutiens.ui.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.ItemPostBinding
import com.group.koinandcoroutiens.pojo.response.Post
import com.group.koinandcoroutiens.utils.RecyclerViewOnClickListener

class PostsAdapter(private val mRecyclerViewOnClickListener: RecyclerViewOnClickListener) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var list = ArrayList<Post>()
    companion object {
        private val TAG = PostsAdapter::class.java.simpleName
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_post,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post,) {

            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body



            binding.root.setOnClickListener {
                Log.e(TAG, "bind: post id ${post.id}" )
                mRecyclerViewOnClickListener.onRootClickListener(
                    binding,
                    post
                )
            }

            binding.executePendingBindings()

        }
    }

    fun submitData(data: ArrayList<Post>) {
        this.list = data
        notifyDataSetChanged()
    }


}