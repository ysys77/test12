package com.dnx.japan.sharedplatform.data.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object AppUtil {
    private val TAG = javaClass.simpleName


    @RequiresApi(Build.VERSION_CODES.O)
    fun HmacSHA256_3() {

        val message = "fapiuser1@sjbbank.co.jp"
        val secret = "uibank_fapi_par"

        try {
            val sha256_HMAC = Mac.getInstance("HmacSHA256")
            val secret_key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            sha256_HMAC.init(secret_key)
            val raw = sha256_HMAC.doFinal(message.toByteArray())

            Log.d("StartActivity", "HmacSHA256_3 byteToString: ${byteToString(raw)}")

            /*val base64Urlencoder = Base64.getUrlEncoder().encodeToString(raw)
            Log.d("StartActivity", "HmacSHA256_3 base64 getUrlEncoder: ${base64Urlencoder}")
            val base642 = Base64.getEncoder().encodeToString(raw)
            Log.d("StartActivity", "HmacSHA256_3 base64 getEncoder: ${base642}")*/
            //NnRlMaaMpJbgetQKm3McknN17pZzVenF2dEYdRbqqT4=

            val rawString = byteToString(raw)
            Log.d("StartActivity", "HmacSHA256_3 rawString: ${rawString}") //
            val encoded = Base64.getEncoder().encodeToString(rawString?.toByteArray())
            Log.d("StartActivity", "HmacSHA256_3 base64rawEncoded: ${encoded}")
            //MzY3NDY1MzFhNjhjYTQ5NmUwN2FkNDBhOWI3MzFjOTI3Mzc1ZWU5NjczNTVlOWM1ZDlkMTE4NzUxNmVhYTkzZQ==
            //ios MzY3NDY1MzFhNjhjYTQ5NmUwN2FkNDBhOWI3MzFjOTI3Mzc1ZWU5NjczNTVlOWM1ZDlkMTE4NzUxNmVhYTkzZQ==

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    // byte[]의 값을 16진수 형태의 문자로 변환하는 함수
    private fun byteToString(hash: ByteArray): String? {
        val buffer = StringBuffer()
        for (i in hash.indices) {
            var d = hash[i].toInt()
            d += if (d < 0) 256 else 0
            if (d < 16) {
                buffer.append("0")
            }
            buffer.append(Integer.toString(d, 16))
        }
        return buffer.toString()
    }

}