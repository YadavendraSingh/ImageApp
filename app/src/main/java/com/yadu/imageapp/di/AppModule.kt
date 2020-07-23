package com.yadu.imageapp.di

import com.yadu.imageapp.data.api.PixabayClient
import com.yadu.imageapp.data.api.PixabayInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): PixabayInterface = PixabayClient.getClient()
}