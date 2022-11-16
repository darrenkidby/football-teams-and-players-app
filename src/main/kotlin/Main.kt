import mu.KotlinLogging
import java.lang.System.exit
import utils.ScannerInput

private val logger = KotlinLogging.logger {}

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
    logger.info { "addTeam() function invoked" }
}

fun listTeams(){
    logger.info { "listTeams() function invoked" }
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