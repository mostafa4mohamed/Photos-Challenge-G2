package com.group.photos_challenge_g2.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class AlbumsResponse : ArrayList<Album>()

@Entity
data class Album(
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val userId: Int
)