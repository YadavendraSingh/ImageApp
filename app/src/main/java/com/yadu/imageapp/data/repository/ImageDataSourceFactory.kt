package com.yadu.imageapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.database.DataDao
import com.yadu.imageapp.data.vo.Image
import io.reactivex.disposables.CompositeDisposable


class ImageDataSourceFactory(private val dao: DataDao, private val apiService: PixabayInterface, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, Image>() {

    val imageLiveDataSource =  MutableLiveData<ImageDataSource>()
    override fun create(): DataSource<Int, Image> {
        val imageDataSource = ImageDataSource(dao,apiService,compositeDisposable)

        imageLiveDataSource.postValue(imageDataSource)
        return imageDataSource
    }
}