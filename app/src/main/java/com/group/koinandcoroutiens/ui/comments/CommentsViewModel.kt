package com.group.koinandcoroutiens.ui.comments

import android.util.Log
import android.view.View.GONE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.data.network.CommentsServices
import com.group.koinandcoroutiens.data.room.RoomManger
import com.group.koinandcoroutiens.databinding.FragmentCommentsBinding
import com.group.koinandcoroutiens.pojo.response.Comment
import com.group.koinandcoroutiens.utils.UtilReference
import kotlinx.coroutines.*
import java.net.UnknownHostException

class CommentsViewModel(
    private var mCommentsServices: CommentsServices,
    private var roomManger: RoomManger
) : ViewModel() {

    companion object {
        private val TAG = CommentsViewModel::class.java.simpleName
    }

    var mutableLiveData = MutableLiveData<List<Comment>>()
    private lateinit var binding: FragmentCommentsBinding

    fun loadComments(_binding: FragmentCommentsBinding,postId:Int) {

        binding = _binding

        CoroutineScope(Dispatchers.Main).launch {

            runCatching { mCommentsServices.getComments(postId) }
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
                        offLine(postId)
                    } else {
                        Log.e(TAG, "loadPhotosDataV1: no")
                        knowError()
                    }

                    Log.e(TAG, "loadPhotosDataV1: error ${it.localizedMessage}")
                }

        }

    }

    private fun offLine(postId:Int) {

        CoroutineScope(Dispatchers.Main).launch {

            Log.e(TAG, "offLine: 1")
            val data = roomManger.getCommentsDAO().comments(postId )
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

    private fun saveDataAsLocal(body: List<Comment>) {

        CoroutineScope(Dispatchers.IO).launch {
            roomManger.getCommentsDAO().insertComments(body)
        }

    }

    private fun knowError() {
        UtilReference.showSnackbar(
            binding.root,
            binding.root.context.getString(R.string.know_error)
//            R.color.purple_200
        )
        binding.progressBar.visibility = GONE
    }

}

