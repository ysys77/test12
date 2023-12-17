package com.dnx.japan.sharedplatform.network

import okhttp3.Response
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

/**
 *  NetworkException.kt
 *
 *  Created by jangwon on 2022/06/20
 *
 */
sealed class NetworkException(errorMessage: String) : Exception(errorMessage) {


    /*companion object {
        fun handleNetworkException(response: Response) {
            if (response.code == 500) {

                //throw response.message)
            }
        }

        fun handleNetworkException(e: Exception): Exception {
            return when (e) {

             is JSONException, is SocketTimeoutException, is SSLException, is ConnectException -> {
                    UIException.InitException(e.message ?: "An unexpected error occurred")
                }
                else -> {
                    UIException.InitException(e.message ?: "An unexpected error occurred")
                }
            }
        }
    }*/
}