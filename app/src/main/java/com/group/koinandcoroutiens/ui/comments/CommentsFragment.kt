package com.group.koinandcoroutiens.ui.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.FragmentCommentsBinding
import com.group.koinandcoroutiens.pojo.response.Comment
import com.group.koinandcoroutiens.utils.Constants
import org.koin.android.ext.android.inject

class CommentsFragment : Fragment() {

    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var binding: FragmentCommentsBinding
    private val viewModel: CommentsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this
        commentsAdapter = CommentsAdapter()
        val postId=requireArguments().getInt(Constants.POST_ID)

        binding.rvComments.adapter = commentsAdapter

        viewModel.loadComments(binding,postId)

        viewModel.mutableLiveData.observe(viewLifecycleOwner, { t ->

            commentsAdapter.submitData(t as ArrayList<Comment>)
            binding.progressBar.visibility = View.GONE

        })


    }
}