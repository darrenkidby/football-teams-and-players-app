package controllers

import models.Team
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
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
}