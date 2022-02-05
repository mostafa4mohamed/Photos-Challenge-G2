package com.group.photos_challenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group.photos_challenge.pojo.response.Photo

@Dao
interface PhotosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(data: List<Photo>)

    @Query("SELECT * FROM Photo LIMIT :start,:count_of_items_at_page ")
    fun photos(count_of_items_at_page: Int, start: Int): List<Photo>

    @Query("SELECT COUNT(*) FROM Photo")
    fun count():Int

}