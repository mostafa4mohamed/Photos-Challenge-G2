package com.group.photos_challenge.di

import com.group.photos_challenge.data.network.PhotosServices
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModules {

    private const val BASE_URL = "https://www.flickr.com/services/"

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
                .build().create(PhotosServices::class.java)

        }

    }

}