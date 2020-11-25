package com.group.koinandcoroutiens.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group.koinandcoroutiens.pojo.response.PhotosResponseItem

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(data: List<PhotosResponseItem>)

    @Query("SELECT * FROM Photos")
    fun photos(): List<PhotosResponseItem>

}