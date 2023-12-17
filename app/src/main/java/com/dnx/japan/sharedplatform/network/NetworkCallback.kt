package com.shinhan.gma.core.network

/**
 *  NetworkCallback.kt
 *
 *  Created by jangwon on 2022/06/20
 *
 */
interface NetworkCallback {
    fun onSuccess(response: String)
    fun onFail(e: Exception)
}