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

    //utility method to determine if an index is valid in a list.
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
}