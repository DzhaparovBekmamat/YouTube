package com.template.youtubekg.core.di

import com.template.youtubekg.ui.detail.DetailViewModel
import com.template.youtubekg.ui.playlist.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}