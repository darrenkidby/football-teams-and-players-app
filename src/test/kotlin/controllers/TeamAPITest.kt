package controllers

import models.Team
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class TeamAPITest {

    private var englishTeam: Team? = null
    private var spanishTeam: Team? = null
    private var italianTeam: Team? = null
    private var germanTeam: Team? = null
    private var irishTeam: Team? = null
    private var populatedTeams: TeamAPI? = TeamAPI()
    private var noTeams: TeamAPI? = TeamAPI()

    @BeforeEach
    fun setup(){
        englishTeam = Team("Manchester United", "England", "Premier League", 3, false)
        spanishTeam = Team("Real Madrid", "Spain", "La Liga", 1, false)
        italianTeam = Team("AS Roma", "Italy", "Serie A", 6, false)
        germanTeam = Team("Union Berlin", "Germany", "Bundesliga", 16, false)
        irishTeam = Team("Waterford FC", "Ireland", "League of Ireland", 9, false)

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
        val newTeam = Team("FC Porto", "Portugal", "Liga Nos", 2, false)
        assertEquals(5, populatedTeams!!.numberOfTeams())
        assertTrue(populatedTeams!!.add(newTeam))
        assertEquals(6, populatedTeams!!.numberOfTeams())
        assertEquals(newTeam, populatedTeams!!.findTeam(populatedTeams!!.numberOfTeams() - 1))
    }

    @Test
    fun `adding a Team to an empty list adds to ArrayList`(){
        val newTeam = Team("FC Porto", "Portugal", "Liga Nos", 2, false)
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
        assertEquals(0, populatedTeams!!.numberOfEuropeanTeams())
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
            assertFalse(populatedTeams!!.updateTeam(6, Team("Manu", "UK", "EPL", 1,  false)))
            assertFalse(populatedTeams!!.updateTeam(-1, Team("Man City", "UK", "EPL", 2, false)))
            assertFalse(noTeams!!.updateTeam(0, Team("Liverpool FC", "UK", "EPL", 3, false)))
        }

        @Test
        fun `updating a team that exists returns true and updates`() {
            assertEquals(irishTeam, populatedTeams!!.findTeam(4))
            assertEquals("Waterford FC", populatedTeams!!.findTeam(4)!!.teamName)
            assertEquals(9, populatedTeams!!.findTeam(4)!!.leaguePosition)
            assertEquals("Ireland", populatedTeams!!.findTeam(4)!!.teamCountry)

            assertTrue(populatedTeams!!.updateTeam(4, Team("Chelsea FC", "UK", "EPL", 4, false)))
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
}