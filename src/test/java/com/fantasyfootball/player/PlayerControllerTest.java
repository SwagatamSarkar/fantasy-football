package com.fantasyfootball.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    @Test
    void testPlayerClassExists() {
        Player player = new Player();
        player.setName("Test Player");
        assertEquals("Test Player", player.getName());
    }

    @Test
    void testPlayerWithAllFields() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Erling Haaland");
        player.setPosition(Player.Position.FORWARD);
        player.setClub("Manchester City");
        player.setLeague("Premier League");

        assertEquals(1L, player.getId());
        assertEquals("Erling Haaland", player.getName());
        assertEquals(Player.Position.FORWARD, player.getPosition());
    }

    @Test
    void testPlayerPositionEnum() {
        Player.Position[] positions = Player.Position.values();
        assertEquals(4, positions.length);
        assertEquals(Player.Position.GOALKEEPER, positions[0]);
        assertEquals(Player.Position.DEFENDER, positions[1]);
        assertEquals(Player.Position.MIDFIELDER, positions[2]);
        assertEquals(Player.Position.FORWARD, positions[3]);
    }
}