# Fantasy Football - Quick Reference Guide

## Project Structure (After Phase 2)

```
fantasy-football/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/fantasyfootball/
│   │   │   ├── FantasyFootballApplication.java
│   │   │   ├── league/
│   │   │   │   ├── League.java
│   │   │   │   ├── LeagueRepository.java
│   │   │   │   ├── LeagueService.java
│   │   │   │   └── LeagueController.java
│   │   │   ├── team/
│   │   │   │   ├── Team.java
│   │   │   │   ├── TeamRepository.java
│   │   │   │   ├── TeamService.java
│   │   │   │   └── TeamController.java
│   │   │   └── player/
│   │   │       ├── Player.java
│   │   │       ├── PlayerRepository.java
│   │   │       ├── PlayerService.java
│   │   │       └── PlayerController.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── import.sql
│   └── test/
│       └── java/com/fantasyfootball/
│           ├── league/LeagueControllerTest.java
│           ├── team/TeamControllerTest.java
│           └── player/PlayerControllerTest.java
```

---

## Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean spring-boot:run` | Start the application |
| `mvn test` | Run all tests |
| `mvn test -Dtest=LeagueControllerTest` | Run specific test class |
| `mvn clean package` | Build JAR file |
| `mvn dependency:tree` | View dependencies |
| `mvn clean` | Clear target directory |

---

## API Endpoints (Phase 1 + 2)

### Players
```
GET    /players              # Get all players
GET    /players/{id}         # Get player by ID
POST   /players              # Create new player
```

### Leagues
```
GET    /leagues              # Get all leagues
GET    /leagues/{id}         # Get league by ID
GET    /leagues/status/{status}  # Get leagues by status (DRAFT, ACTIVE, COMPLETED)
POST   /leagues              # Create new league
PUT    /leagues/{id}         # Update league
DELETE /leagues/{id}         # Delete league
```

### Teams
```
GET    /teams                # Get all teams
GET    /teams/{id}           # Get team by ID
GET    /teams/league/{leagueId}  # Get all teams in a league
POST   /teams/league/{leagueId}  # Create team in league
PUT    /teams/{id}           # Update team
DELETE /teams/{id}           # Delete team
POST   /teams/{teamId}/players/{playerId}     # Add player to team
DELETE /teams/{teamId}/players/{playerId}     # Remove player from team
```

---

## Testing with Postman

### 1. Create League
```
POST http://localhost:8080/leagues
Content-Type: application/json

{
  "name": "Premier Fantasy League",
  "description": "Top league for fantasy players",
  "status": "DRAFT"
}
```

### 2. Create Team
```
POST http://localhost:8080/teams/league/1
Content-Type: application/json

{
  "name": "Manchester Magic",
  "managerName": "John Doe",
  "budget": 100
}
```

### 3. Add Player to Team
```
POST http://localhost:8080/teams/1/players/1
```

### 4. Get Team with Players
```
GET http://localhost:8080/teams/1
```

---

## Common Curl Commands

```bash
# Create League
curl -X POST http://localhost:8080/leagues \
  -H "Content-Type: application/json" \
  -d '{"name":"Test League","description":"Test","status":"DRAFT"}'

# Get all leagues
curl http://localhost:8080/leagues

# Get league by ID
curl http://localhost:8080/leagues/1

# Create team
curl -X POST http://localhost:8080/teams/league/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"My Team","managerName":"John","budget":100}'

# Add player to team
curl -X POST http://localhost:8080/teams/1/players/1

# Remove player from team
curl -X DELETE http://localhost:8080/teams/1/players/1

# Get all teams in league 1
curl http://localhost:8080/teams/league/1
```

---

## H2 Database Console

Access at: `http://localhost:8080/h2-console`

**Credentials:**
- JDBC URL: `jdbc:h2:mem:fantasydb`
- Username: `sa`
- Password: (leave blank)

**Useful Queries:**
```sql
SELECT * FROM player;
SELECT * FROM league;
SELECT * FROM team;
SELECT * FROM team_player;
```

---

## Key Spring Boot Annotations

| Annotation | Purpose |
|------------|---------|
| `@Entity` | Marks class as JPA entity (maps to database table) |
| `@Repository` | Marks class as data access layer |
| `@Service` | Marks class as business logic layer |
| `@RestController` | Marks class as REST API controller |
| `@RequestMapping` | Maps HTTP requests to controller methods |
| `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` | HTTP verb shortcuts |
| `@Autowired` | Injects dependency (constructor injection preferred) |
| `@Transactional` | Marks method as transactional (database operation) |
| `@Column` | Maps field to database column |
| `@ManyToOne` | Many-to-one relationship |
| `@OneToMany` | One-to-many relationship |
| `@ManyToMany` | Many-to-many relationship |
| `@JoinTable` | Specifies join table for many-to-many |
| `@JsonIgnore` | Excludes field from JSON serialization |

---

## Lombok Annotations

| Annotation | Purpose |
|------------|---------|
| `@Data` | Generates getters, setters, toString, equals, hashCode |
| `@NoArgsConstructor` | Generates no-argument constructor |
| `@AllArgsConstructor` | Generates constructor with all fields |
| `@Builder` | Enables builder pattern |
| `@Getter` | Generates getters for all fields |
| `@Setter` | Generates setters for all fields |

---

## Debugging Tips

### Enable SQL Logging
Update `application.yml`:
```yaml
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

### Check Generated Tables
```bash
curl http://localhost:8080/h2-console
# View player, league, team tables
```

### Test a Single Endpoint
```bash
mvn test -Dtest=LeagueControllerTest::testCreateLeague
```

### Clear Database and Restart
```bash
mvn clean spring-boot:run
```
(H2 in-memory database resets on restart)

---

## Troubleshooting

### Port Already in Use
```bash
# Kill process on port 8080 (macOS/Linux)
lsof -ti:8080 | xargs kill -9
```

### Compilation Error: Missing Lombok Annotation
Ensure Lombok is enabled in IntelliJ:
- Preferences → Build, Execution, Deployment → Compiler → Annotation Processors
- Check: "Enable annotation processing"

### Foreign Key Constraint Error
Make sure the parent entity exists before creating child:
```
Creating team with league_id=1 → League with id=1 must exist first
```

### LazyInitializationException
Add `@Transactional` to service method or use `@JsonIgnore` on nested collections

---

## Performance Considerations (Future)

- Add pagination to `GET /players` endpoints
- Use DTOs instead of returning full entities
- Add caching for frequently accessed data
- Add database indexes on `league_id`, `manager_name`

---

## Next Steps

1. ✅ Complete Phase 2 (League & Team Management)
2. 📅 Phase 3: Match Statistics & Fantasy Points
   - Create `Match` entity
   - Create `MatchStatistic` entity
   - Add points calculation logic
3. 🗄️ Database: Migrate to PostgreSQL
4. 🧪 Add Playwright automation tests
5. 🎨 Build Frontend (React or simple HTML)

---

## Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JPA Relationships](https://www.baeldung.com/jpa-relationship-types)
- [Lombok Docs](https://projectlombok.org/)
- [REST API Best Practices](https://restfulapi.net/)

---

## Questions or Issues?

When troubleshooting:
1. Check logs in console (look for ERROR or WARN)
2. Test endpoint in H2 console with raw SQL
3. Verify database state (what's actually in the tables)
4. Check that entity relationships match REST API design

Good luck! 🚀
