package com.fantasyfootball.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    
    private final TeamService teamService;
    
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }
    
    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<Team>> getTeamsByLeague(@PathVariable Long leagueId) {
        return ResponseEntity.ok(teamService.getTeamsByLeague(leagueId));
    }
    
    @PostMapping("/league/{leagueId}")
    public ResponseEntity<Team> createTeam(@PathVariable Long leagueId, @RequestBody Team team) {
        Team created = teamService.createTeam(leagueId, team);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        Team updated = teamService.addPlayerToTeam(teamId, playerId);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        Team updated = teamService.removePlayerFromTeam(teamId, playerId);
        return ResponseEntity.ok(updated);
    }
}
