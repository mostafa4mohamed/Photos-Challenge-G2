package com.group.photos_challenge_g2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.group.photos_challenge_g2.pojo.response.Album
import com.group.photos_challenge_g2.pojo.response.Photo
import com.group.photos_challenge_g2.pojo.response.User

@Dao
interface MainDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(data: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(data: List<Album>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(data: List<Photo>)

    @Query("SELECT * FROM User")
    fun users(): List<User>

    @Query("SELECT * FROM Album WHERE userId =:userId")
    fun albums(userId: Int): List<Album>

    @Query("SELECT * FROM Photo WHERE albumId =:albumId")
    fun photos(albumId: Int): List<Photo>

}