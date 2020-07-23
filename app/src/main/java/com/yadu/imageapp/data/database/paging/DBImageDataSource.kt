package com.yadu.imageapp.data.database.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yadu.imageapp.data.api.FIRST_PAGE
import com.yadu.imageapp.data.api.IMAGE_PER_PAGE
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.database.DataDao
import com.yadu.imageapp.data.database.toDataList
import com.yadu.imageapp.data.repository.NetworkState
import com.yadu.imageapp.data.vo.Image
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DBImageDataSource(private val dao: DataDao, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Image>() {
    private var page = FIRST_PAGE
    private var perPage = IMAGE_PER_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, Image>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            dao.getDataByPage(page)
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback.onResult(it.toDataList(), null, page+1)
                networkState.postValue(NetworkState.LOADED)
            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("DBImageDataSource", it.message)
                }))

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(dao!!.getDataByPage(params.key)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if(it.toDataList().isNotEmpty()) {
                    callback.onResult(it.toDataList(), params.key+1)
                    networkState.postValue(NetworkState.LOADED)
                }
                else{
                    networkState.postValue(NetworkState.ENDOFLIST)
                }
            },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("DBImageDataSource", it.message)
                }))
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Image>) {
        TODO("Not yet implemented")
    }

}