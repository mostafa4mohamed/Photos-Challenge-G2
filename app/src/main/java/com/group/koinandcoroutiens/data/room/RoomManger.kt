package com.group.koinandcoroutiens.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.group.koinandcoroutiens.pojo.response.Comment
import com.group.koinandcoroutiens.pojo.response.Post

@Database(entities = [Post::class, Comment::class], version = 1, exportSchema = false)
abstract class RoomManger : RoomDatabase() {

    abstract fun getPostsDao(): PostsDAO

    abstract fun getCommentsDAO(): CommentsDAO

}