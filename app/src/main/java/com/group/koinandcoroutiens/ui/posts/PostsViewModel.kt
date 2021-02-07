package com.group.koinandcoroutiens.ui.posts

import android.util.Log
import android.view.View.GONE
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.data.network.PostsServices
import com.group.koinandcoroutiens.data.room.RoomManger
import com.group.koinandcoroutiens.databinding.FragmentPostsBinding
import com.group.koinandcoroutiens.pojo.response.Post
import com.group.koinandcoroutiens.utils.UtilReference
import kotlinx.coroutines.*
import java.net.UnknownHostException

class PostsViewModel(
    private var mPostsServices: PostsServices,
    private var roomManger: RoomManger
) : ViewModel() {

    companion object {
        private val TAG = PostsViewModel::class.java.simpleName
    }

    var mutableLiveData = MutableLiveData<List<Post>>()
    private lateinit var binding: FragmentPostsBinding

     fun loadPosts(_binding: FragmentPostsBinding) {

        binding = _binding

        CoroutineScope(Dispatchers.Main).launch {

            runCatching { mPostsServices.getPosts() }
                .onSuccess {


                    if (it.isSuccessful) {
                        Log.i(TAG, it.body()!!.size.toString())
                        saveDataAsLocal(it.body()!!)
                        mutableLiveData.value = it.body()

                    } else {
                        knowError()
                        Log.i(TAG, it.message().toString())
                    }

                }.onFailure {


                    Log.e(TAG, "loadPhotosDataV1: javaClass : ${it.javaClass}")

                    if (it is UnknownHostException) {
                        Log.e(TAG, "loadPhotosDataV1: yes")
                        offLine()
                    } else {
                        Log.e(TAG, "loadPhotosDataV1: no")
                        knowError()
                    }

                    Log.e(TAG, "loadPhotosDataV1: error ${it.localizedMessage}")
                }

        }

    }

    private fun offLine() {

        CoroutineScope(Dispatchers.Main).launch {

            Log.e(TAG, "offLine: 1")
            val data = roomManger.getPostsDao().posts()
            Log.e(TAG, "offLine: 2")

            Log.e(TAG, "offLine: data ${data.size}")

            if (data.isNotEmpty()) {
                mutableLiveData.value = data
                Log.e(TAG, "offLine: 3")

            } else {
                binding.progressBar.visibility = GONE
                UtilReference.showSnackbar(
                    binding.root,
                    binding.root.context.getString(R.string.internet_connection),
                )
                Log.e(TAG, "offLine: 4")

            }
        }

    }

    private fun saveDataAsLocal(body: List<Post>) {

        CoroutineScope(Dispatchers.IO).launch {
            roomManger.getPostsDao().insertPosts(body)
        }

    }


    private fun knowError() {
        UtilReference.showSnackbar(
            binding.root,
            binding.root.context.getString(R.string.know_error),
        )
        binding.progressBar.visibility = GONE
    }

}

