package models

import utils.Utilities

data class Team(
    var teamId: Int = 0,
    var teamName: String,
    var teamCountry: String,
    var leagueName: String,
    var leaguePosition: Int,
    var isTeamPlayingEurope: Boolean,
    var isTeamExtinct: Boolean,
    var players: MutableSet<Player> = mutableSetOf()
) {

    private var lastPlayerId = 0
    private fun getPlayerId() = lastPlayerId++

    fun addPlayer(player: Player): Boolean {
        player.playerId = getPlayerId()
        return players.add(player)
    }

    fun numberOfPlayers() = players.size

    fun findOne(id: Int): Player? {
        return players.find { player -> player.playerId == id }
    }

    fun remove(id: Int): Boolean {
        return players.removeIf { player -> player.playerId == id }
    }

    fun update(id: Int, newPlayer: Player): Boolean {
        val foundPlayer = findOne(id)

        if (foundPlayer != null) {
            foundPlayer.playerName = newPlayer.playerName
            foundPlayer.playerAge = newPlayer.playerAge
            foundPlayer.playerPosition = newPlayer.playerPosition
            foundPlayer.playerCost = newPlayer.playerCost
            foundPlayer.playerWage = newPlayer.playerWage
            foundPlayer.isPlayerYouth = newPlayer.isPlayerYouth
            foundPlayer.isPlayerRetired = newPlayer.isPlayerRetired
            return true
        }

        return false
    }

    fun listPlayers() =
        if (players.isEmpty()) "\tNO PLAYERS ADDED"
        else Utilities.formatSetString(players)

    override fun toString(): String {
        val europeanTeam = if (isTeamPlayingEurope) "Yes" else "No"
        val extinctTeam = if (isTeamExtinct) "Yes" else "No"
        return "$teamName, Country($teamCountry), League($leagueName), Position($leaguePosition), Europe($europeanTeam), Extinct($extinctTeam) \n${listPlayers()}"
    }

    fun checkYouthTeam(): Boolean {
        if (players.isNotEmpty()) {
            for (player in players) {
                if (!player.isPlayerYouth) {
                    return false
                }
            }
        }
        return true
    }
}
