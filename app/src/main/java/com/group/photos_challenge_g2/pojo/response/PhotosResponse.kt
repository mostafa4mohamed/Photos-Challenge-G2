package com.group.photos_challenge_g2.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class PhotosResponse : ArrayList<Photo>()

@Entity
data class Photo(
    @ColumnInfo val albumId: Int,
    @PrimaryKey val id: Int,
    @ColumnInfo val thumbnailUrl: String,
    @ColumnInfo val title: String,
    @ColumnInfo val url: String
)