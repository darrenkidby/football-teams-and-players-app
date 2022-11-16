package controllers

import models.Team
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
}