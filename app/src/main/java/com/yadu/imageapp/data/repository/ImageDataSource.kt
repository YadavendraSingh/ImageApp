package com.yadu.imageapp.data.repository

import android.provider.Contacts
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yadu.imageapp.ImageApplication
import com.yadu.imageapp.data.api.FIRST_PAGE
import com.yadu.imageapp.data.api.IMAGE_PER_PAGE
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.database.DataDao
import com.yadu.imageapp.data.database.toDataEntityList
import com.yadu.imageapp.data.database.toDataList
import com.yadu.imageapp.data.vo.Image
import com.yadu.imageapp.data.vo.ImageList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class ImageDataSource(private val dao: DataDao, private val apiService:PixabayInterface, private val compositeDisposable:CompositeDisposable): PageKeyedDataSource<Int, Image>() {
    private var page = FIRST_PAGE
    private var perPage = IMAGE_PER_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Image>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getImageResults(page, perPage)
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it.hits, null, page+1)
                insertData(it, page)
                networkState.postValue(NetworkState.LOADED)
            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("ImageDataSource", it.message)
                    //load from database
                    getFromDataBaseInitial(callback)
                }))

    }

    fun getFromDataBaseInitial(callback: LoadInitialCallback<Int, Image>){
        compositeDisposable.add(
            dao.getDataByPage(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.toDataList(),  null, page+1)
                    if(it.isEmpty()){
                        networkState.postValue(NetworkState.ERROR)
                    }else{
                        networkState.postValue(NetworkState.LOADED)
                    }
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("DBImageDataSource", it.message)
                    }))
    }

    fun insertData(imageList:ImageList, page:Int){
        //set current page for each object
        var list =imageList.hits
        list.forEach{x->x.page=page}
        val entityList = list.toDataEntityList()
        ImageApplication.database.apply {
            dataDao().insertData(entityList)
        }
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(apiService.getImageResults(params.key, perPage)
            .subscribeOn(Schedulers.io())
            .subscribe({
                var total:Int = it.total/20
                if(total >= params.key) {
                    insertData(it, params.key)
                    callback.onResult(it.hits, params.key+1)
                    networkState.postValue(NetworkState.LOADED)
                }
                else{
                    networkState.postValue(NetworkState.ENDOFLIST)
                }
            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("ImageDataSource", it.message)
                    //load from database
                    getFromDatabaseLoadAfter(callback, params.key)
                }))
    }

    fun getFromDatabaseLoadAfter(callback: LoadCallback<Int, Image>, key:Int ){
        compositeDisposable.add(
            dao.getDataByPage(key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.toDataList(),  key+1)
                    if(it.isEmpty()){
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }else{
                        networkState.postValue(NetworkState.LOADED)
                    }

                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("DBImageDataSource", it.message)
                    }))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        TODO("Not yet implemented")
    }

}