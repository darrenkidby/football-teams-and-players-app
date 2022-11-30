package models

data class Team(
    var teamName: String,
    var teamCountry: String,
    var leagueName: String,
    var leaguePosition: Int,
    val isTeamPlayingEurope :Boolean){
}
