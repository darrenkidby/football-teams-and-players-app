import controllers.TeamAPI
import models.Team
import mu.KotlinLogging
import java.lang.System.exit
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val logger = KotlinLogging.logger {}
private val teamAPI = TeamAPI()

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
          ----------------------------------
          |   0) Exit                      |
          ----------------------------------
          ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addTeam()
            2  -> listTeams()
            3  -> updateTeam()
            4  -> removeTeam()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addTeam(){
    //logger.info { "addTeam() function invoked" }
    val teamName = readNextLine("Enter name of the Team: ")
    val teamCountry  = readNextLine("Enter the Country the Team plays in: ")
    val leagueName = readNextLine("Enter name of the League the team plays in: ")
    val leaguePosition = readNextInt("Enter a priority (Champions-1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20-Relegated): ")
    val isTeamAdded = teamAPI.add(Team(teamName, teamCountry, leagueName, leaguePosition, false))

    if (isTeamAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listTeams(){
    //logger.info { "listTeams() function invoked" }
    println(teamAPI.listAllTeams())
}

fun updateTeam(){
    logger.info { "updateTeam() function invoked" }
}

fun removeTeam(){
    logger.info { "removeTeam() function invoked" }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}