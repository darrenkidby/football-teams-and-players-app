package controllers

import models.Team

class TeamAPI {
    private var teams = ArrayList<Team>()

    fun add(team: Team): Boolean {
        return teams.add(team)
    }

    fun listAllTeams(): String {
        return if (teams.isEmpty()) {
            "No notes stored"
        } else {
            var listOfTeams = ""
            for (i in teams.indices) {
                listOfTeams += "${i}: ${teams[i]} \n"
            }
            listOfTeams
        }
    }
}