package com.group.photos_challenge.data.network

import com.group.photos_challenge.pojo.response.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosServices {

    @GET("rest/")
    suspend fun getPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") api_key: String = "d17378e37e555ebef55ab86c4180e8dc",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 50,
        @Query("text") text: String = "Color",
        @Query("per_page") per_page: Int = 20,
        @Query("page") page: Int = 1
    ): Response<PhotosResponse>
}