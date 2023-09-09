package com.template.youtubekg.core.di

import com.template.youtubekg.core.network.networkModule

val koinModule = listOf(
    repositoryModule,
    remoteDataSourceModule,
    networkModule,
    viewModelModule
)