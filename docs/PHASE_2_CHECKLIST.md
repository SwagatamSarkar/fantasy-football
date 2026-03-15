# Phase 2 Implementation Checklist

## Overview
This checklist breaks down Phase 2 (League & Team Management) into concrete, trackable tasks.  
**Estimated Total Time:** 4-5 hours  
**Target Completion:** After completing all items, you'll have a fully functional League and Team API.

---

## ✅ Part 1: Update Existing Code (30 minutes)

### Task 1.1: Update Player Entity
- [ ] Open `src/main/java/com/fantasyfootball/player/Player.java`
- [ ] Add `marketValue` field (Double, nullable)
- [ ] Add `@ManyToMany(mappedBy = "players")` relationship to teams
- [ ] Add `java.util.List<Team>` field for teams
- [ ] Add import: `import com.fantasyfootball.team.Team;`
- [ ] Verify code compiles: `mvn compile`

---

## ✅ Part 2: Create League Module (45 minutes)

### Task 2.1: Create League Entity
- [ ] Create folder: `src/main/java/com/fantasyfootball/league/`
- [ ] Create file: `League.java`
- [ ] Add fields: id, name, description, status, createdAt, startedAt
- [ ] Add enum: `LeagueStatus` (DRAFT, ACTIVE, COMPLETED)
- [ ] Add relationship: `@OneToMany` for teams
- [ ] Add `@PrePersist` method to set createdAt and default status
- [ ] Add `@Builder` annotation from Lombok
- [ ] Verify code compiles

### Task 2.2: Create League Repository
- [ ] Create file: `LeagueRepository.java`
- [ ] Extend `JpaRepository<League, Long>`
- [ ] Add method: `findByStatus(League.LeagueStatus status)`
- [ ] Mark with `@Repository` annotation
- [ ] Verify code compiles

### Task 2.3: Create League Service
- [ ] Create file: `LeagueService.java`
- [ ] Mark with `@Service` annotation
- [ ] Inject `LeagueRepository`
- [ ] Implement methods:
  - [ ] `getAllLeagues()`
  - [ ] `getLeagueById(Long id)`
  - [ ] `getLeaguesByStatus(LeagueStatus status)`
  - [ ] `createLeague(League league)`
  - [ ] `updateLeague(Long id, League updatedLeague)`
  - [ ] `deleteLeague(Long id)`
- [ ] Add proper exception handling (IllegalArgumentException for not found)
- [ ] Verify code compiles

### Task 2.4: Create League Controller
- [ ] Create file: `LeagueController.java`
- [ ] Mark with `@RestController` and `@RequestMapping("/leagues")`
- [ ] Inject `LeagueService`
- [ ] Implement endpoints:
  - [ ] `GET /leagues` → `getAllLeagues()`
  - [ ] `GET /leagues/{id}` → `getLeagueById(Long id)`
  - [ ] `GET /leagues/status/{status}` → `getLeaguesByStatus(LeagueStatus status)`
  - [ ] `POST /leagues` → `createLeague(League league)` [HTTP 201]
  - [ ] `PUT /leagues/{id}` → `updateLeague(Long id, League league)`
  - [ ] `DELETE /leagues/{id}` → `deleteLeague(Long id)` [HTTP 204]
- [ ] All endpoints return `ResponseEntity<T>`
- [ ] Verify code compiles

---

## ✅ Part 3: Create Team Module (1 hour)

### Task 3.1: Create Team Entity
- [ ] Create folder: `src/main/java/com/fantasyfootball/team/`
- [ ] Create file: `Team.java`
- [ ] Add fields: id, name, managerName, budget, createdAt
- [ ] Add relationship: `@ManyToOne` to League
- [ ] Add relationship: `@ManyToMany` to Player with `@JoinTable`
- [ ] Add `@PrePersist` method:
  - [ ] Set createdAt to now
  - [ ] Set default budget to 100 if null
- [ ] Add `@Builder` annotation
- [ ] Verify code compiles

### Task 3.2: Create Team Repository
- [ ] Create file: `TeamRepository.java`
- [ ] Extend `JpaRepository<Team, Long>`
- [ ] Add method: `findByLeagueId(Long leagueId)`
- [ ] Mark with `@Repository` annotation
- [ ] Verify code compiles

### Task 3.3: Create Team Service
- [ ] Create file: `TeamService.java`
- [ ] Mark with `@Service` annotation
- [ ] Inject: `TeamRepository`, `LeagueService`, `PlayerService`
- [ ] Implement methods:
  - [ ] `getAllTeams()`
  - [ ] `getTeamById(Long id)`
  - [ ] `getTeamsByLeague(Long leagueId)` [verify league exists]
  - [ ] `createTeam(Long leagueId, Team team)` [set league from leagueId]
  - [ ] `updateTeam(Long id, Team updatedTeam)`
  - [ ] `deleteTeam(Long id)`
  - [ ] `addPlayerToTeam(Long teamId, Long playerId)` [check player exists first]
  - [ ] `removePlayerFromTeam(Long teamId, Long playerId)`
- [ ] Add exception handling
- [ ] Verify code compiles

### Task 3.4: Create Team Controller
- [ ] Create file: `TeamController.java`
- [ ] Mark with `@RestController` and `@RequestMapping("/teams")`
- [ ] Inject `TeamService`
- [ ] Implement endpoints:
  - [ ] `GET /teams` → `getAllTeams()`
  - [ ] `GET /teams/{id}` → `getTeamById(Long id)`
  - [ ] `GET /teams/league/{leagueId}` → `getTeamsByLeague(Long leagueId)`
  - [ ] `POST /teams/league/{leagueId}` → `createTeam(Long leagueId, Team team)` [HTTP 201]
  - [ ] `PUT /teams/{id}` → `updateTeam(Long id, Team team)`
  - [ ] `DELETE /teams/{id}` → `deleteTeam(Long id)` [HTTP 204]
  - [ ] `POST /teams/{teamId}/players/{playerId}` → `addPlayerToTeam(...)`
  - [ ] `DELETE /teams/{teamId}/players/{playerId}` → `removePlayerFromTeam(...)`
- [ ] All endpoints return `ResponseEntity<T>`
- [ ] Verify code compiles

---

## ✅ Part 4: Update Configuration (10 minutes)

### Task 4.1: Update application.yml
- [ ] Open `src/main/resources/application.yml`
- [ ] Add logging section:
  ```yaml
  logging:
    level:
      org.hibernate.SQL: DEBUG
      org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  ```
- [ ] Verify no syntax errors

---

## ✅ Part 5: Write Integration Tests (1 hour)

### Task 5.1: Create League Controller Tests
- [ ] Create folder: `src/test/java/com/fantasyfootball/league/`
- [ ] Create file: `LeagueControllerTest.java`
- [ ] Mark with `@SpringBootTest` and `@AutoConfigureMockMvc`
- [ ] Inject: `MockMvc`, `ObjectMapper`
- [ ] Write tests:
  - [ ] `testCreateLeague()` → POST, verify HTTP 201 and name in response
  - [ ] `testGetAllLeagues()` → GET, verify HTTP 200 and array
  - [ ] `testGetLeagueById()` → GET /{id}, verify HTTP 200
  - [ ] `testUpdateLeague()` → PUT, verify updated name
  - [ ] `testDeleteLeague()` → DELETE, verify HTTP 204
  - [ ] `testGetLeaguesByStatus()` → GET /status/{status}, verify filtering
- [ ] Run: `mvn test -Dtest=LeagueControllerTest`
- [ ] All tests pass ✅

### Task 5.2: Create Team Controller Tests
- [ ] Create folder: `src/test/java/com/fantasyfootball/team/`
- [ ] Create file: `TeamControllerTest.java`
- [ ] Mark with `@SpringBootTest` and `@AutoConfigureMockMvc`
- [ ] Inject: `MockMvc`, `ObjectMapper`
- [ ] Write tests:
  - [ ] `testCreateTeam()` → POST /teams/league/{leagueId}, verify HTTP 201
  - [ ] `testGetAllTeams()` → GET, verify array
  - [ ] `testGetTeamById()` → GET /{id}, verify team data
  - [ ] `testGetTeamsByLeague()` → GET /league/{leagueId}, verify filtering
  - [ ] `testUpdateTeam()` → PUT, verify updated name
  - [ ] `testDeleteTeam()` → DELETE, verify HTTP 204
  - [ ] `testAddPlayerToTeam()` → POST /{teamId}/players/{playerId}, verify player added
  - [ ] `testRemovePlayerFromTeam()` → DELETE /{teamId}/players/{playerId}, verify removed
- [ ] Run: `mvn test -Dtest=TeamControllerTest`
- [ ] All tests pass ✅

---

## ✅ Part 6: Manual Testing (1 hour)

### Task 6.1: Start Application
- [ ] Run: `mvn clean spring-boot:run`
- [ ] Wait for "Started FantasyFootballApplication" message
- [ ] Verify: `http://localhost:8080/players` returns existing players

### Task 6.2: Test League Endpoints
- [ ] Create league: `POST /leagues` with name and description
- [ ] Record league ID from response
- [ ] Get all leagues: `GET /leagues` → verify league in list
- [ ] Get league by ID: `GET /leagues/{id}` → verify league details
- [ ] Update league: `PUT /leagues/{id}` with new name
- [ ] Verify update: `GET /leagues/{id}` → name changed
- [ ] Get by status: `GET /leagues/status/DRAFT` → verify league in results

### Task 6.3: Test Team Endpoints
- [ ] Create team: `POST /teams/league/{leagueId}` with name, managerName, budget
- [ ] Record team ID from response
- [ ] Get all teams: `GET /teams` → verify team in list
- [ ] Get teams by league: `GET /teams/league/{leagueId}` → verify filtering
- [ ] Get team by ID: `GET /teams/{id}` → verify team details
- [ ] Update team: `PUT /teams/{id}` with new name
- [ ] Verify update: `GET /teams/{id}` → name changed

### Task 6.4: Test Team-Player Endpoints
- [ ] Add player to team: `POST /teams/{teamId}/players/1`
- [ ] Get team: `GET /teams/{teamId}` → verify player in response
- [ ] Add another player: `POST /teams/{teamId}/players/2`
- [ ] Get team: `GET /teams/{teamId}` → verify 2 players
- [ ] Remove player: `DELETE /teams/{teamId}/players/1`
- [ ] Get team: `GET /teams/{teamId}` → verify 1 player remaining

### Task 6.5: Test Database State
- [ ] Open: `http://localhost:8080/h2-console`
- [ ] Login with defaults (sa, blank password)
- [ ] Query and verify:
  - [ ] `SELECT * FROM league;` → see created leagues
  - [ ] `SELECT * FROM team;` → see created teams
  - [ ] `SELECT * FROM team_player;` → see team-player relationships

---

## ✅ Part 7: Run Full Test Suite (10 minutes)

### Task 7.1: Run All Tests
- [ ] Run: `mvn test`
- [ ] Verify output: "BUILD SUCCESS"
- [ ] Check test count: should have ~15+ tests total
- [ ] All tests pass ✅

### Task 7.2: Build Project
- [ ] Run: `mvn clean package`
- [ ] Verify output: "BUILD SUCCESS"
- [ ] Jar file created at: `target/fantasy-football-0.0.1-SNAPSHOT.jar`

---

## ✅ Part 8: Verify Relationships

### Task 8.1: Test Complex Scenarios
- [ ] Create 2 leagues
- [ ] Create 3 teams in league 1, 2 teams in league 2
- [ ] Add 5 different players to each team
- [ ] Query `GET /teams/league/1` → verify only 3 teams returned
- [ ] Query `GET /teams/1` → verify all 5 players returned
- [ ] Remove a player: `DELETE /teams/1/players/1`
- [ ] Query `GET /teams/1` → verify player removed
- [ ] Query `GET /players/1` → verify player still exists (can be added to other teams)

---

## ✅ Final Verification

- [ ] All code compiles without warnings
- [ ] All tests pass (mvn test)
- [ ] Application starts without errors (mvn clean spring-boot:run)
- [ ] All endpoints respond correctly
- [ ] Database schema matches entities
- [ ] Relationships work bidirectionally where appropriate
- [ ] Error handling returns appropriate HTTP status codes

---

## Summary of Deliverables

After completing all checkboxes, you will have:

✅ **6 new Java classes:**
- League.java (Entity)
- LeagueRepository.java
- LeagueService.java
- LeagueController.java
- Team.java (Entity)
- TeamRepository.java
- TeamService.java
- TeamController.java

✅ **8 new test classes:**
- LeagueControllerTest.java
- TeamControllerTest.java (with 8+ test methods)

✅ **16+ REST endpoints:**
- 6 for League CRUD
- 8 for Team CRUD + Player management

✅ **Database schema:**
- league table
- team table
- team_player join table

✅ **Tested features:**
- Full CRUD operations for leagues and teams
- Team-player relationships
- Cascading operations
- Error handling

---

## Time Breakdown

| Section | Time | Status |
|---------|------|--------|
| Part 1: Update Player | 10 min | ⏱️ |
| Part 2: League Module | 45 min | ⏱️ |
| Part 3: Team Module | 60 min | ⏱️ |
| Part 4: Configuration | 10 min | ⏱️ |
| Part 5: Integration Tests | 60 min | ⏱️ |
| Part 6: Manual Testing | 60 min | ⏱️ |
| Part 7: Full Test Suite | 10 min | ⏱️ |
| Part 8: Verification | 15 min | ⏱️ |
| **Total** | **270 min (4.5 hrs)** | 🚀 |

---

## Next Steps (After Phase 2)

Once all items above are complete ✅:

1. **Phase 3: Match Statistics**
   - Create Match entity
   - Create MatchStatistic entity
   - Build points calculation engine

2. **Database Migration**
   - Switch from H2 to PostgreSQL
   - Add Flyway for migrations

3. **Error Handling**
   - Global exception handler
   - Custom exception classes

4. **API Documentation**
   - Add Springdoc OpenAPI
   - Generate Swagger UI

Good luck! You've got this! 🚀
