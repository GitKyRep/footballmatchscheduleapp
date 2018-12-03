package com.example.rikis.footballmatchscheduleapp.data

import com.google.gson.annotations.SerializedName

data class Players(
        @SerializedName("idPlayer")
        var idPlayer:String? = null,

        @SerializedName("strPlayer")
        var strPlayer:String? = null,

        @SerializedName("strPosition")
        var strPosition:String? = null,

        @SerializedName("strHeight")
        var strHeight: String? = null,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null,

        @SerializedName("strThumb")
        var strThumb: String? = null,

        @SerializedName("strFanart1")
        var strFanart1: String? = null,

        @SerializedName("strFanart2")
        var strFanart2: String? = null,

        @SerializedName("strFanart3")
        var strFanart3: String? = null,

        @SerializedName("strFanart4")
        var strFanart4: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null

)