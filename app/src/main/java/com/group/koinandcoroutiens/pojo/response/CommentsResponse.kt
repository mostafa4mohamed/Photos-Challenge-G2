package com.group.koinandcoroutiens.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class CommentsResponse : ArrayList<Comment>()

@Entity
data class Comment(
    @ColumnInfo val body: String,
    @ColumnInfo val email: String,
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val postId: Int
)