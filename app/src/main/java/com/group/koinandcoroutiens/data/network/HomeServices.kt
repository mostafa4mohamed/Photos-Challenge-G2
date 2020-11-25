package com.group.koinandcoroutiens.data.network

import com.group.koinandcoroutiens.pojo.response.PhotosResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface HomeServices {

    @GET("photos")
    suspend fun getPhotosV1(): Response<PhotosResponse>

    @GET("photos")
    fun getPhotosV2(): Flow<PhotosResponse>

    @GET("photos")
    suspend fun getPhotosV3Async(): Deferred<PhotosResponse>


}