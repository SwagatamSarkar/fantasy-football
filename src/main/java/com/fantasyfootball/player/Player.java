package com.fantasyfootball.player;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fantasyfootball.team.Team;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private String club;

    @Column(nullable = false)
    private String league;

    @Column(nullable = true)
    private Double marketValue;

    // Enum for positions
    public enum Position {
        GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD
    }

    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY)
    private java.util.List<Team> teams;

}