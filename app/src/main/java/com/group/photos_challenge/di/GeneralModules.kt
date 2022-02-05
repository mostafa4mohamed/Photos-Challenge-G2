package com.group.photos_challenge.di

import androidx.room.Room
import com.group.photos_challenge.data.local.RoomManger
import com.group.photos_challenge.utils.Constants
import org.koin.dsl.module

object GeneralModules {

    val modules = module {

        single {
            Room.databaseBuilder(
                get(),
                RoomManger::class.java,
                Constants.DB_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }


}