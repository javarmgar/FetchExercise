package com.example.fetchexercise.framework.library.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> init(data: T?): Resource<T> {
            return Resource(Status.INIT, data, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> done(data: T?): Resource<T> {
            return Resource(Status.DONE, data,null )
        }

    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, message=$message)"
    }


}