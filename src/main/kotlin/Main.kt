import controllers.TeamAPI
import models.Player
import models.Team
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.lang.System.exit
import utils.ScannerInput
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val logger = KotlinLogging.logger {}
//private val teamAPI = TeamAPI(XMLSerializer(File("teams.xml")))
private val teamAPI = TeamAPI(JSONSerializer(File("teams.json")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
                                            
          ------------------------------------
          |        FOOTBALL TEAM APP         |
          ------------------------------------
          | TEAM MENU                        |
          |   1) Add a team                  |
          |   2) List teams                  |
          |   3) Update a team               |
          |   4) Remove a team               |
          |   5) Add team to Europe          |
          |   6) Search team                 |
          |   7) Save a team                 |
          |   8) Load a team                 |
          |   99) Dummy Data                 |
          ------------------------------------
          | PLAYER MENU                      |
          |   9) Add a player to team        |
          |   10) Update player information  |
          |   11) Remove player from team    |
          |   12) Is player a youth player?  |
          |   13) List youth players         |
          |   14) Is player a retired legend?|
          |   15) Search Player              |
          ------------------------------------
          |   0) Exit                        |
          ------------------------------------
          ==>> """.trimMargin(">"))
}

fun listTeams() {
    if (teamAPI.numberOfTeams() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) Show All Teams          |
                  > |   2) Show Non-European Teams |
                  > |   3) Show European Teams     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllTeams();
            2 -> listNonEuropeanTeams();
            3 -> listEuropeanTeams();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No teams stored");
    }
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addTeam()
            2  -> listTeams()
            3  -> updateTeam()
            4  -> expelTeam()
            5  -> europeanTeam()
            6  -> searchTeams()
            7  -> saveTeam()
            8  -> loadTeam()
            9  -> addPlayerToTeam()
            10 -> updatePlayerInTeam()
            11 -> removePlayerFromTeam()
            12 -> youthTeamStatus()
            13 -> listYouthPlayer()
            15 -> searchPlayers()
            99 -> dummyData()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addTeam(){
    //logger.info { "addTeam() function invoked" }
    val teamName = readNextLine("Enter a Team Name: ")
    val teamCountry  = readNextLine("Enter the Country of the Team: ")
    val leagueName = readNextLine("Enter the League of the Team: ")
    val leaguePosition = readNextInt("Enter a position (Champions-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20-Relegated): ")
    val isTeamAdded = teamAPI.add(Team(0, teamName, teamCountry, leagueName, leaguePosition, false))

    if (isTeamAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun updateTeam() {
    //logger.info { "updateTeams() function invoked" }
    listTeams()
    if (teamAPI.numberOfTeams() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the team you want to update: ")
        if (teamAPI.isValidIndex(indexToUpdate)) {
            val teamName = readNextLine("Enter a Team Name: ")
            val teamCountry = readNextLine("Enter the Country of the Team: ")
            val leagueName = readNextLine("Enter the League of the Team: ")
            val leaguePosition = readNextInt("Enter a position (Champions-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20-Relegated): ")

            if (teamAPI.updateTeam(indexToUpdate, Team(0, teamName, teamCountry, leagueName, leaguePosition, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no teams for this index number")
        }
    }
}

fun expelTeam(){
    //logger.info { "expelledTeams() function invoked" }
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

private fun addPlayerToTeam() {
    val team: Team? = askUserToChooseNonEuropeanTeam()
    if (team != null) {
        if (team.addPlayer(Player(playerName = readNextLine("\t Player Name: "), playerAge = readNextInt("\t Player Age: "), playerPosition = readNextLine("\t Player Position: "), playerCost = readNextLine("\t Player Cost: "), playerWage = readNextLine("\t Player Wage: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updatePlayerInTeam() {
    val team: Team? = askUserToChooseNonEuropeanTeam()
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
    val team: Team? = askUserToChooseNonEuropeanTeam()
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
    val team: Team? = askUserToChooseNonEuropeanTeam()
    if (team != null) {
        val player: Player? = askUserToChoosePlayer(team)
        if (player != null) {
            var changeStatus = 'X'
            if (player.isPlayerYouth) {
                changeStatus = readNextChar("The player is currently in the youth team. Do you want to promote them to the first team?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    player.isPlayerYouth = false
            }
            else {
                changeStatus = readNextChar("The player is currently in the first team. Do you want to add them to the youth team?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    player.isPlayerYouth = true
            }
        }
    }
}

fun listYouthPlayer(){
    if (teamAPI.numberOfYouthPlayers() > 0) {
        println("Total Youth Players: ${teamAPI.numberOfYouthPlayers()}")
    }
    println(teamAPI.listYouthPlayers())
}

private fun askUserToChoosePlayer(team: Team): Player? {
    if (team.numberOfPlayers() > 0) {
        print(team.listPlayers())
        return team.findOne(readNextInt("\nEnter the id of the player: "))
    }
    else{
        println ("No players for chosen team")
        return null
    }
}

private fun askUserToChooseNonEuropeanTeam(): Team? {
    listNonEuropeanTeams()
    if (teamAPI.numberOfNonEuropeanTeams() > 0) {
        val team = teamAPI.findTeam(readNextInt("\nEnter the id of the team: "))
        if (team != null) {
            if (team.isTeamPlayingEurope) {
                println("Team is a European Team")
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
    teamAPI.add(Team(0, "Arsenal", "England", "Premier League", 1, true))
    teamAPI.add(Team(1, "Manchester City", "England", "Premier League", 2, true))
    teamAPI.add(Team(2, "Newcastle United", "England", "Premier League", 3, false))
    teamAPI.add(Team(3, "Tottenham", "England", "Premier League", 4, true))
    teamAPI.add(Team(4, "Manchester United", "England", "Premier League", 5, true))
    teamAPI.add(Team(5, "Liverpool", "England", "Premier League", 6, true))
    teamAPI.add(Team(6, "Brighton", "England", "Premier League", 7, false))
    teamAPI.add(Team(7, "Chelsea", "England", "Premier League", 8, true))
    teamAPI.add(Team(8, "Fulham", "England", "Premier League", 9, false))
    teamAPI.add(Team(9, "Brentford", "England", "Premier League", 10, false))
    teamAPI.add(Team(10, "Crystal Palace", "England", "Premier League", 11, false))
    teamAPI.add(Team(11, "Aston Villa", "England", "Premier League", 12, false))
    teamAPI.add(Team(12, "Leicester City", "England", "Premier League", 13, false))
    teamAPI.add(Team(13, "Bournemouth", "England", "Premier League", 14, false))
    teamAPI.add(Team(14, "Leeds United", "England", "Premier League", 15, false))
    teamAPI.add(Team(15, "West Ham", "England", "Premier League", 16, true))
    teamAPI.add(Team(16, "Everton", "England", "Premier League", 17, false))
    teamAPI.add(Team(17, "Nottingham Forest", "England", "Premier League", 18, false))
    teamAPI.add(Team(18, "Southampton", "England", "Premier League", 19, false))
    teamAPI.add(Team(19, "Wolves", "England", "Premier League", 20, false))


}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}
