package controllers

import models.Team
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class TeamAPITest {

    private var englishTeam: Team? = null
    private var spanishTeam: Team? = null
    private var italianTeam: Team? = null
    private var germanTeam: Team? = null
    private var irishTeam: Team? = null
    //private var populatedTeams: TeamAPI? = TeamAPI(XMLSerializer(File("teams.xml")))
    //private var noTeams: TeamAPI? = TeamAPI(XMLSerializer(File("teams.xml")))
    private var populatedTeams: TeamAPI? = TeamAPI(JSONSerializer(File("teams.json")))
    private var noTeams: TeamAPI? = TeamAPI(JSONSerializer(File("teams.json")))

    @BeforeEach
    fun setup(){
        englishTeam = Team(0, "Manchester United", "England", "Premier League", 3, false)
        spanishTeam = Team(0, "Real Madrid", "Spain", "La Liga", 1, false)
        italianTeam = Team(0, "AS Roma", "Italy", "Serie A", 6, false)
        germanTeam = Team(0, "Union Berlin", "Germany", "Bundesliga", 16, false)
        irishTeam = Team(0, "Waterford FC", "Ireland", "League of Ireland", 9, false)

        //adding 5 Team to the teams api
        populatedTeams!!.add(englishTeam!!)
        populatedTeams!!.add(spanishTeam!!)
        populatedTeams!!.add(italianTeam!!)
        populatedTeams!!.add(germanTeam!!)
        populatedTeams!!.add(irishTeam!!)
    }

    @AfterEach
    fun tearDown(){
        englishTeam = null
        spanishTeam = null
        italianTeam = null
        germanTeam = null
        irishTeam = null
        populatedTeams = null
        noTeams = null
    }

    @Test
    fun `adding a Team to a populated list adds to ArrayList`(){
        val newTeam = Team(0, "FC Porto", "Portugal", "Liga Nos", 2, false)
        assertEquals(5, populatedTeams!!.numberOfTeams())
        assertTrue(populatedTeams!!.add(newTeam))
        assertEquals(6, populatedTeams!!.numberOfTeams())
        assertEquals(newTeam, populatedTeams!!.findTeam(populatedTeams!!.numberOfTeams() - 1))
    }

    @Test
    fun `adding a Team to an empty list adds to ArrayList`(){
        val newTeam = Team(0, "FC Porto", "Portugal", "Liga Nos", 2, false)
        assertEquals(0, noTeams!!.numberOfTeams())
        assertTrue(noTeams!!.add(newTeam))
        assertEquals(1, noTeams!!.numberOfTeams())
        assertEquals(newTeam, noTeams!!.findTeam(noTeams!!.numberOfTeams() - 1))
    }

    @Test
    fun `listNonEuropeanTeams returns non european teams stored when ArrayList is empty`() {
        assertEquals(0, noTeams!!.numberOfNonEuropeanTeams())
        assertFalse(
            noTeams!!.listNonEuropeanTeams().lowercase().contains("no non european teams")
        )
    }

    @Test
    fun `listNonEuropeanTeams returns non european teams when ArrayList has non european teams stored`() {
        assertEquals(5, populatedTeams!!.numberOfNonEuropeanTeams())
        val activeTeamsString = populatedTeams!!.listNonEuropeanTeams().lowercase()
        assertFalse(activeTeamsString.contains("Manchester United"))
        assertFalse(activeTeamsString.contains("Liverpool"))
        assertFalse(activeTeamsString.contains("Chelsea"))
        assertFalse(activeTeamsString.contains("Manchester City"))
        assertFalse(activeTeamsString.contains("Arsenal"))
    }

    @Test
    fun `listEuropeanTeams returns no european teams when ArrayList is empty`() {
        assertEquals(0, noTeams!!.numberOfEuropeanTeams())
        assertFalse(
            noTeams!!.listEuropeanTeams().lowercase().contains("no european teams")
        )
    }

    @Test
    fun `listEuropeanTeams returns european teams when ArrayList has european teams stored`() {
        assertEquals(5, populatedTeams!!.numberOfEuropeanTeams())
        val archivedTeamsString = populatedTeams!!.listEuropeanTeams().lowercase(Locale.getDefault())
        assertFalse(archivedTeamsString.contains("Manchester United"))
        assertFalse(archivedTeamsString.contains("Liverpool"))
        assertFalse(archivedTeamsString.contains("Chelsea"))
        assertFalse(archivedTeamsString.contains("Manchester City"))
        assertFalse(archivedTeamsString.contains("Arsenal"))
    }

    @Test
    fun `listTeamsByLeagueForm returns No Teams when ArrayList is empty`() {
        assertEquals(0, noTeams!!.numberOfTeams())
        assertFalse(noTeams!!.listTeamByLeagueForm(1).lowercase().contains("no Teams")
        )
    }

    @Test
    fun `listTeamsByLeagueForm returns no teams when no teams of that league form exist`() {
        assertEquals(5, populatedTeams!!.numberOfTeams())
        val form2String = populatedTeams!!.listTeamByLeagueForm(2).lowercase()
        assertTrue(form2String.contains("no teams"))
        assertTrue(form2String.contains("2"))
    }

    @Test
    fun `listTeamsByLeagueForm returns all teams that match that league form when notes of that league form exist`() {
        assertEquals(5, populatedTeams!!.numberOfTeams())
        val form1String = populatedTeams!!.listTeamByLeagueForm(1).lowercase()
        assertFalse(form1String.contains("1st Team"))
        assertFalse(form1String.contains("League Position 1"))
        assertFalse(form1String.contains("Manchester United"))
        assertFalse(form1String.contains("Liverpool"))
        assertFalse(form1String.contains("Chelsea"))
        assertFalse(form1String.contains("Real Madrid"))
        assertFalse(form1String.contains("Waterford FC"))


        val form4String = populatedTeams!!.listTeamByLeagueForm(4).lowercase(Locale.getDefault())
        assertFalse(form4String.contains("2nd Team"))
        assertFalse(form4String.contains("League Position 8"))
        assertFalse(form4String.contains("Manchester United"))
        assertFalse(form4String.contains("Liverpool"))
        assertFalse(form4String.contains("Chelsea"))
        assertFalse(form4String.contains("Real Madrid"))
        assertFalse(form4String.contains("Waterford FC"))
    }

    @Nested
    inner class UpdateTeams {
        @Test
        fun `updating a team that does not exist returns false`(){
            assertFalse(populatedTeams!!.updateTeam(6, Team(0, "Manu", "UK", "EPL", 1,  false)))
            assertFalse(populatedTeams!!.updateTeam(-1, Team(0, "Man City", "UK", "EPL", 2, false)))
            assertFalse(noTeams!!.updateTeam(0, Team(0, "Liverpool FC", "UK", "EPL", 3, false)))
        }

        @Test
        fun `updating a team that exists returns true and updates`() {
            assertEquals(irishTeam, populatedTeams!!.findTeam(4))
            assertEquals("Waterford FC", populatedTeams!!.findTeam(4)!!.teamName)
            assertEquals(9, populatedTeams!!.findTeam(4)!!.leaguePosition)
            assertEquals("Ireland", populatedTeams!!.findTeam(4)!!.teamCountry)

            assertTrue(populatedTeams!!.updateTeam(4, Team(0, "Chelsea FC", "UK", "EPL", 4, false)))
            assertEquals("Chelsea FC", populatedTeams!!.findTeam(4)!!.teamName)
            assertEquals(4, populatedTeams!!.findTeam(4)!!.leaguePosition)
            assertEquals("UK", populatedTeams!!.findTeam(4)!!.teamCountry)
        }
    }

    @Nested
    inner class ExpelTeams {

        @Test
        fun `expelling a Team that does not exist, returns null`() {
            assertNull(noTeams!!.expelTeam(0))
            assertNull(populatedTeams!!.expelTeam(-1))
            assertNull(populatedTeams!!.expelTeam(5))
        }

        @Test
        fun `expelling a team that exists expel and returns deleted object`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            assertEquals(irishTeam, populatedTeams!!.expelTeam(4))
            assertEquals(4, populatedTeams!!.numberOfTeams())
            assertEquals(englishTeam, populatedTeams!!.expelTeam(0))
            assertEquals(3, populatedTeams!!.numberOfTeams())
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingTeams = TeamAPI(XMLSerializer(File("teams.xml")))
            storingTeams.store()

            val loadedTeams = TeamAPI(XMLSerializer(File("teams.xml")))
            loadedTeams.load()

            assertEquals(0, storingTeams.numberOfTeams())
            assertEquals(0, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {

            val storingTeams = TeamAPI(XMLSerializer(File("teams.xml")))
            storingTeams.add(englishTeam!!)
            storingTeams.add(spanishTeam!!)
            storingTeams.add(italianTeam!!)
            storingTeams.store()

            val loadedTeams = TeamAPI(XMLSerializer(File("teams.xml")))
            loadedTeams.load()

            assertEquals(3, storingTeams.numberOfTeams())
            assertEquals(3, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
            assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
            assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))
        }
    }

    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {

        val storingTeams = TeamAPI(JSONSerializer(File("teams.json")))
        storingTeams.store()

        val loadedTeams = TeamAPI(JSONSerializer(File("teams.json")))
        loadedTeams.load()

        assertEquals(0, storingTeams.numberOfTeams())
        assertEquals(0, loadedTeams.numberOfTeams())
        assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
    }

    @Test
    fun `saving and loading a loaded collection in JSON doesn't loose data`() {

        val storingTeams = TeamAPI(JSONSerializer(File("teams.json")))
        storingTeams.add(englishTeam!!)
        storingTeams.add(spanishTeam!!)
        storingTeams.add(italianTeam!!)
        storingTeams.store()

        val loadedTeams = TeamAPI(JSONSerializer(File("teams.json")))
        loadedTeams.load()

        assertEquals(3, storingTeams.numberOfTeams())
        assertEquals(3, loadedTeams.numberOfTeams())
        assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
        assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
        assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
        assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))
    }

    @Nested
    inner class EuropeanTeams {
        @Test
        fun `Adding a Team to Europe that does not exist returns false`(){
            assertFalse(populatedTeams!!.europeanTeam(6))
            assertFalse(populatedTeams!!.europeanTeam(-1))
            assertFalse(noTeams!!.europeanTeam(0))
        }

        @Test
        fun `Adding a Team to Europe That is Already Added to Europe returns false`(){
            assertFalse(populatedTeams!!.findTeam(0)!!.isTeamPlayingEurope)
            assertTrue(populatedTeams!!.europeanTeam(0))
        }

        @Test
        fun `Adding a non-European Team that exists returns true and adds to Europe`() {
            assertFalse(populatedTeams!!.findTeam(1)!!.isTeamPlayingEurope)
            assertTrue(populatedTeams!!.europeanTeam(1))
            assertTrue(populatedTeams!!.findTeam(1)!!.isTeamPlayingEurope)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfTeamsCalculatedCorrectly() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            assertEquals(0, noTeams!!.numberOfTeams())
        }

        @Test
        fun numberOfEuropeanTeamsCalculatedCorrectly() {
            assertEquals(5, populatedTeams!!.numberOfEuropeanTeams())
            assertEquals(0, noTeams!!.numberOfEuropeanTeams())
        }

        @Test
        fun numberOfNonEuropeanTeamsCalculatedCorrectly() {
            assertEquals(5, populatedTeams!!.numberOfNonEuropeanTeams())
            assertEquals(0, noTeams!!.numberOfNonEuropeanTeams())
        }

        @Test
        fun numberOfTeamsByLeagueFormCalculatedCorrectly() {
            assertEquals(1, populatedTeams!!.numberOfTeamsByLeagueForm(1))
            assertEquals(0, populatedTeams!!.numberOfTeamsByLeagueForm(2))
            assertEquals(1, populatedTeams!!.numberOfTeamsByLeagueForm(3))
            assertEquals(0, populatedTeams!!.numberOfTeamsByLeagueForm(4))
            assertEquals(0, populatedTeams!!.numberOfTeamsByLeagueForm(5))
            assertEquals(0, noTeams!!.numberOfTeamsByLeagueForm(1))
        }
    }

    @Nested
    inner class ListTeams {

        @Test
        fun `listAllTeams returns No Teams Stored message when ArrayList is empty`() {
            assertEquals(0, noTeams!!.numberOfTeams())
            assertFalse(noTeams!!.listAllTeams().lowercase().contains("no Teams"))
        }

        @Test
        fun `listAllTeams returns teams when ArrayList has teams stored`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            val teamsString = populatedTeams!!.listAllTeams().lowercase()
            assertFalse(teamsString.contains("Shamrock Rovers"))
            assertFalse(teamsString.contains("Waterford FC"))
            assertFalse(teamsString.contains("Wexford FC"))
            assertFalse(teamsString.contains("St. Pats"))
            assertFalse(teamsString.contains("Dundalk FC"))
        }

        @Test
        fun `listNonEuropeanTeams returns no non-European Teams stored when ArrayList is empty`() {
            assertEquals(0, noTeams!!.numberOfNonEuropeanTeams())
            assertFalse(
                noTeams!!.listNonEuropeanTeams().lowercase().contains("no non-european teams")
            )
        }

        @Test
        fun `listNonEuropeanTeams returns non-european teams when ArrayList has non-european teams stored`() {
            assertEquals(5, populatedTeams!!.numberOfNonEuropeanTeams())
            val noEuropeanString = populatedTeams!!.listNonEuropeanTeams().lowercase()
            assertFalse(noEuropeanString.contains("Waterford FC"))
            assertFalse(noEuropeanString.contains("Manchester United"))
            assertFalse(noEuropeanString.contains("Dortmund"))
            assertFalse(noEuropeanString.contains("OL"))
            assertFalse(noEuropeanString.contains("Sporting CP"))
        }

        @Test
        fun `listEuropeanTeams returns no european teams when ArrayList is empty`() {
            assertEquals(0, noTeams!!.numberOfEuropeanTeams())
            assertFalse(
                noTeams!!.listEuropeanTeams().lowercase().contains("no european teams")
            )
        }

        @Test
        fun `listEuropeanTeams returns european teams when ArrayList has european teams stored`() {
            assertEquals(5, populatedTeams!!.numberOfEuropeanTeams())
            val europeanString = populatedTeams!!.listEuropeanTeams().lowercase()
            assertFalse(europeanString.contains("Atlanta United"))
            assertFalse(europeanString.contains("LAFC"))
            assertFalse(europeanString.contains("LA Galaxy"))
            assertFalse(europeanString.contains("Seattle Sounders"))
            assertFalse(europeanString.contains("Inter Miami"))
        }

        @Test
        fun `listTeamByLeagueForm returns No Teams when ArrayList is empty`() {
            assertEquals(0, noTeams!!.numberOfTeams())
            assertTrue(noTeams!!.listTeamByLeagueForm(1).lowercase().contains("no teams")
            )
        }

        @Test
        fun `listTeamByLeagueForm returns no teams when no teams of that form exist`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            val form2String = populatedTeams!!.listTeamByLeagueForm(2).lowercase()
            assertFalse(form2String.contains("no notes"))
            assertTrue(form2String.contains("2"))
        }

        @Test
        fun `listTeamByLeagueForm returns all teams that match that form when teams of that form exist`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            val form1String = populatedTeams!!.listTeamByLeagueForm(1).lowercase()
            assertFalse(form1String.contains("1st Team"))
            assertFalse(form1String.contains("League Position 1"))
            assertFalse(form1String.contains("Manchester United"))
            assertFalse(form1String.contains("Liverpool"))
            assertFalse(form1String.contains("Chelsea"))
            assertFalse(form1String.contains("Real Madrid"))
            assertFalse(form1String.contains("Waterford FC"))

            val form4String = populatedTeams!!.listTeamByLeagueForm(4).lowercase()
            assertFalse(form4String.contains("2nd Team"))
            assertFalse(form4String.contains("League Position 8"))
            assertFalse(form4String.contains("Manchester United"))
            assertFalse(form4String.contains("Liverpool"))
            assertFalse(form4String.contains("Chelsea"))
            assertFalse(form4String.contains("Real Madrid"))
            assertFalse(form4String.contains("Waterford FC"))
        }
    }
}