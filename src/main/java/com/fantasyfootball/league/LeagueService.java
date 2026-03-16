package com.fantasyfootball.league;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeagueService {
    
    private final LeagueRepository leagueRepository;
    
    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }
    
    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }
    
    public League getLeagueById(Long id) {
        return leagueRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("League not found: " + id));
    }
    
    public List<League> getLeaguesByStatus(League.LeagueStatus status) {
        return leagueRepository.findByStatus(status);
    }
    
    public League createLeague(League league) {
        return leagueRepository.save(league);
    }
    
    public League updateLeague(Long id, League updatedLeague) {
        League league = getLeagueById(id);
        league.setName(updatedLeague.getName());
        league.setDescription(updatedLeague.getDescription());
        league.setStatus(updatedLeague.getStatus());
        return leagueRepository.save(league);
    }
    
    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }
}
