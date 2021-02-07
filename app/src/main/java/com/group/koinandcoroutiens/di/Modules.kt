package com.group.koinandcoroutiens.di

import androidx.room.Room
import com.group.koinandcoroutiens.data.network.CommentsServices
import com.group.koinandcoroutiens.data.network.PostsServices
import com.group.koinandcoroutiens.data.room.RoomManger
import com.group.koinandcoroutiens.ui.comments.CommentsViewModel
import com.group.koinandcoroutiens.ui.posts.PostsViewModel
import com.group.koinandcoroutiens.utils.Constants
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Modules {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val modules = module {

        single {
            OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        }

        single {

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PostsServices::class.java)

        }

        single {

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CommentsServices::class.java)

        }

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

        viewModel { PostsViewModel(get(),get()) }
        viewModel { CommentsViewModel(get(),get()) }
    }


}