package com.example.rikis.footballmatchscheduleapp.api


import android.net.Uri
import com.example.rikis.footballmatchscheduleapp.BuildConfig

object TheSportDbApi{

    fun getNextEvent(leagueId:String?):String{

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", leagueId)
                .build()
                .toString()
    }

    fun getLastEvent (leagueId:String?):String{

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", leagueId)
                .build()
                .toString()
    }

    fun getDetailTeam(idTeam:String?):String{

        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", idTeam)
                .build()
                .toString()
    }
}