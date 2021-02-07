package com.group.koinandcoroutiens.ui.posts


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.databinding.FragmentPostsBinding
import com.group.koinandcoroutiens.pojo.response.Post
import com.group.koinandcoroutiens.utils.Constants
import com.group.koinandcoroutiens.utils.RecyclerViewOnClickListener
import org.koin.android.ext.android.inject

class PostsFragment : Fragment(), RecyclerViewOnClickListener {

    private lateinit var postsAdapter: PostsAdapter
    private lateinit var binding: FragmentPostsBinding
    private val viewModel: PostsViewModel by inject()

    companion object {
        private val TAG = PostsFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = this
        postsAdapter = PostsAdapter(this)

        binding.rvMain.adapter = postsAdapter

        viewModel.loadPosts(binding)

        viewModel.mutableLiveData.observe(viewLifecycleOwner, { t ->

            postsAdapter.submitData(t as ArrayList<Post>)
            binding.progressBar.visibility = GONE

        })


    }

    override fun <UI, D> onRootClickListener(dataBinding: UI, data: D) {

        val post = data as Post

        Log.e(TAG, "onRootClickListener: post id ${post.id}")
        Log.e(TAG, "post id ------------------------------------")

        val bundle = Bundle()
        bundle.putInt(Constants.POST_ID, post.id)

        findNavController().navigate(R.id.commentsFragment, bundle)

    }
}