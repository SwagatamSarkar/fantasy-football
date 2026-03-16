package com.fantasyfootball.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fantasyfootball.league.League;
import com.fantasyfootball.player.Player;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String managerName;
    
    @Column(nullable = false)
    private Integer budget;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "team_player",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> players;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (budget == null) {
            budget = 100;
        }
    }
}
