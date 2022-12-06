import controllers.TeamAPI
import models.Player
import models.Team
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
// private val teamAPI = TeamAPI(XMLSerializer(File("teams.xml")))
private val teamAPI = TeamAPI(JSONSerializer(File("teams.json")))

var colour = "\u001B[31m"
var bold = "\u001B[1m"

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """$colour $bold
                                            
          ------------------------------------
          |        FOOTBALL TEAM APP         |
          ------------------------------------
          |   1) TEAM MENU                   |
          ------------------------------------
          |   2) PLAYER MENU                 |
          ------------------------------------
          |   0) EXIT                        |
          ------------------------------------
          ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> teamsMenu()
            2 -> playersMenu()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun listTeams() {
    if (teamAPI.numberOfTeams() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) Show All Teams          |
                  > |   2) Show Non-European Teams |
                  > |   3) Show European Teams     |
                  > |   4) Show Active Teams       |
                  > |   5) Show Extinct Teams      |
                  > --------------------------------
                  > |   0) Exit                    |
                  > --------------------------------
                  > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllTeams()
            2 -> listNonEuropeanTeams()
            3 -> listEuropeanTeams()
            4 -> listActiveTeams()
            5 -> listExtinctTeams()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No teams stored")
    }
}

fun teamsMenu() {
    if (teamAPI.numberOfTeams() >= 0) {
        val option = readNextInt(
            """
          ------------------------------------
          | TEAM MENU                        |
          |   1) Add a team                  |
          |   2) List teams                  |
          |   3) Update a team               |
          |   4) Remove a team               |
          |   5) Add team to Europe          |
          |   6) Make team Extinct           |
          |   7) Search team                 |
          |   8) Save a team                 |
          |   9) Load a team                 |
          |   99) Dummy Data                 |
          ------------------------------------
          |   0) Exit                        |
          ------------------------------------
          ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> addTeam()
            2 -> listTeams()
            3 -> updateTeam()
            4 -> expelTeam()
            5 -> europeanTeam()
            6 -> extinctTeam()
            7 -> searchTeams()
            8 -> saveTeam()
            9 -> loadTeam()
            99 -> dummyData()
            0 -> exitApp()
            else -> println("Invalid option entered: " + option)
        }
    }
}

fun playersMenu() {
    if (teamAPI.numberOfTeams() >= 0) {
        val option = readNextInt(
            """
          ------------------------------------
          | PLAYER MENU                      |
          |   1) Add a player to team        |
          |   2) Update player information   |
          |   3) Remove player from team     |
          |   4) Is player a youth player?   |
          |   5) List youth players          |
          |   6) Is player a retired legend? |
          |   7) List retired players        |
          |   8) Search Player               |
          ------------------------------------
          |   0) Exit                        |
          ------------------------------------
          ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> addPlayerToTeam()
            2 -> updatePlayerInTeam()
            3 -> removePlayerFromTeam()
            4 -> youthTeamStatus()
            5 -> listYouthPlayer()
            6 -> retiredStatus()
            7 -> listRetiredPlayer()
            8 -> searchPlayers()
            0 -> exitApp()
            else -> println("Invalid option entered: " + option)
        }
    }
}

fun addTeam() {
    // logger.info { "addTeam() function invoked" }
    val teamName = readNextLine("Enter a Team Name: ")
    val teamCountry = readNextLine("Enter the Country of the Team: ")
    val leagueName = readNextLine("Enter the League of the Team: ")
    val leaguePosition = readNextInt("Enter a position (Champions-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20-Relegated): ")
    val isTeamAdded = teamAPI.add(Team(0, teamName, teamCountry, leagueName, leaguePosition, false, false))

    if (isTeamAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun updateTeam() {
    // logger.info { "updateTeams() function invoked" }
    listTeams()
    if (teamAPI.numberOfTeams() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the team you want to update: ")
        if (teamAPI.isValidIndex(indexToUpdate)) {
            val teamName = readNextLine("Enter a Team Name: ")
            val teamCountry = readNextLine("Enter the Country of the Team: ")
            val leagueName = readNextLine("Enter the League of the Team: ")
            val leaguePosition = readNextInt("Enter a position (Champions-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20-Relegated): ")

            if (teamAPI.updateTeam(indexToUpdate, Team(0, teamName, teamCountry, leagueName, leaguePosition, false, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no teams for this index number")
        }
    }
}

fun expelTeam() {
    // logger.info { "expelledTeams() function invoked" }
    listTeams()
    if (teamAPI.numberOfTeams() > 0) {

        val expelledTeamWithIndex = readNextInt("Enter the index of the Team to expel from football: ")

        val expelTheTeam = teamAPI.expelTeam(expelledTeamWithIndex)
        if (expelTheTeam != null) {
            println("Successfully Expelled! Team Expelled From Football: ${expelTheTeam.teamName}")
        } else {
            println("Unsuccessfully Expelled!")
        }
    }
}

fun listNonEuropeanTeams() {
    println(teamAPI.listNonEuropeanTeams())
}

fun listActiveTeams() {
    println(teamAPI.listActiveTeams())
}

fun europeanTeam() {
    listNonEuropeanTeams()
    if (teamAPI.numberOfNonEuropeanTeams() > 0) {

        val indexToEuropeanTeam = readNextInt("Enter the index of the team to add to Europe: ")

        if (teamAPI.europeanTeam(indexToEuropeanTeam)) {
            println("Team Successfully Added to Europe!")
        } else {
            println("Team Unsuccessfully Added to Europe!")
        }
    }
}

fun extinctTeam() {
    listActiveTeams()
    if (teamAPI.numberOfActiveTeams() > 0) {

        val indexToExtinct = readNextInt("Enter the index of the extinct team: ")

        if (teamAPI.extinctTeam(indexToExtinct)) {
            println("Team is Extinct!")
        } else {
            println("Team is not Extinct!")
        }
    }
}

fun searchTeams() {
    val searchName = readNextLine("Enter the Team Name to search by: ")
    val searchResults = teamAPI.searchTeamByName(searchName)
    if (searchResults.isEmpty()) {
        println("No teams found")
    } else {
        println(searchResults)
    }
}

fun saveTeam() {
    try {
        teamAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadTeam() {
    try {
        teamAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun listAllTeams() {
    println(teamAPI.listAllTeams())
}

fun listEuropeanTeams() {
    println(teamAPI.listEuropeanTeams())
}

fun listExtinctTeams() {
    println(teamAPI.listExtinctTeams())
}

private fun addPlayerToTeam() {
    val team: Team? = askUserToChooseTeam()
    if (team != null) {
        if (team.addPlayer(Player(playerName = readNextLine("\t Player Name: "), playerAge = readNextInt("\t Player Age: "), playerPosition = readNextLine("\t Player Position: "), playerCost = readNextLine("\t Player Cost: "), playerWage = readNextLine("\t Player Wage: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updatePlayerInTeam() {
    val team: Team? = askUserToChooseTeam()
    if (team != null) {
        val player: Player? = askUserToChoosePlayer(team)
        if (player != null) {
            val newName = readNextLine("Enter new Name: ")
            val newAge = readNextInt("Enter new Age: ")
            val newPosition = readNextLine("Enter new Position: ")
            val newCost = readNextLine("Enter new Cost: ")
            val newWage = readNextLine("Enter new Wage: ")
            if (team.update(player.playerId, Player(playerName = newName, playerAge = newAge, playerPosition = newPosition, playerCost = newCost, playerWage = newWage))) {
                println("Player information updated")
            } else {
                println("Player information NOT updated")
            }
        } else {
            println("Invalid Player Id")
        }
    }
}

fun removePlayerFromTeam() {
    val team: Team? = askUserToChooseTeam()
    if (team != null) {
        val player: Player? = askUserToChoosePlayer(team)
        if (player != null) {
            val isRemoved = team.remove(player.playerId)
            if (isRemoved) {
                println("Remove Successful!")
            } else {
                println("Remove NOT Successful")
            }
        }
    }
}

fun searchPlayers() {
    val searchName = readNextLine("Enter the Players Name to search by: ")
    val searchResults = teamAPI.searchPlayerByName(searchName)
    if (searchResults.isEmpty()) {
        println("No Player found")
    } else {
        println(searchResults)
    }
}

fun youthTeamStatus() {
    val team: Team? = askUserToChooseTeam()
    if (team != null) {
        val player: Player? = askUserToChoosePlayer(team)
        if (player != null) {
            var changeStatus = 'X'
            if (player.isPlayerYouth) {
                changeStatus = readNextChar("The player is currently in the youth team. Do you want to promote them to the first team?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    player.isPlayerYouth = false
            } else {
                changeStatus = readNextChar("The player is currently in the first team. Do you want to add them to the youth team?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    player.isPlayerYouth = true
            }
        }
    }
}

fun listYouthPlayer() {
    if (teamAPI.numberOfYouthPlayers() > 0) {
        println("Total Youth Players: ${teamAPI.numberOfYouthPlayers()}")
    }
    println(teamAPI.listYouthPlayers())
}

fun retiredStatus() {
    val team: Team? = askUserToChooseTeam()
    if (team != null) {
        val player: Player? = askUserToChoosePlayer(team)
        if (player != null) {
            var changeStatus = 'X'
            if (player.isPlayerRetired) {
                changeStatus = readNextChar("The player is currently in the youth team. Do you want player to come out of retirement?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    player.isPlayerRetired = false
            } else {
                changeStatus = readNextChar("The player is currently in the first team. Do you want to retire this player?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    player.isPlayerRetired = true
            }
        }
    }
}

fun listRetiredPlayer() {
    if (teamAPI.numberOfRetiredPlayers() > 0) {
        println("Total Youth Players: ${teamAPI.numberOfRetiredPlayers()}")
    }
    println(teamAPI.listRetiredPlayers())
}

private fun askUserToChoosePlayer(team: Team): Player? {
    if (team.numberOfPlayers() > 0) {
        print(team.listPlayers())
        return team.findOne(readNextInt("\nEnter the id of the player: "))
    } else {
        println("No players for chosen team")
        return null
    }
}

private fun askUserToChooseTeam(): Team? {
    listActiveTeams()
    if (teamAPI.numberOfActiveTeams() > 0) {
        val team = teamAPI.findTeam(readNextInt("\nEnter the id of the team: "))
        if (team != null) {
            if (team.isTeamExtinct) {
                println("Team is extinct")
            } else {
                return team
            }
        } else {
            println("Team id is not valid")
        }
    }
    return null
}

fun dummyData() {
    teamAPI.add(Team(0, "Arsenal", "England", "Premier League", 1, true, false))
    teamAPI.add(Team(1, "Manchester City", "England", "Premier League", 2, true, false))
    teamAPI.add(Team(2, "Newcastle United", "England", "Premier League", 3, false, false))
    teamAPI.add(Team(3, "Tottenham", "England", "Premier League", 4, true, false))
    teamAPI.add(Team(4, "Manchester United", "England", "Premier League", 5, true, false))
    teamAPI.add(Team(5, "Liverpool", "England", "Premier League", 6, true, false))
    teamAPI.add(Team(6, "Brighton", "England", "Premier League", 7, false, false))
    teamAPI.add(Team(7, "Chelsea", "England", "Premier League", 8, true, false))
    teamAPI.add(Team(8, "Fulham", "England", "Premier League", 9, false, false))
    teamAPI.add(Team(9, "Brentford", "England", "Premier League", 10, false, false))
    teamAPI.add(Team(10, "Crystal Palace", "England", "Premier League", 11, false, false))
    teamAPI.add(Team(11, "Aston Villa", "England", "Premier League", 12, false, false))
    teamAPI.add(Team(12, "Leicester City", "England", "Premier League", 13, false, false))
    teamAPI.add(Team(13, "Bournemouth", "England", "Premier League", 14, false, false))
    teamAPI.add(Team(14, "Leeds United", "England", "Premier League", 15, false, false))
    teamAPI.add(Team(15, "West Ham", "England", "Premier League", 16, true, false))
    teamAPI.add(Team(16, "Everton", "England", "Premier League", 17, false, false))
    teamAPI.add(Team(17, "Nottingham Forest", "England", "Premier League", 18, false, false))
    teamAPI.add(Team(18, "Southampton", "England", "Premier League", 19, false, false))
    teamAPI.add(Team(19, "Wolves", "England", "Premier League", 20, false, false))
}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}
