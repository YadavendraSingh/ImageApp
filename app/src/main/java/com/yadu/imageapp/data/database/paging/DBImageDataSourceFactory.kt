package com.yadu.imageapp.data.database.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.database.DataDao
import com.yadu.imageapp.data.repository.ImageDataSource
import com.yadu.imageapp.data.vo.Image
import io.reactivex.disposables.CompositeDisposable

class DBImageDataSourceFactory (private val dao: DataDao, private val compositeDisposable: CompositeDisposable):
    DataSource.Factory<Int, Image>() {

    val imageLiveDataSource =  MutableLiveData<DBImageDataSource>()
    override fun create(): DataSource<Int, Image> {
        val imageDataSource = DBImageDataSource(dao,compositeDisposable)

        imageLiveDataSource.postValue(imageDataSource)
        return imageDataSource
    }
}