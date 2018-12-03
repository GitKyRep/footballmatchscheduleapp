package com.example.rikis.footballmatchscheduleapp.api


import com.example.rikis.footballmatchscheduleapp.BuildConfig

object TheSportDbApi {

    fun getNextEvent(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "eventsnextleague.php?id=" + leagueId
    }

    fun getLastEvent(leagueId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "eventspastleague.php?id=" + leagueId
    }

    fun getDetailTeam(idTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "lookupteam.php?id=" + idTeam
    }

    fun getDetailEvent(idEvent: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + "lookupevent.php?id=" + idEvent
    }

    fun getAllLeague(): String {
        //return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/all_leagues.php"
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/search_all_leagues.php?s=Soccer"

    }

    fun getTeams(league:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league

    }

    fun getTeamsByIdLeague(idLeague:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_teams.php?id=" + idLeague

    }

    fun getPlayers(idTeam:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + idTeam
    }

    fun getPlayerDetail(idPlayer:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + idPlayer
    }

    fun getSearchEvent(namaEvent:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + namaEvent
    }

    fun getSearchTeam(namaTeam:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + namaTeam
    }


}