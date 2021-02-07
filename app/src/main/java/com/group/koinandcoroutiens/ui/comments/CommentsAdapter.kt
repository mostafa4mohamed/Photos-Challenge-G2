package com.group.koinandcoroutiens.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.ItemCommentBinding
import com.group.koinandcoroutiens.pojo.response.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    companion object {
//            private val TAG = MainAdapter::class.java.simpleName
    }

    private var list = ArrayList<Comment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_comment,
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {

            binding.tvEmail.text = comment.email
            binding.tvName.text = comment.name
            binding.tvBody.text = comment.body

            binding.executePendingBindings()

        }
    }

    fun submitData(data: ArrayList<Comment>) {
        this.list = data
        notifyDataSetChanged()
    }

}