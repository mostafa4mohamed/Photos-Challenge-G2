package com.group.koinandcoroutiens.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.group.koinandcoroutiens.pojo.response.PhotosResponseItem

@Database(entities = [PhotosResponseItem::class], version = 1, exportSchema = false)
abstract class RoomManger : RoomDatabase() {

    abstract fun getDao(): DAO

    companion object {

        @Volatile
        private var instance: RoomManger? = null

        fun instance(context: Context): RoomManger? {
            if (instance == null) {
                synchronized(RoomManger::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomManger::class.java,
                        "Photos"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

    }


}