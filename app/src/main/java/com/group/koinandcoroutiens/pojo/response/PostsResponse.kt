package com.group.koinandcoroutiens.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class PostsResponse : ArrayList<Post>()

@Entity
data class Post(
    @ColumnInfo val body: String,
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val userId: Int
)