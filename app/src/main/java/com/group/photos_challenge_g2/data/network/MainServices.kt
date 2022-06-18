package com.group.photos_challenge_g2.data.network

import com.group.photos_challenge_g2.pojo.response.AlbumsResponse
import com.group.photos_challenge_g2.pojo.response.PhotosResponse
import com.group.photos_challenge_g2.pojo.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainServices {

    @GET("users")
    suspend fun users(): Response<UsersResponse>

    @GET("albums")
    suspend fun albums(
        @Query("userId") userId: Int
    ): Response<AlbumsResponse>

    @GET("photos")
    suspend fun photos(
        @Query("albumId") albumId: Int
    ): Response<PhotosResponse>
}