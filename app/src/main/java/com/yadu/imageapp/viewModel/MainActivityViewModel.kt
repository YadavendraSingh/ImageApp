package com.yadu.imageapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yadu.imageapp.data.repository.NetworkState
import com.yadu.imageapp.data.vo.Image
import com.yadu.imageapp.view.ui.ImagePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val imageRepository:ImagePagedListRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val imagePagedList: LiveData<PagedList<Image>> by lazy {
        imageRepository.fetchLiveImagePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        imageRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return imagePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}