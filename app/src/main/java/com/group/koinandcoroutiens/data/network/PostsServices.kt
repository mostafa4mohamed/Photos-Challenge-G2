package com.group.koinandcoroutiens.data.network

import com.group.koinandcoroutiens.pojo.response.PostsResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostsServices {

    @GET("posts")
    suspend fun getPosts(): Response<PostsResponse>

}