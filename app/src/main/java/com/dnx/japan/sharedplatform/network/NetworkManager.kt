package com.dnx.japan.sharedplatform.network

import android.telecom.Call
import android.webkit.CookieManager
import com.example.myapplication.BuildConfig
import com.shinhan.gma.core.network.GMAException
import com.shinhan.gma.core.network.NetworkCallback
import com.shinhan.gma.core.network.NetworkParam

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class NetworkManager {
    private val client by lazy {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .ignoreAllSSLErrors()
            .cookieJar(JavaNetCookieJar(java.net.CookieManager()))
    }

    private var baseUrl = "https://gcodev.shinhanglobal.com"
    private var sessionId = ""

    fun request(
        type: String,
        api: String,
        param: NetworkParam,
        callback: NetworkCallback,
        echoMode: Boolean = false
    ) {
        val request = Request.Builder()
            .url(baseUrl + api)
            .post(body = param.makeJsonRequestBody())
            .header("Proworks-lang", type)
            .header("echoMode", echoMode.toString())
            .build()

        /*client.build().newCall(request = request).enqueue(object : Callback {

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    callback.onFail(NetworkException.handleNetworkException(e))

                }

                override fun onResponse(call: okhttp3.Call, response: Response) {
                    try {
                        val responseBody = response.body?.string()
                        // 1. network exception
                        //NetworkException.handleNetworkException(response)
                        // 2. gma exception
                        //GMAException.checkGMAException(responseBody)
                        // 3. success
                        callback.onSuccess(responseBody ?: "")
                        setCookie(response)
                    } catch (e: Exception) {
                        callback.onFail(e)
                    }
                }
            })*/
    }

    private fun setCookie(response: Response) {
        response.headers["Set-Cookie"]?.let {
            sessionId = it
            CookieManager.getInstance().apply {
                removeAllCookies(null)
                setCookie(baseUrl, sessionId)
                flush()
            }
        }
    }

    private fun getGtraceId(screenId: String): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val date = sdf.format(Date())
        val group = "152"
        val time = System.currentTimeMillis()
        return date + group + screenId + time
    }

    fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("SSL").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier(HostnameVerifier { _, _ -> true })
        return this
    }

    companion object {
        @Volatile
        private var instance: NetworkManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: NetworkManager().also {
                    instance = it
                }
            }
    }
}