package com.group.photos_challenge.ui.photos

import androidx.lifecycle.ViewModel
import com.group.photos_challenge.data.network.PhotosServices
import com.group.photos_challenge.data.local.RoomManger
import com.group.photos_challenge.pojo.response.Photo
import com.group.photos_challenge.pojo.response.Photos
import com.group.photos_challenge.pojo.response.PhotosResponse
import com.group.photos_challenge.utils.Constants
import com.group.photos_challenge.utils.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PhotosViewModel(
    private var mPhotosServices: PhotosServices,
    private var roomManger: RoomManger
) : ViewModel() {

    private val _photosStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val photosStateFlow get() = _photosStateFlow

    fun loadPosts(page: Int, perPage: Int, pages: Int, total: Int) {

        _photosStateFlow.value = NetworkState.Loading

        CoroutineScope(Dispatchers.IO).launch {

            runCatching { mPhotosServices.getPhotos(page = page) }
                .onSuccess {

                    if (it.isSuccessful && it.body() != null) {

                        _photosStateFlow.value = NetworkState.Result(it.body())

                        cashing(it.body()!!.photos.photo)

                    } else {
                        _photosStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                    }

                }.onFailure {

                    if (it is UnknownHostException) {
                        offLine(page = page, per_page = perPage, _pages = pages, _total = total)
                    } else {
                        _photosStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    }

                }

        }

    }

    private fun offLine(page: Int, per_page: Int, _pages: Int, _total: Int) {

        val perPage = if (per_page == 0) 20 else per_page
        var total = _total
        var pages = _pages

        CoroutineScope(Dispatchers.IO).launch {

            if (_total == 0) {
                total = roomManger.photosDAO().count()
                pages = total / perPage
            }

            val data = roomManger.photosDAO().photos(perPage, (page - 1) * perPage)

            if (data.isNotEmpty()) {

                _photosStateFlow.value =
                    NetworkState.Result(
                        PhotosResponse(
                            photos = Photos(
                                page = page,
                                pages = pages,
                                perpage = perPage,
                                photo = data,
                                total = total
                            ), "ok"
                        )
                    )

            } else {

                _photosStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)

            }
        }

    }

    private fun cashing(body: List<Photo>) {

        CoroutineScope(Dispatchers.IO).launch {
            roomManger.photosDAO().insertPhotos(body)
        }

    }

    companion object {
        private val TAG = PhotosViewModel::class.java.simpleName
    }

}

