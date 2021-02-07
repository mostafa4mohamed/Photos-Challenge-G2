package com.group.koinandcoroutiens.utils

import android.app.Application
import com.group.koinandcoroutiens.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidLogger()//Level.NONE
            androidContext(this@BaseApp)
            modules(
                listOf(
                   Modules.modules
                )
            )

        }


    }
}