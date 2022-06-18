package com.group.photos_challenge_g2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.photos_challenge_g2.data.local.MainDAO
import com.group.photos_challenge_g2.data.network.MainServices
import com.group.photos_challenge_g2.pojo.response.*
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
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private val _usersStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val usersStateFlow: MutableStateFlow<NetworkState> get() = _usersStateFlow

    private val _albumsStateFlow = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val albumsStateFlow: MutableStateFlow<NetworkState> get() = _albumsStateFlow

    fun users() {

        _usersStateFlow.value = NetworkState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                repository.users()
            }.onFailure {

                when (it) {
                    is UnknownHostException ->
                        _usersStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    is java.net.ConnectException ->
                        _usersStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    else -> _usersStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                }

            }.onSuccess { result ->

                if (result is Response<*>) {

                    if (result.body() != null) {
                        _usersStateFlow.value = NetworkState.Result(result.body())
                        repository.cashingUsers(result.body() as UsersResponse)
                    } else
                        _usersStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)

                } else {

                    val data = UsersResponse()
                    data.addAll(result as List<User>)

                    _usersStateFlow.value = NetworkState.Result(data)
                }
            }

        }
    }

    fun albums(userId: Int) {

        _albumsStateFlow.value = NetworkState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            kotlin.runCatching {
                repository.albums(userId)
            }.onFailure {

                when (it) {
                    is UnknownHostException ->
                        _albumsStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    is java.net.ConnectException ->
                        _albumsStateFlow.value = NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE)
                    else -> _albumsStateFlow.value =
                        NetworkState.Error(Constants.Codes.UNKNOWN_CODE)
                }

            }.onSuccess { result ->

                if (result is Response<*>) {

                    if (result.body() != null) {
                        _albumsStateFlow.value = NetworkState.Result(result.body())
                        repository.cashingAlbums(result.body() as AlbumsResponse)
                    } else
                        _albumsStateFlow.value = NetworkState.Error(Constants.Codes.UNKNOWN_CODE)

                } else {

                    val data = AlbumsResponse()
                    data.addAll(result as List<Album>)

                    _albumsStateFlow.value = NetworkState.Result(data)
                }
            }

        }
    }

}


class HomeRepository @Inject constructor(
    private val mainServices: MainServices,
    private val mainDAO: MainDAO
) {

    suspend fun users(): Any {
        return if (Utils.isInternetAvailable()) {
            remoteUsers()
        } else {
            offLineUsers()
        }
    }

    private suspend fun remoteUsers() = mainServices.users()

    private fun offLineUsers() = mainDAO.users()

    fun cashingUsers(body: List<User>) {

        CoroutineScope(Dispatchers.IO).launch {
            mainDAO.insertUsers(body)
        }

    }

    suspend fun albums(userId: Int): Any {
        return if (Utils.isInternetAvailable()) {
            remoteAlbums(userId)
        } else {
            offLineAlbums(userId)
        }
    }

    private suspend fun remoteAlbums(userId: Int) = mainServices.albums(userId)

    private fun offLineAlbums(userId: Int) = mainDAO.albums(userId)

    fun cashingAlbums(body: List<Album>) {

        CoroutineScope(Dispatchers.IO).launch {
            mainDAO.insertAlbums(body)
        }

    }

}