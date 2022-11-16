package models

data class Note(val teamName: String,
                val teamCountry: String,
                val leagueName: String,
                val leaguePosition: Int,
                val isTeamPlayingEurope :Boolean){
}

class Team(teamName: String, teamCountry: String, leagueName: String, leaguePosition: Int, b: Boolean) {

}
