package com.example.rikis.footballmatchscheduleapp.data

data class League(var idLeague: String, var strLeague: String,var idCup: String) {

        override fun toString(): String {
                return strLeague
        }
}

/*
data class League(
        @SerializedName("idLeague")
        var idLeague: String? = null,
        @SerializedName("strLeague")
        var strLeague: String? = null
)
{
        override fun toString(): String {
                return idLeague+strLeague
        }
}
*/