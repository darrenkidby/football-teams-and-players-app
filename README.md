# football-teams-and-players-app

01. Adding a Menu
    I needed to add, Add a team, List all teams, Update a team Expel a team and exit as options for the user to click.

    1. mainMenu() function
       I needed to add the variable val scanner = Scanner(System.`in`)
       as well as a few println() to create the menu.

    2. runMenu function
       I needed to add the functions so when the user typed the number that was beside the option, it chooses the users choice.

    3. remaining functions
       I added skeleton code for addTeam(), listTeams(), updateTeam(), expelTeam() and exitApp. This will be updated later.

02. Refactoring Menu Code

    1. I changed the println() menu to one singular print() with all options inside.

03. Input Mismatch Exception

    1. I created a new package called utils and in the package a file called ScannerInput and added the needed code into it.
    2. I added code in the mainMenu() to allow the contents of the print statement is sent as a parameter in the ScannerInput() function.

04. Adding Logging Support

    1. I added two new dependencies to build.gradle.kts
    2. In the main.kt I added private val logger = KotlinLogging.logger {}
       and updated the addTeam() skeleton code.

05. Issues

    1. I created an issue about adding teams.
    2. I created a second issue about JUnit automated testing.

06. Branches

    1. I created a branch to help solve issue one, I called the branch API-and-Team.
    2. I created a new package called models and added a kotlin class called Team in this package, I also added code to the Team file.
    3. I committed these new changes to the new branch.

07. Collection

    1. I created a new package called controllers and added a kotlin class called TeamAPI in this package, I added the relevant code to this file.
    2. I added a variable to the main.kt to allow this file to work.
    3. I added code to the addTeam() function to make the add work.
    4. I also updated the listTeams() skeleton code.

08. Pull Request

    1. I clicked Compare & pull request on my GitHub page and created the pull request.
    2. I added issue one to this pull request.
    3. I merged the pull request.
    4. I deleted the branch from the GitHub.
    5. I used git fetch on intellij to delete the remote API-and-Team branch.
    6. I clicked checkout on the Master remote branch.
    7. I manually deleted the local API-and-Team branch.

09. Testing

    1. I added test code to teamAPItest
    2. I ran the tests to see if they were working.
    3. I did the same as the 08. Pull Request section.

10. List and Number

    1. I added the code to work listNonEuropeanTeams(), listEuropeanTeams(), numberOfEuropeanTeams() and numberOfNonEuropeanTeams().
    2. I added the associated test code.
    3. I created the issue branch and used a pull request to close the issue.
    4. I added the code to work listTeamsByLeagueForm(form: Int) and numberOfTeamsByLeagueForm(form: Int).
    5. I added the associated test code.
    6. I created the issue branch and used a pull request to close the issue.

11. New Issues

    1. Created two issues for Update and JUnits tests.
    2. Created two issue for Delete and JUnits tests.
    3. Created two issue Persist for JSON or XML and JUnits tests.

12. Update Team

    1. Created a branch for the update function.
    2. I added code in teamAPI to make the updateTeam() work.
    3. I added code to the main.kt.
    4. I added the test code for the update.
    5. I committed and pushed the code. Created a pull request to close the issue and deleted the branch.

13. Expel Team

    1. Created a branch for the expel function.
    2. I added code in teamAPI to make the expelTeam() work.
    3. I added code to the main.kt.
    4. I added the test code for expel.
    5. I committed and pushed the code. Created a pull request to close the issue and deleted the branch.

14. Persistence XML and JSON

    1. Created a branch for the Persistence.
    2. I created a new package called persistence and added three new kotlin classes called Serializer, JSONSerializer and XMLSerializer.
    3. I added the relevant code to the Serializer file.
    4. I added new dependencies to build.gradle.kts.
    5. I added the relevant code to the XMLSerializer file.
    6. I added code for the XMLSerializer in TeamAPI and Main.kt
    7. I added to new menu items in saveTeam() and loadTeam()
    8. I added testing for XML in TeamAPItest.
    9. I committed and pushed the code.
    10. I added the relevant code to the JSONSerializer file.
    11. I added code for the JSONSerializer in TeamAPI and Main.kt
    12. I added testing for JSON in TeamAPItest.
    13. I committed and pushed the code. Created a pull request to close the issue and deleted the branch.

15. European Team

    1. I added code to teamAPI for europeanTeam().
    2. I updated the menu changes for european team.
    3. I added code to main.kt to make europeanTeam() work as well as listNonEuropeanTeams().
    4. I created a test that was associated with this.
    5. I used a pull request and deleted the branch.

16. Submenu

    1. I added a submenu for listTeams and added new functions.

17. Tracked Files

    1. I added /build in .gitignore so it would ignore all the build folder.
    2. Using the terminal, I stopped the tracking for these files.
    3. I did the same for the .gradle files.

18. Teams With Players
    
    1. I updated my menu to allow players to be added to it with a new section called player menu.
    2. I added a new models called Player, as well as adding the information about the player to it.
    3. I added a new utils called Utilities, which will help with the refactoring later.
    4. I added var players as a part of my Team.kt
    5. I added the relevant code to make the add player work in Team.kt
    6. I added the relevant code to make the remove and update player work in Team.kt
    7. I also added listPlayers
    8. I added toString function to Team.kt
    9. I added checkYouthTeam to Team.kt
    10. I updated the skeleton code I added for addPlayerToTeam() to allow this to work.
    11. I updated the skeleton code I added for updatePlayerInTeam() to allow this to work.
    12. I added askUserToChoosePlayer as the code wouldn't work without being able to select the d of the player
    13. I updated the skeleton code I added for removePlayerFromTeam() to allow this to work.
    14. I added youthTeamStatus() to allow user to select the player as a youth player or promote them to first team.
    15. I added retiredStatus() to allow user to select retire a player or add him back from retirement.
    16. I added toString() to Player.kt
    17. I added search player to allow myself to search a player using their name.
    18. I added listYouthPlayer to allow the user to list the youth players.
    19. I added listRetiredPlayer to allow the user to list the retired players.

19. Lambdas Counting

    1. I added a new issue and branch.
    2. I added the relevant test code.
    3. I made the numberOfNonEuropeanTeams(), numberOfEuropeanTeams() and numberOfTeamsByForm(form: Int) easier to read.
    4. I committed and pushed as well as use a pull request and deleted the branch.

20. Lambdas List All

    1. I added a new issue and branch.
    2. I added the relevant test code.
    3. I made listAllTeams() easier to read.
    4. I committed and pushed as well as use a pull request and deleted the branch.

21. Lambdas Listing

    1. I added a new issue and branch.
    2. I added the relevant test code.
    3. I made listEuropeanTeams() and listNonEuropeanTeams() easier to read.
    4. I committed and pushed as well as use a pull request and deleted the branch.

22. Lambdas Searching

    1. I added a new issue and branch.
    2. I created a new function called searchTeams.
    3. In main.kt I added a new menu item for search.
    4. I committed and pushed as well as use a pull request and deleted the branch.

23. Lambdas Refactoring

    1. I added a new issue and branch.
    2. I refactored the joinString code. This is where the utils.Utilities helped.
    3. I checked the tests.
    4. I refactored the counting methods code.
    5. I checked the tests.
    6. I committed and pushed as well as use a pull request and deleted the branch.

24. Additional

    1. I added colour to the console. I found a GitHub webpage by mgumiero9 with different Ansi codes. https://gist.github.com/mgumiero9/665ab5f0e5e7e46cb049c1544a00e29f.
    2. I created an issue and a branch.
    3. I added extinctTeams and activeTeams functions. (similar to europeanTeams and nonEuropeanTeams)
    4. I fixed the tests for the team information.
    5. I committed and pushed as well as use a pull request and deleted the branch.
