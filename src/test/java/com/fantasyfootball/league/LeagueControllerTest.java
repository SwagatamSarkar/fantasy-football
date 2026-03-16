package com.fantasyfootball.league;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class LeagueControllerTest {

    @Test
    void testLeagueClassExists() {
        League league = new League();
        league.setName("Test League");
        assertEquals("Test League", league.getName());
    }

    @Test
    void testLeagueBuilder() {
        League league = League.builder()
                .name("Premier Fantasy League")
                .description("Top league")
                .status(League.LeagueStatus.DRAFT)
                .build();

        assertEquals("Premier Fantasy League", league.getName());
        assertEquals("Top league", league.getDescription());
        assertEquals(League.LeagueStatus.DRAFT, league.getStatus());
    }

    @Test
    void testLeagueStatusEnum() {
        League.LeagueStatus[] statuses = League.LeagueStatus.values();
        assertEquals(3, statuses.length);
        assertEquals(League.LeagueStatus.DRAFT, statuses[0]);
        assertEquals(League.LeagueStatus.ACTIVE, statuses[1]);
        assertEquals(League.LeagueStatus.COMPLETED, statuses[2]);
    }

    @Test
    void testLeagueCreatedAtIsSet() {
        League league = new League();
        league.onCreate();
        assertNotNull(league.getCreatedAt());
    }

    @Test
    void testLeagueDefaultStatus() {
        League league = new League();
        league.onCreate();
        assertEquals(League.LeagueStatus.DRAFT, league.getStatus());
    }

    @Test
    void testLeagueWithAllFields() {
        League league = League.builder()
                .id(1L)
                .name("Test League")
                .description("Description")
                .status(League.LeagueStatus.ACTIVE)
                .build();

        assertEquals(1L, league.getId());
        assertEquals("Test League", league.getName());
        assertEquals(League.LeagueStatus.ACTIVE, league.getStatus());
    }
}