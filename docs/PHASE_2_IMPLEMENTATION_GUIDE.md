# Fantasy Football - Phase 2 Implementation Guide
## League & Team Management

**Estimated Time:** 4-5 hours  
**Difficulty:** Intermediate  
**Concepts:** JPA Relationships, REST CRUD operations, Service layer patterns

---

## Overview

Phase 2 adds the core fantasy gameplay structure:
- **Leagues**: Containers where users play fantasy football
- **Teams**: Individual user fantasy teams within a league
- **Relationships**: A league has many teams, a team belongs to one league

### Architecture Diagram
```
Leagues (1) -----> (Many) Teams
   |
   └──> (Many) Players (selected by team)
```

---

## Step 1: Update Entity Relationships (30 mins)

### Step 1.1 - Modify Player Entity

Update `src/main/java/com/fantasyfootball/player/Player.java` to add relationship support:

```java
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
    private Double marketValue; // For future fantasy pricing
    
    // NEW: Relationship to teams (a player can be in multiple teams)
    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY)
    private java.util.List<Team> teams;
    
    public enum Position {
        GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD
    }
}
```

---

## Step 2: Create League Entity (30 mins)

### Step 2.1 - Create League Class

Create `src/main/java/com/fantasyfootball/league/League.java`:

```java
package com.fantasyfootball.league;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fantasyfootball.team.Team;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "league")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class League {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeagueStatus status;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    // NEW: Relationship to teams (one league has many teams)
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = LeagueStatus.DRAFT;
        }
    }
    
    public enum LeagueStatus {
        DRAFT,      // League created, teams can be added
        ACTIVE,     // League started, matches being played
        COMPLETED   // Season finished
    }
}
```

### Step 2.2 - Create League Repository

Create `src/main/java/com/fantasyfootball/league/LeagueRepository.java`:

```java
package com.fantasyfootball.league;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    List<League> findByStatus(League.LeagueStatus status);
}
```

### Step 2.3 - Create League Service

Create `src/main/java/com/fantasyfootball/league/LeagueService.java`:

```java
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
```

### Step 2.4 - Create League Controller

Create `src/main/java/com/fantasyfootball/league/LeagueController.java`:

```java
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
```

---

## Step 3: Create Team Entity (45 mins)

### Step 3.1 - Create Team Class

Create `src/main/java/com/fantasyfootball/team/Team.java`:

```java
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
    private Integer budget; // Starting budget (e.g., 100M for player purchases)
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Relationship to league (many teams belong to one league)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;
    
    // Relationship to players (many-to-many: a team has many players, a player can be in many teams)
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
            budget = 100; // Default 100M budget
        }
    }
}
```

### Step 3.2 - Create Team Repository

Create `src/main/java/com/fantasyfootball/team/TeamRepository.java`:

```java
package com.fantasyfootball.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByLeagueId(Long leagueId);
}
```

### Step 3.3 - Create Team Service

Create `src/main/java/com/fantasyfootball/team/TeamService.java`:

```java
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
        // Verify league exists
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
    
    // Add a player to a team
    public Team addPlayerToTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Player player = playerService.getPlayerById(playerId);
        
        if (!team.getPlayers().contains(player)) {
            team.getPlayers().add(player);
        }
        return teamRepository.save(team);
    }
    
    // Remove a player from a team
    public Team removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Player player = playerService.getPlayerById(playerId);
        team.getPlayers().remove(player);
        return teamRepository.save(team);
    }
}
```

### Step 3.4 - Create Team Controller

Create `src/main/java/com/fantasyfootball/team/TeamController.java`:

```java
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
    
    // Add player to team
    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        Team updated = teamService.addPlayerToTeam(teamId, playerId);
        return ResponseEntity.ok(updated);
    }
    
    // Remove player from team
    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        Team updated = teamService.removePlayerFromTeam(teamId, playerId);
        return ResponseEntity.ok(updated);
    }
}
```

---

## Step 4: Update application.yml (10 mins)

Ensure JPA logging is helpful for debugging relationships:

```yaml
spring:
  application:
    name: fantasy-football
  
  datasource:
    url: jdbc:h2:mem:fantasydb
    driverClassName: org.h2.Driver
    username: sa
    password:
  
  h2:
    console:
      enabled: true
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    defer-datasource-initialization: true
    show-sql: false
    properties:
      hibernate:
        format_sql: true

logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE

server:
  port: 8080
```

---

## Step 5: Write Tests (1 hour)

### Step 5.1 - League Integration Tests

Create `src/test/java/com/fantasyfootball/league/LeagueControllerTest.java`:

```java
package com.fantasyfootball.league;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LeagueControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateLeague() throws Exception {
        League league = League.builder()
            .name("Premier Fantasy League")
            .description("Top fantasy league")
            .status(League.LeagueStatus.DRAFT)
            .build();
        
        mockMvc.perform(post("/leagues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(league)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Premier Fantasy League"));
    }
    
    @Test
    void testGetAllLeagues() throws Exception {
        mockMvc.perform(get("/leagues"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void testGetLeagueById() throws Exception {
        mockMvc.perform(get("/leagues/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }
}
```

### Step 5.2 - Team Integration Tests

Create `src/test/java/com/fantasyfootball/team/TeamControllerTest.java`:

```java
package com.fantasyfootball.team;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateTeam() throws Exception {
        Team team = Team.builder()
            .name("My Dream Team")
            .managerName("John Doe")
            .budget(100)
            .build();
        
        mockMvc.perform(post("/teams/league/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(team)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("My Dream Team"));
    }
    
    @Test
    void testGetTeamsByLeague() throws Exception {
        mockMvc.perform(get("/teams/league/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void testAddPlayerToTeam() throws Exception {
        mockMvc.perform(post("/teams/1/players/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.players").isArray());
    }
}
```

---

## Step 6: Test Your Implementation

### Step 6.1 - Start the App
```bash
mvn clean spring-boot:run
```

### Step 6.2 - Test Endpoints with Postman or curl

**Create a League:**
```bash
curl -X POST http://localhost:8080/leagues \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Premier Fantasy League",
    "description": "Top fantasy players",
    "status": "DRAFT"
  }'
```

**Create a Team (note the leagueId from previous response):**
```bash
curl -X POST http://localhost:8080/teams/league/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Dream Team",
    "managerName": "John Doe",
    "budget": 100
  }'
```

**Add a Player to Team:**
```bash
curl -X POST http://localhost:8080/teams/1/players/1
```

**Get All Teams in a League:**
```bash
curl http://localhost:8080/teams/league/1
```

### Step 6.3 - Run Tests
```bash
mvn test
```

---

## Architecture Summary

```
FantasyFootballApplication
    |
    ├── league/
    │   ├── League.java (Entity)
    │   ├── LeagueRepository.java
    │   ├── LeagueService.java
    │   ├── LeagueController.java
    │   └── LeagueControllerTest.java
    |
    ├── team/
    │   ├── Team.java (Entity)
    │   ├── TeamRepository.java
    │   ├── TeamService.java
    │   ├── TeamController.java
    │   └── TeamControllerTest.java
    |
    └── player/
        └── (Updated Player.java with relationships)
```

---

## Database Schema (Auto-Generated by Hibernate)

```sql
CREATE TABLE league (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    started_at TIMESTAMP
);

CREATE TABLE team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    manager_name VARCHAR(255) NOT NULL,
    budget INT,
    league_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (league_id) REFERENCES league(id)
);

CREATE TABLE team_player (
    team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    PRIMARY KEY (team_id, player_id),
    FOREIGN KEY (team_id) REFERENCES team(id),
    FOREIGN KEY (player_id) REFERENCES player(id)
);
```

---

## Common Issues & Solutions

### Issue: "LazyInitializationException" when accessing nested objects
**Solution:** Use `@Transactional` on service methods or change fetch type to `EAGER`

### Issue: "Foreign key constraint failed" when creating team
**Solution:** Ensure league with that ID exists before creating team

### Issue: Circular JSON serialization (League → Team → League)
**Solution:** Add `@JsonIgnore` on one side of relationship:
```java
@OneToMany(mappedBy = "league")
@JsonIgnore
private List<Team> teams;
```

---

## Next Steps After Phase 2

Once Phase 2 is complete and tested:

1. **Phase 3:** Match statistics and fantasy points calculation
   - Create `Match` and `MatchStatistic` entities
   - Build calculation logic (goals, assists, clean sheets, etc.)

2. **Database Migration:** Switch from H2 to PostgreSQL
   - Add PostgreSQL driver to pom.xml
   - Update application.yml

3. **Error Handling:** Global exception handler
   - Create `GlobalExceptionHandler.java`
   - Handle 400, 404, 500 errors gracefully

4. **API Documentation:** Swagger/Springdoc
   - Add Springdoc OpenAPI dependency
   - Auto-generate API docs at `/swagger-ui.html`

---

## Key Learnings in Phase 2

✅ **JPA One-to-Many relationships** (@ManyToOne, @OneToMany)  
✅ **JPA Many-to-Many relationships** (@ManyToMany, @JoinTable)  
✅ **Service layer patterns** (business logic separation)  
✅ **REST CRUD operations** (POST, GET, PUT, DELETE)  
✅ **Integration testing** with MockMvc  
✅ **Lombok annotations** (@Builder, @Data)  
✅ **Cascade operations** (automatic child updates)  

---

Good luck with Phase 2! This is a solid foundation for your fantasy football backend. 🚀
