package com.group.koinandcoroutiens.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group.koinandcoroutiens.pojo.response.Comment

@Dao
interface CommentsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(data: List<Comment>)

    @Query("SELECT * FROM Comment where postId=:postId")
    fun comments(postId:Int): List<Comment>

}