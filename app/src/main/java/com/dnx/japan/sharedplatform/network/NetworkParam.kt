package com.shinhan.gma.core.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

class NetworkParam() {
    private var json: JSONObject = JSONObject()

    init {
        try {
            json.putOpt(STR_ROOT_INFO, JSONObject())
            json.putOpt(STR_EL_DATA, JSONObject())
        } catch (e: JSONException) {
        }
    }

    private fun putHeader(key: String?, value: Any?) {
        try {
            json.optJSONObject(STR_ROOT_INFO)?.putOpt(key, value)
        } catch (e: Exception) {
        }
    }

    fun put(key: String?, value: Any?) {
        try {
            json.optJSONObject(STR_EL_DATA)?.putOpt(key, value)
        } catch (e: Exception) {
        }
    }

    fun makeJsonRequestBody(): RequestBody {
        return (replaceCharacter(toString()) ?: "").toRequestBody(CHARSET.toMediaTypeOrNull())
    }

    private fun replaceCharacter(param: String?): String? {
        return param?.replace("[+]".toRegex(), "%2B")
            ?.replace("&".toRegex(), "%26")
    }

    override fun toString(): String {
        return json.toString()
    }

    companion object {
        private const val STR_ROOT_INFO = "root_info"
        private const val STR_EL_DATA = "elData"
        private const val CHARSET = "application/json; charset=utf-8"
    }
}