package com.template.youtubekg.core.di

import com.template.youtubekg.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository(get()) }
}