package com.yadu.imageapp.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import com.yadu.imageapp.ImageApplication
import com.yadu.imageapp.ImageApplication.Companion.database
import com.yadu.imageapp.data.api.IMAGE_PER_PAGE
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.database.paging.DBImageDataSourceFactory
import com.yadu.imageapp.data.repository.ImageDataSource
import com.yadu.imageapp.data.repository.ImageDataSourceFactory
import com.yadu.imageapp.data.repository.NetworkState
import com.yadu.imageapp.data.vo.Image
import com.yadu.imageapp.di.DaggerAppComponent
import io.reactivex.disposables.CompositeDisposable

class ImagePagedListRepository(private val apiService: PixabayInterface) {
    lateinit var imagePagedList: LiveData<PagedList<Image>>
    lateinit var imageDataSourceFactory: ImageDataSourceFactory
    lateinit var dbImageDataSourceFactory: DBImageDataSourceFactory

fun fetchLiveImagePagedList(compositeDisposable:CompositeDisposable):LiveData<PagedList<Image>>{
    imageDataSourceFactory = ImageDataSourceFactory(ImageApplication.database.dataDao(),apiService, compositeDisposable)
    dbImageDataSourceFactory = DBImageDataSourceFactory(ImageApplication.database.dataDao(), compositeDisposable)

    val config = PagedList.Config.Builder().setEnablePlaceholders(false)
        .setPageSize(IMAGE_PER_PAGE)
        .build()

    imagePagedList = LivePagedListBuilder(imageDataSourceFactory, config)
    //imagePagedList = LivePagedListBuilder(dbImageDataSourceFactory, config)
        .build()

    return imagePagedList
}

    init {
        DaggerAppComponent.create().inject(this)
    }
    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<ImageDataSource, NetworkState>(
            imageDataSourceFactory.imageLiveDataSource, ImageDataSource::networkState)
    }
}