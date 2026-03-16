package com.fantasyfootball.league;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/leagues")
public class LeagueController {
    
    private final LeagueService leagueService;
    
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }
    
    @GetMapping
    public ResponseEntity<List<League>> getAllLeagues() {
        return ResponseEntity.ok(leagueService.getAllLeagues());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable Long id) {
        return ResponseEntity.ok(leagueService.getLeagueById(id));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<League>> getLeaguesByStatus(@PathVariable League.LeagueStatus status) {
        return ResponseEntity.ok(leagueService.getLeaguesByStatus(status));
    }
    
    @PostMapping
    public ResponseEntity<League> createLeague(@RequestBody League league) {
        League created = leagueService.createLeague(league);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<League> updateLeague(@PathVariable Long id, @RequestBody League league) {
        return ResponseEntity.ok(leagueService.updateLeague(id, league));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.noContent().build();
    }
}
