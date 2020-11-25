package com.group.koinandcoroutiens.pojo.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class PhotosResponse : ArrayList<PhotosResponseItem>()

@Entity(tableName="Photos")
data class PhotosResponseItem(
    @ColumnInfo val albumId: Int,
    @PrimaryKey val id: Int,
    @ColumnInfo val thumbnailUrl: String,
    @ColumnInfo val title: String,
    @ColumnInfo val url: String
)