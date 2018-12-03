package com.example.rikis.footballmatchscheduleapp.data

data class FavoritesEvent(val id: Long?, val eventId: String?, val homeTeam: String?, val awayTeam: String?, val dateEvent: String?,val timeEvent: String?, val homeScore: String?, val awayScore: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val TIME_EVENT: String = "TIME_EVENT"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"

    }
}