package com.group.photos_challenge.ui.photos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.photos_challenge.R
import com.group.photos_challenge.databinding.FragmentPhotosBinding
import com.group.photos_challenge.pojo.response.Photo
import com.group.photos_challenge.pojo.response.PhotosResponse
import com.group.photos_challenge.utils.Constants
import com.group.photos_challenge.utils.NetworkState
import com.group.photos_challenge.utils.RecyclerViewOnClickListener
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class PhotosFragment : Fragment(), RecyclerViewOnClickListener {

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var binding: FragmentPhotosBinding
    private val viewModel: PhotosViewModel by inject()
    private lateinit var navController: NavController
    private lateinit var mContext: Context
    private var pageNum = 0
    private var pageSize = 0
    private var pages = 0
    private var total = 0
    private var isLastPage = false
    private var isLoading = false
    var loading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        mContext = requireContext()
        binding.lifecycleOwner = this
        photosAdapter = PhotosAdapter(this)

        binding.rvMain.adapter = photosAdapter

        data()
//        pagination()

    }


    private fun data(page: Int = 1) {

        viewModel.loadPosts(page = page, perPage = pageSize, pages = pages, total = total)

        lifecycleScope.launchWhenStarted {

            viewModel.photosStateFlow.collect {
                when (it) {
                    is NetworkState.Idle -> {
                        return@collect
                    }
                    is NetworkState.Loading -> {
                        if (page == 1) visMainProgress(true)
                        else visFooterProgress(true)
                    }
                    is NetworkState.Error -> {
                        if (page == 1) visMainProgress(true)
                        else visFooterProgress(true)
                        it.handleErrors(mContext)
                    }
                    is NetworkState.Result<*> -> {
                        if (page == 1) visMainProgress(false)
                        else visFooterProgress(false)

                        handleResult(it.response as PhotosResponse)

                    }
                }

            }
        }

    }

    private fun handleResult(response: PhotosResponse) {

        total = response.photos.total
        pageSize = response.photos.perpage
        pages = response.photos.pages

        pagination()

        ui(response.photos.photo as ArrayList<Photo>)

//        if (pageNum == 1)
//            test()

    }

    private fun ui(response: ArrayList<Photo>) {

        photosAdapter.submitData(response)

    }


    private fun pagination() {

        val layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        binding.rvMain.layoutManager = layoutManager

        binding.rvMain.addOnScrollListener(object :
            PaginationListener(layoutManager, pageSize) {

            override fun loadMoreItems() {

//                this@PhotosFragment.isLoading = true
                pageNum++

                if (pageNum <= pages) {
                    Log.e(TAG, "doPaginationApiCall: true")
                    isLoading = false
                } else {
                    Log.e(TAG, "doPaginationApiCall: false")
                    isLastPage = true
                }

                data(pageNum)
            }

            override var isLastPage: Boolean = false
                get() = this@PhotosFragment.isLastPage
            override var isLoading: Boolean = false
                get() = this@PhotosFragment.isLoading

        })
    }

    fun test() {

        Log.e(TAG, "onScrolled: 0")

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        val mLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvMain.layoutManager = mLayoutManager

        binding.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.e(TAG, "onScrolled: 1")
                if (dy > 0) { //check for scroll down
                    Log.e(TAG, "onScrolled: 2")
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        Log.e(TAG, "onScrolled: loading true")
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount
                            && pastVisiblesItems >= 0
                            && totalItemCount >= 10/*PAGE_SIZE*/) {
                            Log.e(TAG, "onScrolled: page true")
                            loading = false
                            pageNum++


                            if (pageNum <= pages) {
                                Log.e(TAG, "doPaginationApiCall: true")
                                loading = false
                            } else {
                                Log.e(TAG, "doPaginationApiCall: false")
                                isLastPage = true
                                return
                            }

                            data(pageNum)
                        } else {
                            Log.e(TAG, "onScrolled: page false")
                        }
                    } else {
                        Log.e(TAG, "onScrolled: loading false")

                    }
                } else {
                    Log.e(TAG, "onScrolled: 3")
                }
            }
        })


    }

    override fun onRootClickListener(url: String) {

        val bundle = Bundle()
        bundle.putString(Constants.PHOTO_URL, url)

        navController.navigate(R.id.photoDetailsFragment, bundle)

    }

    private fun visMainProgress(s: Boolean) {

        if (s)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun visFooterProgress(s: Boolean) {

        if (s)
            binding.itemLoading.visibility = View.VISIBLE
        else
            binding.itemLoading.visibility = View.GONE
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }

}