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
- **[Contributing](#contributing)** – How to extend this project
- **[Learning Objectives](#learning-objectives)** – What you'll learn

---

## 🎯 Project Overview

Fantasy Football is a **portfolio + learning project** demonstrating:

- ✅ **Clean REST API architecture** using Spring Boot 3
- ✅ **Feature-based modular design** with clear separation of concerns
- ✅ **Industry-standard practices** (layered architecture, dependency injection, testing)
- ✅ **Scalable foundation** for future microservices
- ✅ **Zero infrastructure cost** using open-source tools

### What You Can Do (MVP)

- ✅ **Phase 1:** Load real soccer players from major European leagues
- 🔄 **Phase 2 (In Progress):** Create fantasy leagues and teams
- 📅 **Phase 3 (Planned):** Calculate fantasy points from match statistics
- 🎨 **Phase 4 (Planned):** Build web UI and automation tests

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher ([Download](https://openjdk.org/))
- **Maven 3.9+** ([Download](https://maven.apache.org/))
- **Git** ([Download](https://git-scm.com/))
- **IntelliJ IDEA Community Edition** (optional, recommended) ([Download](https://www.jetbrains.com/idea/download/))

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

You'll see JSON response with all seeded players:
```json
[
  {
    "id": 1,
    "name": "Erling Haaland",
    "position": "FORWARD",
    "club": "Manchester City",
    "league": "Premier League"
  }
]
```

✅ **Success!** Your API is running.

---

## 📚 Project Structure

```
fantasy-football/
├── README.md                          # This file
├── pom.xml                            # Maven dependencies
│
├── src/main/java/com/fantasyfootball/
│   ├── FantasyFootballApplication.java  # Spring Boot entry point
│   │
│   └── player/                        # Player domain (Phase 1)
│       ├── Player.java                # JPA Entity
│       ├── PlayerRepository.java      # Data access layer
│       ├── PlayerService.java         # Business logic layer
│       └── PlayerController.java      # REST endpoints
│
├── src/main/resources/
│   ├── application.yml               # Spring Boot configuration
│   └── import.sql                    # Player seed data (~120 players)
│
├── src/test/java/com/fantasyfootball/
│   └── player/
│       └── PlayerControllerTest.java # Integration tests
│
└── docs/                             # Documentation
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
Repository Layer (Data Access)
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

### Players API (Phase 1)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/players` | Get all players |
| `GET` | `/players/{id}` | Get player by ID |
| `POST` | `/players` | Create new player |

#### Examples

**Get All Players**
```bash
curl http://localhost:8080/players | jq
```

**Get Player by ID**
```bash
curl http://localhost:8080/players/1 | jq
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
  }' | jq
```

---

## 📊 Database

### Current Setup (Phase 1)

- **Type:** H2 In-Memory Database
- **URL:** `jdbc:h2:mem:fantasydb`
- **Console:** `http://localhost:8080/h2-console`
- **Credentials:** Username: `sa`, Password: (blank)

### Tables (Auto-created by Hibernate)

```sql
CREATE TABLE player (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    position VARCHAR(50) NOT NULL,
    club VARCHAR(255) NOT NULL,
    league VARCHAR(100) NOT NULL
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
```

### Test Coverage

Current test suite includes:
- ✅ Integration tests for all REST endpoints
- ✅ Database persistence tests
- ✅ Service layer logic tests
- ✅ Error handling tests

---

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 3.2** – REST API framework
- **Spring Web** – HTTP request handling
- **Spring Data JPA** – Object-Relational Mapping (ORM)

### Database
- **H2 Database** – In-memory (Phase 1)
- **PostgreSQL** – Production database (Phase 2+)

### Build & Dependency Management
- **Maven 3.9** – Project build tool
- **Java 21** – Language version

### Development Tools
- **Lombok** – Reduce boilerplate code
- **JUnit 5** – Unit testing framework
- **Mockito** – Test mocking (Spring provides built-in support)

### Future (Phase 3+)
- **Playwright** – API + UI automation testing
- **Swagger/Springdoc** – API documentation
- **Flyway/Liquibase** – Database migrations

---

## 📈 Development Roadmap

### ✅ Phase 1: Core Backend (Complete)
- [x] Spring Boot project setup
- [x] Player entity and database seeding
- [x] REST API endpoints for players
- [x] Integration tests
- [x] H2 in-memory database

**Status:** Ready for Phase 2 ✅

---

### 🔄 Phase 2: League & Team Management (In Progress)

**Goal:** Add fantasy league and team management features

**Deliverables:**
- [ ] League entity with CRUD operations
- [ ] Team entity linked to leagues
- [ ] Team-player relationships (many-to-many)
- [ ] REST APIs for league and team management
- [ ] Integration tests for new modules

**Estimated Duration:** 4-5 hours

**How to Implement:** See [PHASE_2_IMPLEMENTATION_GUIDE.md](docs/PHASE_2_IMPLEMENTATION_GUIDE.md)

**Quick Start:**
```bash
# Follow the step-by-step guide to add:
# 1. League entity, repository, service, controller
# 2. Team entity, repository, service, controller
# 3. Relationships between leagues, teams, and players
# 4. Integration tests
```

**New Endpoints (Phase 2):**
```
POST   /leagues                     # Create league
GET    /leagues                     # Get all leagues
GET    /leagues/{id}                # Get league by ID
PUT    /leagues/{id}                # Update league
DELETE /leagues/{id}                # Delete league

POST   /teams/league/{leagueId}     # Create team in league
GET    /teams                       # Get all teams
GET    /teams/{id}                  # Get team by ID
GET    /teams/league/{leagueId}     # Get teams in league
PUT    /teams/{id}                  # Update team
DELETE /teams/{id}                  # Delete team

POST   /teams/{teamId}/players/{playerId}     # Add player to team
DELETE /teams/{teamId}/players/{playerId}     # Remove player from team
```

---

### 📅 Phase 3: Match Scoring & Fantasy Points

**Goal:** Ingest match statistics and calculate fantasy points

**Features:**
- Match entity (score, date, statistics)
- Match statistics (goals, assists, clean sheets, etc.)
- Fantasy points calculation engine
- Leaderboards and team rankings

**Endpoints:**
```
POST   /matches                     # Create match
POST   /match-stats                 # Ingest match statistics
GET    /leagues/{leagueId}/leaderboard  # Get standings
```

---

### 🎨 Phase 4: Frontend & Automation

**Goal:** Build UI and comprehensive automation tests

**Features:**
- Simple web dashboard (React or HTML/CSS)
- Playwright test suite for API and UI
- Leaderboard visualization
- Team management UI

---

## 🧠 Learning Objectives

This project is designed to practice:

✅ **Backend Architecture**
- Layered architecture (Controller → Service → Repository → Database)
- Dependency injection and inversion of control
- Feature-based modular design
- Clean code principles

✅ **Spring Boot Mastery**
- REST API development
- JPA/Hibernate ORM
- Service layer patterns
- Repository pattern

✅ **Database Design**
- Entity relationships (One-to-Many, Many-to-Many)
- JPA annotations and mappings
- Database schema design
- Migration strategies (H2 → PostgreSQL)

✅ **Testing**
- Integration testing with Spring Boot Test
- MockMvc for API testing
- Test-driven development practices

✅ **Professional Practices**
- Git version control
- Code organization
- Documentation
- CI/CD readiness

---

## 📋 Development Commands

### Build & Run
```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Build JAR
mvn clean package
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=PlayerControllerTest

# Run with coverage (optional)
mvn test jacoco:report
```

### Database
```bash
# Access H2 Console
# http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:fantasydb
# User: sa, Password: (blank)
```

### IDE Setup (IntelliJ)
```bash
# Enable Lombok annotation processing
# Settings → Build, Execution, Deployment → Compiler → Annotation Processors
# Check: "Enable annotation processing"

# Invalidate caches and restart
# File → Invalidate Caches → Invalidate and Restart
```

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Kill process on port 8080 (macOS/Linux)
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Compilation Error: "Lombok not recognized"
Enable annotation processing in IntelliJ:
- Settings → Build, Execution, Deployment → Compiler → Annotation Processors
- ✅ Check "Enable annotation processing"

### "Foreign key constraint failed" when creating team
Ensure league with that ID exists before creating team:
```bash
# Create league first
curl -X POST http://localhost:8080/leagues ...

# Then create team with that league ID
curl -X POST http://localhost:8080/teams/league/1 ...
```

### LazyInitializationException when accessing nested objects
Use `@Transactional` on service methods or change fetch type to `EAGER`

For more troubleshooting, see [QUICK_REFERENCE.md](docs/QUICK_REFERENCE.md)

---

## 📖 Additional Documentation

- **[PHASE_2_IMPLEMENTATION_GUIDE.md](docs/PHASE_2_IMPLEMENTATION_GUIDE.md)** – Complete step-by-step guide to implement Phase 2
- **[QUICK_REFERENCE.md](docs/QUICK_REFERENCE.md)** – Handy reference for commands, endpoints, annotations
- **[PHASE_2_CHECKLIST.md](docs/PHASE_2_CHECKLIST.md)** – Detailed checklist with 50+ trackable tasks

---

## 🌐 API Testing Tools

### Using Postman
1. Download [Postman](https://www.postman.com/downloads/)
2. Create new request → GET → `http://localhost:8080/players`
3. Click Send

### Using curl (Command Line)
```bash
# Get all players
curl http://localhost:8080/players | jq

# Create new player
curl -X POST http://localhost:8080/players \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Player","position":"MIDFIELDER","club":"Test Club","league":"Test League"}'
```

### Using Rest Client Extension (VS Code)
Install "REST Client" extension and use `.http` or `.rest` files:
```http
GET http://localhost:8080/players HTTP/1.1
```

---

## 🤝 Contributing

This is a **personal learning project**, but contributions and suggestions are welcome!

### How to Extend

1. **Add new features** by following the existing pattern:
   - Create entity in new module folder
   - Add repository, service, controller
   - Write integration tests
   - Update this README

2. **Follow the architecture:**
   - Keep domain logic in Service layer
   - Keep HTTP concerns in Controller
   - Avoid mixing layers

3. **Test your changes:**
   ```bash
   mvn clean test
   ```

4. **Document your changes** in README and code comments

---

## 📄 License

This project is licensed under the **MIT License** – feel free to use, modify, and distribute.

See [LICENSE](LICENSE) file for details.

---

## 🎓 Educational Value

This project demonstrates:

| Concept | Where It's Used |
|---------|-----------------|
| **Dependency Injection** | Service injection in controllers |
| **Repository Pattern** | `PlayerRepository`, `LeagueRepository` |
| **Entity Relationships** | `@OneToMany`, `@ManyToMany`, `@ManyToOne` |
| **REST API Design** | CRUD endpoints following REST principles |
| **Layered Architecture** | Controller → Service → Repository |
| **Exception Handling** | `IllegalArgumentException`, HTTP status codes |
| **Testing** | Integration tests with `MockMvc` |
| **Database Design** | Schema, relationships, constraints |

---

## 📊 Project Statistics

| Metric | Value |
|--------|-------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.2 |
| **Lines of Code (Phase 1)** | ~500 |
| **Test Coverage** | 8+ integration tests |
| **Database Tables** | 1 (player) |
| **REST Endpoints** | 3 (GET all, GET by ID, POST) |
| **Build Time** | ~30 seconds |
| **Startup Time** | ~5 seconds |

---

## 🔗 Resources & Links

### Official Documentation
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Lombok Documentation](https://projectlombok.org/)
- [JPA/Hibernate Guide](https://www.baeldung.com/jpa-hibernate-difference)

### Tutorials & Learning
- [REST API Best Practices](https://restfulapi.net/)
- [Spring Boot Tutorial](https://www.baeldung.com/spring-boot)
- [Database Relationships](https://www.baeldung.com/jpa-relationship-types)
- [Maven Guide](https://maven.apache.org/guides/getting-started/)

### Tools
- [Spring Initializr](https://start.spring.io/) – Bootstrap Spring projects
- [Postman](https://www.postman.com/) – API testing
- [IntelliJ IDEA Community](https://www.jetbrains.com/idea/) – IDE

---

## 💡 Next Steps

**Recommended Path:**

1. ✅ **Start here:** Clone and run Phase 1 locally
2. 📖 **Read:** [PHASE_2_IMPLEMENTATION_GUIDE.md](docs/PHASE_2_IMPLEMENTATION_GUIDE.md)
3. 💻 **Build:** Implement Phase 2 using the step-by-step guide
4. 🧪 **Test:** Run full test suite (`mvn test`)
5. 📤 **Push:** Commit to GitHub
6. 📅 **Continue:** Start Phase 3 (Match Scoring)

---

## 📧 Support & Questions

- 📖 Check documentation files first
- 🐛 File issues on GitHub
- 💬 Review code comments for implementation details
- 🔍 Check existing issues before creating new ones

---

## 🎯 Quick Command Reference

```bash
# Clone and setup
git clone <repo> && cd fantasy-football && mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Test specific endpoint
curl http://localhost:8080/players | jq

# Build for production
mvn clean package

# View H2 console
# Open: http://localhost:8080/h2-console
```

---

## 🏆 Project Milestones

- ✅ **Milestone 1 (Phase 1):** Core REST API with player data
- 🔄 **Milestone 2 (Phase 2):** League and team management (In Progress)
- 📅 **Milestone 3 (Phase 3):** Fantasy points calculation
- 🎨 **Milestone 4 (Phase 4):** Frontend and automation tests

---

<div align="center">

### Built with ❤️ as a learning project

**Stars** | **Forks** | **Contributors** | **License**  
:---:|:---:|:---:|:---:  
![Stars](https://img.shields.io/github/stars/yourusername/fantasy-football?style=social) | ![Forks](https://img.shields.io/github/forks/yourusername/fantasy-football?style=social) | ![Contributors](https://img.shields.io/github/contributors/yourusername/fantasy-football) | ![License](https://img.shields.io/badge/license-MIT-blue)

</div>

---

**Last Updated:** January 2025  
**Current Phase:** Phase 1 Complete ✅ | Phase 2 Ready to Build 🚀
