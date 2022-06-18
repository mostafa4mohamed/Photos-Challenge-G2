package com.group.photos_challenge_g2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.group.photos_challenge_g2.pojo.response.Album
import com.group.photos_challenge_g2.pojo.response.Photo
import com.group.photos_challenge_g2.pojo.response.User

@Database(entities = [User::class, Album::class, Photo::class], version = 1, exportSchema = false)
@TypeConverters(ConverterHelper::class)
abstract class RoomManger : RoomDatabase() {

    abstract fun mainDAO(): MainDAO

}