package com.group.photos_challenge_g2.ui.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.photos_challenge_g2.data.local.MainDAO
import com.group.photos_challenge_g2.data.network.MainServices
import com.group.photos_challenge_g2.pojo.response.Photo
import com.group.photos_challenge_g2.pojo.response.PhotosResponse
import com.group.photos_challenge_g2.utils.Constants
import com.group.photos_challenge_g2.utils.NetworkState
import com.group.photos_challenge_g2.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class PhotosViewModel @Inject constructor(private val repository: PhotosRepository) :
    ViewModel() {

    private val _dataStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val dataStateFlow: MutableStateFlow<NetworkState> get() = _dataStateFlow

    private val _searchStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val searchStateFlow: MutableStateFlow<NetworkState> get() = _searchStateFlow

    fun photos(albumId: Int) {

        _dataStateFlow.value = NetworkState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                repository.photos(albumId)
            }.onFailure {

                when (it) {
                    is UnknownHostException ->
                        _dataStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    is java.net.ConnectException ->
                        _dataStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    else -> _dataStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                }

            }.onSuccess { result ->

                if (result is Response<*>) {

                    if (result.body() != null) {
                        _dataStateFlow.value = NetworkState.Result(result.body())
                        repository.cashing(result.body() as PhotosResponse)
                    } else
                        _dataStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)

                } else {

                    val data = PhotosResponse()
                    data.addAll(result as List<Photo>)

                    _dataStateFlow.value = NetworkState.Result(data)
                }
            }

        }
    }

    fun search(key: String) {

        _searchStateFlow.value = NetworkState.Loading

        if (_dataStateFlow.value is NetworkState.Result<*>) {

            val data = (_dataStateFlow.value as NetworkState.Result<*>).response as PhotosResponse

            val result = data.filter {
                it.thumbnailUrl.contains(key)
            }

            val finalResult = PhotosResponse()
            finalResult.addAll(result)

            _searchStateFlow.value = NetworkState.Result(finalResult)
        }

    }

}


class PhotosRepository @Inject constructor(
    private val mainServices: MainServices,
    private val mainDAO: MainDAO
) {

    suspend fun photos(albumId: Int): Any {
        return if (Utils.isInternetAvailable()) {
            remote(albumId)
        } else {
            offLine(albumId)
        }
    }


    private suspend fun remote(albumId: Int) = mainServices.photos(albumId)

    private fun offLine(albumId: Int) = mainDAO.photos(albumId)

    fun cashing(body: List<Photo>) {

        CoroutineScope(Dispatchers.IO).launch {
            mainDAO.insertPhotos(body)
        }

    }

}