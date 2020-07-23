package com.yadu.imageapp.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.yadu.imageapp.data.api.DATABASE_NAME
import com.yadu.imageapp.data.database.paging.DBImageDataSourceFactory
import com.yadu.imageapp.data.vo.Image
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Database(entities = [DataEntity::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {

        @Volatile // All threads have immediate access to this property
        private var instance: ImageDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ImageDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }


}