package com.dnx.japan.sharedplatform.data.repository.model

import com.google.gson.annotations.SerializedName

data class SampleResponse(
    @SerializedName("fapicallresult")
    val fapicallresult: String
)