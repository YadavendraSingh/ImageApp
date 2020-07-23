package com.yadu.imageapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yadu.imageapp.R
import com.yadu.imageapp.data.api.PixabayClient
import com.yadu.imageapp.data.api.PixabayInterface
import com.yadu.imageapp.data.repository.NetworkState
import com.yadu.imageapp.di.DaggerAppComponent
import com.yadu.imageapp.view.adapter.ImagePagedListAdapter
import com.yadu.imageapp.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var imageRepository:ImagePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        DaggerAppComponent.create().inject(this)

        val apiService:PixabayInterface = PixabayClient.getClient()
        imageRepository = ImagePagedListRepository(apiService)

        viewModel = getViewModel()

        val imageAdapter =
            ImagePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 1)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = imageAdapter.getItemViewType(position)
                if (viewType == imageAdapter.IMAGE_VIEW_TYPE) return  1    // IMAGE_VIEW_TYPE will occupy 1 out of 3 span
                else return 1                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };

        rv_image_list.layoutManager = gridLayoutManager
        rv_image_list.setHasFixedSize(true)
        rv_image_list.adapter = imageAdapter

        viewModel.imagePagedList.observe(this, Observer {
            imageAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                imageAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(
                    imageRepository
                ) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
