package controllers

import models.Team
import persistence.Serializer
import utils.Utilities

class TeamAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType
    private var teams = ArrayList<Team>()

    fun add(team: Team): Boolean {
        return teams.add(team)
    }

    fun listAllTeams(): String {
        return if (teams.isEmpty()) {
            "No teams stored"
        } else {
            var listOfTeams = ""
            for (i in teams.indices) {
                listOfTeams += "${i}: ${teams[i]} \n"
            }
            listOfTeams
        }
    }

    fun numberOfTeams(): Int {
        return teams.size
    }

    fun findTeam(index: Int): Team? {
        return if (isValidListIndex(index, teams)) {
            teams[index]
        } else null
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listNonEuropeanTeams(): String {
        return if (numberOfNonEuropeanTeams() == 0) {
            "No teams stored"
        } else {
            var listOfNonEuropeanTeams = ""
            for (team in teams) {
                if (!team.isTeamPlayingEurope) {
                    listOfNonEuropeanTeams += "${teams.indexOf(team)}: $team \n"
                }
            }
            listOfNonEuropeanTeams
        }
    }

    fun listEuropeanTeams(): String {
        return if (numberOfEuropeanTeams() == 0) {
            "No teams stored"
        } else {
            var listOfEuropeanTeams = ""
            for (team in teams) {
                if (team.isTeamPlayingEurope) {
                    listOfEuropeanTeams += "${teams.indexOf(team)}: $team \n"
                }
            }
            listOfEuropeanTeams
        }
    }

    fun numberOfEuropeanTeams(): Int {
        return teams.stream()
            .filter{team: Team -> !team.isTeamPlayingEurope}
            .count()
            .toInt()
    }

    fun numberOfNonEuropeanTeams(): Int {
        return teams.stream()
            .filter{team: Team -> !team.isTeamPlayingEurope}
            .count()
            .toInt()
    }

    fun listTeamByLeagueForm(form: Int): String {
        return if (teams.isEmpty()) {
            "No teams stored"
        } else {
            var listOfTeams = ""
            for (i in teams.indices) {
                if (teams[i].leaguePosition == form) {
                    listOfTeams +=
                        """$i: ${teams[i]}
                        """.trimIndent()
                }
            }
            if (listOfTeams.equals("")) {
                "No teams with league form: $form"
            } else {
                "${numberOfTeamsByLeagueForm(form)} notes with priority $form: $listOfTeams"
            }
        }
    }

    fun numberOfTeamsByLeagueForm(form: Int): Int {
        var counter = 0
        for (team in teams) {
            if (team.leaguePosition == form) {
                counter++
            }
        }
        return counter
    }

    fun updateTeam(indexToUpdate: Int, team: Team?): Boolean {
        val foundTeam = findTeam(indexToUpdate)

        if ((foundTeam != null) && (team != null)) {
            foundTeam.teamName = team.teamName
            foundTeam.teamCountry = team.teamCountry
            foundTeam.leagueName = team.leagueName
            foundTeam.leaguePosition = team.leaguePosition
            return true
        }

        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, teams);
    }

    fun europeanTeam(indexToEuropeanTeam: Int): Boolean {
        if (isValidIndex(indexToEuropeanTeam)) {
            val teamToEuropeanTeam = teams[indexToEuropeanTeam]
            if (!teamToEuropeanTeam.isTeamPlayingEurope) {
                teamToEuropeanTeam.isTeamPlayingEurope = true
                return true
            }
        }
        return false
    }

    fun expelTeam(indexToExpel: Int): Team? {
        return if (isValidListIndex(indexToExpel, teams)) {
            teams.removeAt(indexToExpel)
        } else null
    }

    fun searchTeamByName(searchString: String) =
        Utilities.formatListString(
            teams.filter { team -> team.teamName.contains(searchString, ignoreCase = true) }
        )

    fun searchPlayerByName(searchString: String): String {
        return if (numberOfTeams() == 0) "No teams stored"
        else {
            var listOfTeams = ""
            for (team in teams) {
                for (player in team.players) {
                    if (player.playerName.contains(searchString, ignoreCase = true)) {
                        listOfTeams += "${team.teamName}: ${team.teamName} \n\t${player}\n"
                    }
                }
            }
            if (listOfTeams == "") "No players found for: $searchString"
            else listOfTeams
        }
    }

    fun listYouthPlayers(): String =
        if (numberOfTeams() == 0) "No teams stored"
        else {
            var listOfYouthPlayers = ""
            for (team in teams) {
                    for (player in team.players) {
                        if (!player.isPlayerYouth) {
                            listOfYouthPlayers += team.teamName + ": " + player.playerName + "\n"
                        }
                    }
            }
            listOfYouthPlayers
        }

    fun numberOfYouthPlayers(): Int {
        var numberOfYouthPlayers = 0
        for (team in teams) {
            for (player in team.players) {
                if (!player.isPlayerYouth) {
                    numberOfYouthPlayers++
                }
            }
        }
        return numberOfYouthPlayers
    }

    fun listRetiredPlayers(): String =
        if (numberOfTeams() == 0) "No teams stored"
        else {
            var listOfRetiredPlayers = ""
            for (team in teams) {
                for (player in team.players) {
                    if (!player.isPlayerRetired) {
                        listOfRetiredPlayers += team.teamName + ": " + player.playerName + "\n"
                    }
                }
            }
            listOfRetiredPlayers
        }

    fun numberOfRetiredPlayers(): Int {
        var numberOfRetiredPlayers = 0
        for (team in teams) {
            for (player in team.players) {
                if (!player.isPlayerRetired) {
                    numberOfRetiredPlayers++
                }
            }
        }
        return numberOfRetiredPlayers
    }

    @Throws(Exception::class)
    fun load() {
        teams = serializer.read() as ArrayList<Team>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(teams)
    }

}