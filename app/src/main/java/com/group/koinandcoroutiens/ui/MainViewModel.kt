package com.group.koinandcoroutiens.ui

import android.util.Log
import android.view.View.GONE
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.group.koinandcoroutiens.R
import com.group.koinandcoroutiens.data.network.HomeServices
import com.group.koinandcoroutiens.data.room.RoomManger
import com.group.koinandcoroutiens.databinding.ActivityMainBinding
import com.group.koinandcoroutiens.pojo.response.PhotosResponse
import com.group.koinandcoroutiens.pojo.response.PhotosResponseItem
import kotlinx.coroutines.*
import java.io.IOException
import java.net.UnknownHostException

//import kotlinx.coroutines.flow.*

class MainViewModel(private var mHomeServices: HomeServices) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    var mutableLiveData = MutableLiveData<List<PhotosResponseItem>>()
    private lateinit var binding: ActivityMainBinding

    suspend fun loadPhotosDataV1(_binding: ActivityMainBinding) {

        binding = _binding

        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {

            runCatching { mHomeServices.getPhotosV1() }
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
            val data = RoomManger.instance(binding.root.context)!!.getDao().photos()
            Log.e(TAG, "offLine: 2")

            Log.e(TAG, "offLine: data ${data.size}")

            if (data.isNotEmpty()) {
                mutableLiveData.value = data
                Log.e(TAG, "offLine: 3")

            } else {
                binding.progressBar.visibility = GONE
                Toast.makeText(
                    binding.root.context,
                    binding.root.context.getString(R.string.internet_connection),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "offLine: 4")

            }
        }

    }

    private fun saveDataAsLocal(body: PhotosResponse) {

        CoroutineScope(Dispatchers.IO).launch {
            RoomManger.instance(binding.root.context)!!.getDao().insertPhotos(body)
        }

    }

    private fun knowError() {
        Toast.makeText(
            binding.root.context,
            binding.root.context.getString(R.string.know_error),
            Toast.LENGTH_SHORT
        ).show()
        binding.progressBar.visibility = GONE
    }

/*

    suspend fun loadPhotosDataV2() {

        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {

            mHomeServices.getPhotosV2("en").catch { throwable ->
                Log.e(TAG, "loadPhotosDataV2: error ${throwable.message}")
            }.collect { t1 ->
                Log.e(TAG, "loadCataegoryDataV2-- : $t1")
                mutableLiveData.value = t1
            }
        }

    }

    @ExperimentalCoroutinesApi
    suspend fun loadPhotosDataV3() {

        withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {


//                mHomeServices.getPhotosV3("ar")

            runCatching { mHomeServices.getPhotosV3Async("en").await() }
                .onSuccess {

                    mutableLiveData.value = it

                }.onFailure {
                    Log.e(TAG, "loadPhotosDataV3: error ${it.localizedMessage}")
                }


        }

    }
*/

}

