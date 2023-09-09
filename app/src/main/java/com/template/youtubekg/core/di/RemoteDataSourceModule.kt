package com.template.youtubekg.core.di

import com.template.youtubekg.core.network.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteDataSource(get()) }
}