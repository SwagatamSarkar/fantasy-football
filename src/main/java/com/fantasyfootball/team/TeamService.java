package com.fantasyfootball.team;

import org.springframework.stereotype.Service;
import com.fantasyfootball.league.League;
import com.fantasyfootball.league.LeagueService;
import com.fantasyfootball.player.Player;
import com.fantasyfootball.player.PlayerService;
import java.util.List;

@Service
public class TeamService {
    
    private final TeamRepository teamRepository;
    private final LeagueService leagueService;
    private final PlayerService playerService;
    
    public TeamService(TeamRepository teamRepository, 
                       LeagueService leagueService,
                       PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.leagueService = leagueService;
        this.playerService = playerService;
    }
    
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found: " + id));
    }
    
    public List<Team> getTeamsByLeague(Long leagueId) {
        leagueService.getLeagueById(leagueId);
        return teamRepository.findByLeagueId(leagueId);
    }
    
    public Team createTeam(Long leagueId, Team team) {
        League league = leagueService.getLeagueById(leagueId);
        team.setLeague(league);
        return teamRepository.save(team);
    }
    
    public Team updateTeam(Long id, Team updatedTeam) {
        Team team = getTeamById(id);
        team.setName(updatedTeam.getName());
        team.setManagerName(updatedTeam.getManagerName());
        team.setBudget(updatedTeam.getBudget());
        return teamRepository.save(team);
    }
    
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
    
    public Team addPlayerToTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Player player = playerService.getPlayerById(playerId);
        if (!team.getPlayers().contains(player)) {
            team.getPlayers().add(player);
        }
        return teamRepository.save(team);
    }
    
    public Team removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Player player = playerService.getPlayerById(playerId);
        team.getPlayers().remove(player);
        return teamRepository.save(team);
    }
}
