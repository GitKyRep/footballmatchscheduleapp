package com.example.rikis.footballmatchscheduleapp.api


import android.net.Uri
import com.example.rikis.footballmatchscheduleapp.BuildConfig

object TheSportDbApi {

    fun getNextEvent(leagueId: String?): String {

        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "eventsnextleague.php?id=" + leagueId

        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", leagueId)
                .build()
                .toString()*/
    }

    fun getLastEvent(leagueId: String?): String {

        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "eventspastleague.php?id=" + leagueId

        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", leagueId)
                .build()
                .toString()*/
    }

    fun getDetailTeam(idTeam: String?): String {

        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "lookupteam.php?id=" + idTeam

        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", idTeam)
                .build()
                .toString()*/
    }

    fun getDetailEvent(idEvent: String?): String {

        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "lookupevent.php?id=" + idEvent

        /*return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupevent.php")
                .appendQueryParameter("id", idEvent)
                .build()
                .toString()*/
    }
}