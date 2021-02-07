package com.group.koinandcoroutiens.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group.koinandcoroutiens.pojo.response.Post

@Dao
interface PostsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(data: List<Post>)

    @Query("SELECT * FROM Post")
    fun posts(): List<Post>

}