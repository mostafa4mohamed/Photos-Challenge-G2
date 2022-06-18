package com.group.photos_challenge_g2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.group.photos_challenge_g2.R
import com.group.photos_challenge_g2.databinding.FragmentHomeBinding
import com.group.photos_challenge_g2.pojo.response.*
import com.group.photos_challenge_g2.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var usersAdapter: UsersAdapter

    @Inject
    lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUp()
        users()
        observe()

    }

    private fun setUp() {

        binding.rvUsers.adapter = usersAdapter
        binding.rvAlbums.adapter = albumsAdapter

        binding.rvAlbums.layoutManager = Utils.getVerticalLayoutManager(mContext)

        usersAdapter.setOnItemClickListener {

            albumsAdapter.clear()
            albums(it.id)

        }


        albumsAdapter.setOnItemClickListener {

            val bundle = Bundle()
            bundle.putInt(Constants.ALBUM_ID, it.id)

            navController.navigate(R.id.photosFragment, bundle)

        }

    }

    private fun users() {

        viewModel.users()
    }

    private fun albums(userId: Int) {
        viewModel.albums(userId)
    }

    private fun observe() {

        lifecycleScope.launchWhenStarted {

            viewModel.usersStateFlow.collect {
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
                        handleResult(it.response as UsersResponse)

                    }
                }

            }
        }

        lifecycleScope.launchWhenStarted {

            viewModel.albumsStateFlow.collect {
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
                        handleResult(it.response as AlbumsResponse)

                    }
                }

            }
        }

    }

    private fun <T> handleResult(response: T) {

        when (response) {
            is UsersResponse -> {

                if (response.isEmpty())
                    Utils.showToast(mContext, getString(R.string.empty_data))
                else
                    uiUsers(response)

            }
            is AlbumsResponse -> {

                if (response.isEmpty())
                    Utils.showToast(mContext, getString(R.string.empty_data))
                else
                    uiAlbums(response)
            }

        }

    }

    private fun uiUsers(data: ArrayList<User>) {
        usersAdapter.submitList(data)
    }

    private fun uiAlbums(data: ArrayList<Album>) {

        binding.tvTitle.visibility = View.VISIBLE
        albumsAdapter.submitList(data)
    }

    private fun visProgress(s: Boolean) {
        if (s)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

}