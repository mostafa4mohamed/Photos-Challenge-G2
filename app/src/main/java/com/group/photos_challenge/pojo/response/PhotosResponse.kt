package com.group.photos_challenge.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PhotosResponse(
    val photos: Photos,
    val stat: String
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)

@Entity
data class Photo(
    @ColumnInfo val farm: Int,
    @PrimaryKey val id: String,
    @ColumnInfo val isfamily: Int,
    @ColumnInfo val isfriend: Int,
    @ColumnInfo val ispublic: Int,
    @ColumnInfo val owner: String,
    @ColumnInfo val secret: String,
    @ColumnInfo val server: String,
    @ColumnInfo val title: String
)