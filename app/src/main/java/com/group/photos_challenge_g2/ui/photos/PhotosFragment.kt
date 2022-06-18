package com.group.photos_challenge_g2.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.group.photos_challenge_g2.R
import com.group.photos_challenge_g2.databinding.FragmentPhotosBinding
import com.group.photos_challenge_g2.pojo.response.PhotosResponse
import com.group.photos_challenge_g2.utils.BaseFragment
import com.group.photos_challenge_g2.utils.Constants
import com.group.photos_challenge_g2.utils.NetworkState
import com.group.photos_challenge_g2.utils.Utils
import com.group.photos_challenge_g2.utils.Validator.validate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PhotosFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotosBinding
    private val viewModel: PhotosViewModel by viewModels()
    private var albumId: Int? = null

    @Inject
    lateinit var photosAdapter: PhotosAdapter

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


        setUp()
        data()
        observe()

    }

    private fun setUp() {

        albumId = requireArguments().getInt(Constants.ALBUM_ID)

        binding.rvMain.adapter = photosAdapter

        photosAdapter.setOnItemClickListener {

            val bundle = Bundle()
            bundle.putString(Constants.PHOTO_URL, it.thumbnailUrl)

            navController.navigate(R.id.photoDetailsFragment, bundle)

        }

        binding.incTb.tvSearch.setOnClickListener {
            if (validate()) {
                photosAdapter.clear()
                viewModel.search(binding.incTb.etSearch.text.toString())
            }
        }

    }

    private fun validate(): Boolean {
        if (binding.incTb.etSearch.validate())
            return true

        return false
    }

    private fun data() {

        viewModel.photos(albumId!!)
    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {

            viewModel.dataStateFlow.collect {
                when (it) {
                    is NetworkState.Idle -> {
                        return@collect
                    }
                    is NetworkState.Loading -> {
                        visProgress(true)
                    }
                    is NetworkState.Error -> {
                        visProgress(false)
                        it.handleErrors(mContext)
                    }
                    is NetworkState.Result<*> -> {
                        visProgress(false)
                        handleResult(it.response as PhotosResponse)

                    }
                }

            }
        }

        lifecycleScope.launchWhenStarted {

            viewModel.searchStateFlow.collect {
                when (it) {
                    is NetworkState.Idle -> {
                        return@collect
                    }
                    is NetworkState.Loading -> {
                        visProgress(true)
                    }
                    is NetworkState.Error -> {
                        visProgress(false)
                        it.handleErrors(mContext)
                    }
                    is NetworkState.Result<*> -> {
                        visProgress(false)
                        handleResult(it.response as PhotosResponse)

                    }
                }

            }
        }

    }

    private fun handleResult(response: PhotosResponse) {

        if (response.isEmpty())
            Utils.showToast(mContext, getString(R.string.empty_data))
        else
            ui(response)
    }

    private fun ui(data: PhotosResponse) {
        photosAdapter.submitList(data)
    }

    private fun visProgress(s: Boolean) {

        if (s)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE

    }

}