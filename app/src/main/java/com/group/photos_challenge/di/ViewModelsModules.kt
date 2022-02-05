package com.group.photos_challenge.di

import com.group.photos_challenge.ui.photos.PhotosViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelsModules {

    val modules = module {

        viewModel { PhotosViewModel(get(),get()) }
    }


}