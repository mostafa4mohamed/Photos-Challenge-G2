package com.group.koinandcoroutiens.data.network

import com.group.koinandcoroutiens.pojo.response.CommentsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentsServices {

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId:Int): Response<CommentsResponse>

}