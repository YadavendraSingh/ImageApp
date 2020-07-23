package com.yadu.imageapp.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

open class NetworkState(val status: Status, val msg: String) {

    companion object{
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "You have reached the end")

    }

}