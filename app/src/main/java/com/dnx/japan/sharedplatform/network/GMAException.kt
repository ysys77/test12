package com.shinhan.gma.core.network

import android.util.Log
import org.json.JSONObject

/**
 *  GMAException.kt
 *
 */
sealed class GMAException(errorMessage: String) : Exception(errorMessage) {

    companion object {
        fun checkGMAException(response: String?) {
            val resJSONObject = JSONObject(response ?: "{}")
            val elHeader = resJSONObject.optJSONObject("elHeader") ?: JSONObject()
            val resSuc = elHeader.optBoolean("resSuc", false)
            val resMsgVo = elHeader.optJSONObject("resMsgVo") ?: JSONObject()
            val msgType = resMsgVo.optString("msgType", "E")

            if (!resSuc) {
                val resMsg = System.getProperty("line.separator")
                    ?.let { elHeader.optString("resMsg").replace("<br/>", it) }
                val resCode = elHeader.optString("resCode")
                Log.d("GMAException", "errMes: $resMsg")
                Log.d("GMAException", "errCode: $resCode")
                /*when (msgType) {
                    "I" -> {
                        throw UIException.InitException(resMsg ?: "")
                    }
                    "M" -> {
                        throw UIException.MainException(resMsg ?: "")
                    }
                    "E" -> {
                        throw UIException.ErrorException(resMsg ?: "", resCode)
                    }
                    "W" -> {
                        throw UIException.WarnException(resMsg ?: "", resCode)
                    }
                    "C" -> {
                        throw UIException.CustomException(resMsg ?: "", resCode)
                    }
                }*/
            }
        }
    }
}