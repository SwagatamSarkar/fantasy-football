package com.fantasyfootball.team;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class TeamControllerTest {

    @Test
    void testTeamClassExists() {
        Team team = new Team();
        team.setName("Test Team");
        assertEquals("Test Team", team.getName());
    }

    @Test
    void testTeamBuilder() {
        Team team = Team.builder()
                .name("My Dream Team")
                .managerName("John Doe")
                .budget(100)
                .build();

        assertEquals("My Dream Team", team.getName());
        assertEquals("John Doe", team.getManagerName());
        assertEquals(100, team.getBudget());
    }

    @Test
    void testTeamCreatedAtIsSet() {
        Team team = new Team();
        team.onCreate();
        assertNotNull(team.getCreatedAt());
    }

    @Test
    void testTeamDefaultBudget() {
        Team team = new Team();
        team.onCreate();
        assertEquals(100, team.getBudget());
    }

    @Test
    void testTeamWithAllFields() {
        Team team = Team.builder()
                .id(1L)
                .name("Test Team")
                .managerName("Jane Smith")
                .budget(120)
                .build();

        assertEquals(1L, team.getId());
        assertEquals("Test Team", team.getName());
        assertEquals("Jane Smith", team.getManagerName());
        assertEquals(120, team.getBudget());
    }

    @Test
    void testTeamPlayersInitialized() {
        Team team = new Team();
        team.setPlayers(new ArrayList<>());
        assertNotNull(team.getPlayers());
        assertEquals(0, team.getPlayers().size());
    }

    @Test
    void testTeamWithMultiplePlayers() {
        Team team = new Team();
        team.setPlayers(new ArrayList<>());
        team.getPlayers().add(new com.fantasyfootball.player.Player());
        team.getPlayers().add(new com.fantasyfootball.player.Player());
        assertEquals(2, team.getPlayers().size());
    }
}