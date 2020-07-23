package com.yadu.imageapp

import android.app.Application
import com.yadu.imageapp.data.database.ImageDatabase

class ImageApplication : Application() {

    companion object {
        lateinit var instance: ImageApplication
        lateinit var database: ImageDatabase
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = ImageDatabase.invoke(this)
    }
}