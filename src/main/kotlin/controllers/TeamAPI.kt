package controllers

import models.Team

class TeamAPI {
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
        var counter = 0
        for (team in teams) {
            if (team.isTeamPlayingEurope) {
                counter++
            }
        }
        return counter
    }

    fun numberOfNonEuropeanTeams(): Int {
        var counter = 0
        for (team in teams) {
            if (!team.isTeamPlayingEurope) {
                counter++
            }
        }
        return counter
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

}