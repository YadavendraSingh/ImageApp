package com.yadu.imageapp.data.database

import com.yadu.imageapp.data.vo.Image

fun DataEntity.toData() = Image(
    this.id,
    this.likes,
    this.user,
    this.webformatURL,
    this.userImageURL,
    this.page
)

fun List<DataEntity>.toDataList() = this.map { it.toData() }

fun Image.toDataEntity() = DataEntity(
    id = this.id,
    likes = this.likes,
    user = this.user,
    webformatURL = this.webformatURL,
    userImageURL = this.userImageURL,
    page = this.page
)

fun List<Image>.toDataEntityList() = this.map { it.toDataEntity() }