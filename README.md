# Fantasy Football (European Soccer) 🏆

> A **zero-cost, open-source fantasy football platform** for European soccer leagues, built with **clean backend architecture** and **industry best practices**.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=java&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Status: Active Development](https://img.shields.io/badge/Status-Active%20Development-brightgreen)]()

---

## 📌 Quick Links

- **[Getting Started](#getting-started)** – Setup and run the project
- **[Project Structure](#project-structure)** – Codebase organization
- **[API Documentation](#api-documentation)** – All endpoints and examples
- **[Development Roadmap](#development-roadmap)** – Current & future phases
- **[Testing](#testing)** – How to run tests
- **[Technology Stack](#technology-stack)** – Tools and frameworks

---

## 🎯 Project Overview

Fantasy Football is a **portfolio + learning project** demonstrating:

- ✅ **Clean REST API architecture** using Spring Boot 3
- ✅ **Feature-based modular design** with clear separation of concerns
- ✅ **Industry-standard practices** (layered architecture, dependency injection, testing)
- ✅ **Scalable foundation** for future microservices
- ✅ **Zero infrastructure cost** using open-source tools

### Current Status

- ✅ **Phase 1:** Player management API (Complete)
- ✅ **Phase 2:** League & Team management (Complete)
- 🔄 **Phase 3:** Match statistics & fantasy points calculation (Planned)
- 📅 **Phase 4:** Web UI & automation (Planned)

**Overall Progress:** 40% Complete 🚀

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher ([Download](https://openjdk.org/))
- **Maven 3.9+** ([Download](https://maven.apache.org/))
- **Git** ([Download](https://git-scm.com/))
- **Postman** (optional, for API testing) ([Download](https://www.postman.com/))

### Installation

**1. Clone the Repository**
```bash
git clone https://github.com/yourusername/fantasy-football.git
cd fantasy-football
```

**2. Build the Project**
```bash
mvn clean install
```

**3. Start the Application**
```bash
mvn spring-boot:run
```

You should see:
```
Started FantasyFootballApplication in X.XXX seconds
```

**4. Test the API**

Open in browser or use curl:
```bash
curl http://localhost:8080/players
```

You'll see JSON response with all seeded players (120+).

✅ **Success!** Your API is running.

---

## 📚 Project Structure

```
fantasy-football/
├── README.md                          # This file
├── .gitignore                        # Git ignore rules
├── pom.xml                            # Maven dependencies
│
├── src/main/java/com/fantasyfootball/
│   ├── FantasyFootballApplication.java  # Spring Boot entry point
│   │
│   ├── player/                        # Player domain (Phase 1)
│   │   ├── Player.java                # JPA Entity
│   │   ├── PlayerRepository.java      # Data access layer
│   │   ├── PlayerService.java         # Business logic layer
│   │   └── PlayerController.java      # REST endpoints
│   │
│   ├── league/                        # League domain (Phase 2)
│   │   ├── League.java                # JPA Entity
│   │   ├── LeagueRepository.java      # Data access layer
│   │   ├── LeagueService.java         # Business logic layer
│   │   └── LeagueController.java      # REST endpoints (6 endpoints)
│   │
│   └── team/                          # Team domain (Phase 2)
│       ├── Team.java                  # JPA Entity
│       ├── TeamRepository.java        # Data access layer
│       ├── TeamService.java           # Business logic layer
│       └── TeamController.java        # REST endpoints (8 endpoints)
│
├── src/main/resources/
│   ├── application.yml                # Spring Boot configuration
│   └── import.sql                     # Player seed data (~120 players)
│
├── src/test/java/com/fantasyfootball/
│   ├── FantasyFootballApplicationTests.java
│   ├── player/
│   │   └── PlayerControllerTest.java  # Unit tests (3 tests)
│   ├── league/
│   │   └── LeagueControllerTest.java  # Unit tests (6 tests)
│   └── team/
│       └── TeamControllerTest.java    # Unit tests (8 tests)
│
└── docs/                              # Documentation
    ├── PHASE_2_IMPLEMENTATION_GUIDE.md
    ├── QUICK_REFERENCE.md
    └── PHASE_2_CHECKLIST.md
```

### Architecture Pattern

```
Client (Browser / Postman / Tests)
    ↓
REST Controller (@RestController)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access - JPA)
    ↓
H2 Database (In-Memory) → PostgreSQL (Future)
```

Each **domain module** (player, league, team) follows this pattern independently.

---

## 🔌 API Documentation

### Base URL
```
http://localhost:8080
```

### Players API (Phase 1) - 3 Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/players` | Get all players |
| `GET` | `/players/{id}` | Get player by ID |
| `POST` | `/players` | Create new player |

#### Player Examples

**Get All Players (120+ seeded players)**
```bash
curl http://localhost:8080/players
```

**Get Player by ID**
```bash
curl http://localhost:8080/players/1
```

**Create New Player**
```bash
curl -X POST http://localhost:8080/players \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Vinicius Junior",
    "position": "FORWARD",
    "club": "Real Madrid",
    "league": "La Liga"
  }'
```

---

### Leagues API (Phase 2) - 6 Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/leagues` | Get all leagues |
| `GET` | `/leagues/{id}` | Get league by ID |
| `GET` | `/leagues/status/{status}` | Get leagues by status (DRAFT/ACTIVE/COMPLETED) |
| `POST` | `/leagues` | Create new league |
| `PUT` | `/leagues/{id}` | Update league |
| `DELETE` | `/leagues/{id}` | Delete league |

#### League Examples

**Create a League**
```bash
curl -X POST http://localhost:8080/leagues \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Premier Fantasy League",
    "description": "Top fantasy players",
    "status": "DRAFT"
  }'
```

Response:
```json
{
  "id": 1,
  "name": "Premier Fantasy League",
  "description": "Top fantasy players",
  "status": "DRAFT",
  "createdAt": "2025-03-15T10:30:00"
}
```

**Get All Leagues**
```bash
curl http://localhost:8080/leagues
```

**Get Leagues by Status**
```bash
curl http://localhost:8080/leagues/status/DRAFT
```

---

### Teams API (Phase 2) - 8 Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/teams` | Get all teams |
| `GET` | `/teams/{id}` | Get team by ID |
| `GET` | `/teams/league/{leagueId}` | Get teams in a league |
| `POST` | `/teams/league/{leagueId}` | Create team in league |
| `PUT` | `/teams/{id}` | Update team |
| `DELETE` | `/teams/{id}` | Delete team |
| `POST` | `/teams/{teamId}/players/{playerId}` | Add player to team |
| `DELETE` | `/teams/{teamId}/players/{playerId}` | Remove player from team |

#### Team Examples

**Create a Team** (in league with id=1)
```bash
curl -X POST http://localhost:8080/teams/league/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Dream Team",
    "managerName": "John Doe",
    "budget": 100
  }'
```

Response:
```json
{
  "id": 1,
  "name": "My Dream Team",
  "managerName": "John Doe",
  "budget": 100,
  "createdAt": "2025-03-15T10:35:00",
  "league": {
    "id": 1,
    "name": "Premier Fantasy League"
  },
  "players": []
}
```

**Add Player to Team** (team id=1, player id=1)
```bash
curl -X POST http://localhost:8080/teams/1/players/1
```

**Get All Teams**
```bash
curl http://localhost:8080/teams
```

**Get Teams by League**
```bash
curl http://localhost:8080/teams/league/1
```

---

## 📊 Database Schema

### Current Setup (Phases 1 & 2)

- **Type:** H2 In-Memory Database
- **Console:** `http://localhost:8080/h2-console`
- **Credentials:** Username: `sa`, Password: (blank)

### Tables

```sql
-- Phase 1: Players
CREATE TABLE player (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    position VARCHAR(50) NOT NULL,
    club VARCHAR(255) NOT NULL,
    league VARCHAR(100) NOT NULL,
    market_value DOUBLE
);

-- Phase 2: Leagues
CREATE TABLE league (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    started_at DATETIME
);

-- Phase 2: Teams
CREATE TABLE team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    manager_name VARCHAR(255) NOT NULL,
    budget INT DEFAULT 100,
    league_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (league_id) REFERENCES league(id)
);

-- Phase 2: Team-Player Many-to-Many Relationship
CREATE TABLE team_player (
    team_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    PRIMARY KEY (team_id, player_id),
    FOREIGN KEY (team_id) REFERENCES team(id),
    FOREIGN KEY (player_id) REFERENCES player(id)
);
```

### Seed Data

The application loads **~120 real players** from major European leagues:

- 🏴󐁧󐁢󐁥󐁮󐁧󐁿 **Premier League** (England)
- 🇪🇸 **La Liga** (Spain)
- 🇮🇹 **Serie A** (Italy)
- 🇩🇪 **Bundesliga** (Germany)
- 🇫🇷 **Ligue 1** (France)

Players include stars like: Erling Haaland, Kylian Mbappé, Vinícius Júnior, Jude Bellingham, etc.

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PlayerControllerTest
mvn test -Dtest=LeagueControllerTest
mvn test -Dtest=TeamControllerTest
```

### Test Coverage

Current test suite includes:
- ✅ **17 Unit Tests** across all modules
- ✅ Player entity and position enum tests
- ✅ League entity, builder, and status enum tests
- ✅ Team entity, builder, and relationships tests
- ✅ All tests passing

---

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 3.2** – REST API framework
- **Spring Web** – HTTP request handling
- **Spring Data JPA** – Object-Relational Mapping (ORM)

### Database
- **H2 Database** – In-memory (Phases 1-2)
- **PostgreSQL** – Production database (Phase 3+)

### Build & Dependency Management
- **Maven 3.9** – Project build tool
- **Java 21** – Language version

### Development Tools
- **Lombok** – Reduce boilerplate code (@Data, @Builder)
- **JUnit 5** – Unit testing framework
- **Postman** – API testing client

### Future (Phase 3+)
- **Swagger/Springdoc** – API documentation
- **Flyway/Liquibase** – Database migrations
- **Docker** – Containerization
- **React** – Web frontend

---

## 📈 Development Roadmap

### ✅ Phase 1: Core Backend (Complete)
- [x] Spring Boot project setup
- [x] Player entity and database seeding (~120 real players)
- [x] REST API endpoints for players (3 endpoints)
- [x] Unit tests with 100% pass rate
- [x] H2 in-memory database

**Status:** ✅ Complete & Deployed to GitHub

**Commit:** `feat: Phase 1 - Player management API with seed data`

---

### ✅ Phase 2: League & Team Management (Complete)

**Goal:** Add fantasy league and team management features

**What's Implemented:**
- [x] League entity with CRUD operations
- [x] Team entity linked to leagues
- [x] Team-player relationships (many-to-many)
- [x] REST APIs for league and team management (14 endpoints total)
- [x] Unit tests for all modules (6 league + 8 team tests)
- [x] Database schema with proper foreign keys
- [x] All endpoints tested and working in Postman

**New Endpoints (14 Total):**
- **Leagues:** 6 endpoints (GET, GET by ID, GET by status, POST, PUT, DELETE)
- **Teams:** 8 endpoints (GET, GET by ID, GET by league, POST, PUT, DELETE, Add player, Remove player)

**Technologies Used:**
- Spring Data JPA for data access
- Lombok @Builder and @Data annotations
- JPA relationships: @OneToMany, @ManyToOne, @ManyToMany
- @JoinTable for team-player mapping

**Status:** ✅ Complete & Ready for Phase 3

**Next Commit:** `feat: Phase 2 - League and Team management with working APIs`

---

### 🔄 Phase 3: Match Scoring & Fantasy Points (Planned)

**Goal:** Implement match statistics and fantasy points calculation

**Planned Features:**
- Match entity and statistics tracking
- Player performance recording (goals, assists, cards, saves, etc.)
- Fantasy points calculation engine
   - Position-based scoring (GK/DEF/MID/FWD)
   - Bonuses for clean sheets and hat tricks
   - Penalties for yellow/red cards
- Leaderboards and rankings
- 14 new REST endpoints

**Estimated Duration:** 5-6 hours (manual) or 30-45 minutes (agentic AI)

**Status:** 📋 Fully Planned (see PHASE_3_PLANNING.md)

---

### 📅 Phase 4: Web UI & Automation (Future)

**Goal:** Build frontend and automation testing

**Planned Features:**
- React web dashboard
- Create/manage leagues and teams UI
- View leaderboards
- Player selection interface
- Automation tests with Selenium/Playwright

**Status:** 📅 Future

---

## 📝 Documentation

Additional documentation files available in repository:

- **PHASE_2_IMPLEMENTATION_GUIDE.md** – Step-by-step Phase 2 guide
- **PHASE_2_CHECKLIST.md** – 50+ task checklist
- **PHASE_3_PLANNING.md** – Complete Phase 3 specification
- **QUICK_REFERENCE.md** – Commands and endpoints reference

---

## 🚀 How to Extend This Project

### Add a New Domain Module

1. Create folder structure: `src/main/java/com/fantasyfootball/newmodule/`
2. Create entity class with `@Entity` and `@Table` annotations
3. Create repository extending `JpaRepository<Entity, Long>`
4. Create service with business logic
5. Create controller with `@RestController` endpoints
6. Create test class with unit tests
7. Update this README

### Example: Adding a Match Module

```bash
mkdir -p src/main/java/com/fantasyfootball/match
mkdir -p src/test/java/com/fantasyfootball/match
```

Then create:
- `Match.java` (entity)
- `MatchRepository.java` (data access)
- `MatchService.java` (business logic)
- `MatchController.java` (REST endpoints)
- `MatchControllerTest.java` (tests)

---

## 🔗 Related Projects

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [RESTful API Design Best Practices](https://restfulapi.net/)
- [JUnit 5 Documentation](https://junit.org/junit5/)

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

**Your Name**  
GitHub: [@yourusername](https://github.com/yourusername)  
Portfolio: [your-website.com](https://your-website.com)

---

## 🤝 Contributing

Contributions are welcome! Here's how:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## 📮 Support

For issues, questions, or suggestions:
- Open an GitHub issue
- Check existing documentation
- Review QUICK_REFERENCE.md for common commands

---

**Last Updated:** March 15, 2025  
**Status:** Active Development ✅  
**Next Phase:** Phase 3 - Match Statistics & Fantasy Points Calculation