package com.example.rikis.footballmatchscheduleapp.data

import com.google.gson.annotations.SerializedName

data class ListTeam(
        @SerializedName("idTeam")
        var teamId:String? = null,

        @SerializedName("strTeam")
        var teamName:String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge:String? = null,

        @SerializedName("intFormedYear")
        var teamFormedYear: String? = null,

        @SerializedName("strStadium")
        var teamStadium: String? = null,

        @SerializedName("strDescriptionEN")
        var teamDescription: String? = null
)