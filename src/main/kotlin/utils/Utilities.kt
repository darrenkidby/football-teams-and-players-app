package utils

import models.Player
import models.Team

object Utilities {

    @JvmStatic
    fun formatListString(teamsToFormat: List<Team>): String =
        teamsToFormat
            .joinToString(separator = "\n") { team -> "$team" }

    @JvmStatic
    fun formatSetString(playersToFormat: Set<Player>): String =
        playersToFormat
            .joinToString(separator = "\n") { player -> "\t$player" }
}
