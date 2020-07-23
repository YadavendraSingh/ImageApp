package com.yadu.imageapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data")
data class DataEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "webformatURL")
    val webformatURL: String,
    @ColumnInfo(name = "userImageURL")
    val userImageURL: String,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "likes")
    val likes: Int,
    @ColumnInfo(name = "page")
    val page: Int

)