import controllers.TeamAPI
import models.Team
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.lang.System.exit
import utils.ScannerInput
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
                                            
          ----------------------------------
          |       FOOTBALL TEAM APP        |
          ----------------------------------
          | TEAM MENU                      |
          |   1) Add a team                |
          |   2) List all teams            |
          |   3) Update a team             |
          |   4) Remove a team             |
          |   5) Add team to Europe        |
          |   6) Save a team               |
          |   7) Load a team               |
          ----------------------------------
          |   0) Exit                      |
          ----------------------------------
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
        println("Option Invalid - No notes stored");
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
            6  -> saveTeam()
            7  -> loadTeam()
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
    val isTeamAdded = teamAPI.add(Team(teamName, teamCountry, leagueName, leaguePosition, false))

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

            if (teamAPI.updateTeam(indexToUpdate, Team(teamName, teamCountry, leagueName, leaguePosition, false))){
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

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}