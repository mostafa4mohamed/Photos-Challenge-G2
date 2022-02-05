package com.group.photos_challenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.group.photos_challenge.pojo.response.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class RoomManger : RoomDatabase() {

    abstract fun photosDAO(): PhotosDAO

}