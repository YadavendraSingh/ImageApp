package com.yadu.imageapp.di

import com.yadu.imageapp.view.ui.ImagePagedListRepository
import com.yadu.imageapp.view.ui.MainActivity
import com.yadu.imageapp.viewModel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(imageRepository: ImagePagedListRepository)

    fun inject(viewModel: MainActivityViewModel)

    fun inject(mainActivity: MainActivity)
}