# 🚀 TechPrep Academy

<div align="center">

**A comprehensive placement preparation platform for engineering students**

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-green?style=flat-square&logo=springsecurity)
![Gemini AI](https://img.shields.io/badge/AI-Google%20Gemini-purple?style=flat-square&logo=google)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

</div>

---

## 📖 About

**TechPrep Academy** is a full-stack web application that helps engineering students crack placement interviews. It provides an organized question bank across **DSA, Aptitude, Reasoning, Verbal, Java, C++, Python, JavaScript, Database, and CS Fundamentals** — with practice sessions, timed mock tests, AI-powered explanations, a performance analytics engine, and a competitive leaderboard.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **Secure Authentication** | JWT-based login & registration with BCrypt password hashing |
| 📅 **Daily Challenge** | Fresh Question of the Day with streak multiplier, live success rate, and countdown timer |
| 📚 **Organized Question Bank** | 3-level hierarchy: Category → SubCategory → Topic (15+ questions per topic) |
| 🎯 **Practice Sessions** | Topic-wise practice with difficulty selection, shuffle, streak tracking |
| 📝 **Mock Tests** | Timed tests auto-built from the question bank with instant evaluation |
| 📊 **Performance Engine** | Classifies topics as Strong / Weak / Improving / Declining with recommendations |
| 📈 **Progress Dashboard** | Overall accuracy, streak, daily goal, and weak topic highlights |
| 🏆 **Leaderboard** | Overall / Weekly / Monthly rankings across all students |
| 🔖 **Bookmarks** | Save questions for later revision |
| 📜 **Practice History** | Full history of all past practice sessions with review |
| 🤖 **AI Integration** | Gemini AI explains any question + generates new MCQs for admins |
| 👨‍💼 **Admin Panel** | Manage categories, subcategories, topics, questions, and mock tests |
| 🌙 **Dark Mode** | Theme toggle across all pages |

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Spring Boot | 3.1.5 | Application framework |
| Spring Security | 6.1.5 | Authentication & Authorization |
| JWT (jjwt) | 0.11.5 | Stateless token auth |
| Spring Data JPA | — | ORM / database abstraction |
| Hibernate | — | JPA implementation |
| Lombok | — | Boilerplate reduction |
| Jackson | — | JSON serialization |
| Maven | — | Build tool |

### Frontend
| Technology | Purpose |
|---|---|
| HTML5 / CSS3 | Page structure & styling |
| Vanilla JavaScript (ES6+) | Dynamic UI, API calls |
| Font Awesome 6 | Icons |
| Fetch API | REST calls to backend |

### Database & AI
| Technology | Purpose |
|---|---|
| MySQL | Primary relational database |
| Google Gemini API | AI question explanation & generation |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│         Browser (HTML + JS)         │
│  Student Portal │ Admin Panel       │
└────────────┬────────────────────────┘
             │  HTTP/JSON (JWT in header)
             ▼
┌─────────────────────────────────────┐
│         Spring Boot REST API        │
│                                     │
│  ┌─────────────┐  ┌──────────────┐  │
│  │ Controllers │  │   Security   │  │
│  │  (REST API) │  │  (JWT Filter)│  │
│  └──────┬──────┘  └──────────────┘  │
│         ▼                           │
│  ┌─────────────────────────────┐    │
│  │       Service Layer         │    │
│  │ (Business Logic + Analytics)│    │
│  └──────────────┬──────────────┘    │
│                 ▼                   │
│  ┌─────────────────────────────┐    │
│  │   Spring Data JPA / Hibernate│   │
│  └──────────────┬──────────────┘    │
└─────────────────┼───────────────────┘
                  ▼
         ┌─────────────┐     ┌──────────────────┐
         │    MySQL    │     │  Google Gemini AI │
         └─────────────┘     └──────────────────┘
```

---

## 📂 Project Structure

```
TechPrep/
│
├── src/main/java/com/techprep/
│   ├── config/              # Security config, data seeders
│   │   ├── SecurityConfig.java
│   │   ├── DataInitializer.java
│   │   ├── QuestionBankSeeder.java
│   │   ├── QuestionTopUpSeeder.java
│   │   ├── AptitudeTopUp.java
│   │   ├── ReasoningTopUp.java
│   │   ├── TechnicalTopUpPart1.java
│   │   ├── TechnicalTopUpPart2.java
│   │   └── VerbalAndCommTopUp.java
│   │
│   ├── controller/          # REST API controllers
│   │   ├── AuthController.java
│   │   ├── CategoryController.java
│   │   ├── SubCategoryController.java
│   │   ├── TopicController.java
│   │   ├── QuestionController.java
│   │   ├── PracticeController.java
│   │   ├── TestController.java
│   │   ├── TestAttemptController.java
│   │   ├── ProgressController.java
│   │   ├── LeaderboardController.java
│   │   ├── PerformanceEngineController.java
│   │   ├── SavedQuestionController.java
│   │   ├── AIController.java
│   │   └── UserController.java
│   │
│   ├── service/             # Business logic (interfaces + implementations)
│   │   ├── impl/
│   │   │   ├── AuthServiceImpl.java
│   │   │   ├── PracticeServiceImpl.java
│   │   │   ├── TestServiceImpl.java
│   │   │   ├── PerformanceEngineServiceImpl.java
│   │   │   ├── LeaderboardServiceImpl.java
│   │   │   ├── ProgressServiceImpl.java
│   │   │   ├── GeminiAIServiceImpl.java
│   │   │   ├── SavedQuestionServiceImpl.java
│   │   │   └── ...
│   │   └── *.java (service interfaces)
│   │
│   ├── entity/              # JPA entities (DB tables)
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── SubCategory.java
│   │   ├── Topic.java
│   │   ├── Question.java
│   │   ├── PracticeSession.java
│   │   ├── PracticeAttempt.java
│   │   ├── TestEntity.java
│   │   ├── TestQuestion.java
│   │   ├── TestAttempt.java
│   │   ├── TestQuestionAnswer.java
│   │   ├── SavedQuestion.java
│   │   └── ...
│   │
│   ├── dto/                 # Request & Response DTOs
│   ├── repository/          # Spring Data JPA repositories
│   ├── security/            # JWT utilities & filter
│   │   ├── JwtUtil.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── CustomUserDetailsService.java
│   └── exception/           # Global exception handling
│       ├── GlobalExceptionHandler.java
│       └── ResourceNotFoundException.java
│
├── src/main/resources/
│   ├── static/
│   │   ├── index.html
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── student/         # Student pages
│   │   │   ├── dashboard.html
│   │   │   ├── categories.html
│   │   │   ├── category.html
│   │   │   ├── subcategory.html
│   │   │   ├── practice.html
│   │   │   ├── result.html
│   │   │   ├── mock-tests.html
│   │   │   ├── exam-interface.html
│   │   │   ├── test-result.html
│   │   │   ├── leaderboard.html
│   │   │   ├── progress.html
│   │   │   ├── history.html
│   │   │   └── saved-questions.html
│   │   ├── admin/           # Admin pages
│   │   │   ├── dashboard.html
│   │   │   ├── questions.html
│   │   │   ├── categories.html
│   │   │   ├── tests.html
│   │   │   └── ai-generator.html
│   │   ├── js/              # JavaScript files
│   │   └── css/             # Stylesheets
│   └── application.properties  # (gitignored — not committed)
│
├── pom.xml
└── README.md
```

---

## 🗄️ Database Design

```
User ──────────────────────────────────────────────────────┐
 ├── PracticeSession (OneToMany)                            │
 │    └── PracticeAttempt (OneToMany) ──► Question          │
 ├── TestAttempt (OneToMany)                               │
 │    └── TestQuestionAnswer (OneToMany)                    │
 └── SavedQuestion (OneToMany) ──► Question                │
                                                            │
Category                                                   │
 └── SubCategory (OneToMany)                               │
      └── Topic (OneToMany)                                │
           ├── Question (OneToMany) ◄──────────────────────┘
           └── (referenced by TestEntity)

TestEntity
 └── TestQuestion (OneToMany) ──► Question
```

---

## 🔌 REST API Overview

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Categories / Topics
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/categories` | List all categories |
| GET | `/api/categories/{id}/subcategories` | Subcategories under a category |
| GET | `/api/subcategories/{id}/topics` | Topics under a subcategory |
| GET | `/api/topics/{id}/questions/count` | Active question count for a topic |

### Practice
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/practice/start` | Start a new practice session |
| GET | `/api/practice/sessions/{id}/questions/{order}` | Get question by order |
| POST | `/api/practice/sessions/{id}/attempts/{attemptId}/submit` | Submit an answer |
| POST | `/api/practice/sessions/{id}/complete` | Complete session early |
| GET | `/api/practice/sessions/{id}/result` | Get session result |
| GET | `/api/practice/history` | Practice history |
| GET | `/api/practice/stats` | Student stats (streak, accuracy, etc.) |

### Mock Tests
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tests` | List active mock tests |
| POST | `/api/tests/{id}/start` | Start a mock test |
| POST | `/api/test-attempts/{id}/submit-answer` | Submit a test answer |
| POST | `/api/test-attempts/{id}/finish` | Finish the test |
| GET | `/api/test-attempts/{id}/result` | Get test result |

### Performance & Progress
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/progress` | Dashboard stats for student |
| GET | `/api/performance/analytics` | Full analytics (topic classification, trends) |
| GET | `/api/performance/topic/{id}` | Analytics for a specific topic |

### Leaderboard & Bookmarks
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/leaderboard?timeFrame=OVERALL` | Leaderboard (OVERALL / WEEKLY / MONTHLY) |
| POST | `/api/student/bookmarks/{questionId}` | Bookmark a question |
| DELETE | `/api/student/bookmarks/{questionId}` | Remove bookmark |
| GET | `/api/student/bookmarks` | List all saved questions |

### Daily Challenge
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/daily-challenge` | Any auth user | Fetch Question of the Day & participant statistics |
| POST | `/api/daily-challenge/submit` | Student only | Submit answer, earn bonus points, and extend streak |

### AI
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/ai/explain` | Any auth user | AI explanation for a question |
| POST | `/api/ai/generate` | Admin only | Generate MCQs via Gemini |

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **MySQL 8.0+**
- **Google Gemini API Key** ([Get it here](https://aistudio.google.com/))

### 1. Clone the Repository

```bash
git clone https://github.com/Hemlata-31/TechPrep.git
cd TechPrep
```

### 2. Set Up MySQL Database

```sql
CREATE DATABASE techprep_db;
```

### 3. Configure `application.properties`

Create `src/main/resources/application.properties` (this file is gitignored):

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/techprep_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT
jwt.secret=YOUR_LONG_RANDOM_SECRET_KEY_MIN_32_CHARS
jwt.expiration=86400000

# Google Gemini AI
gemini.api.key=YOUR_GEMINI_API_KEY
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent
```

> ⚠️ **Never commit** `application.properties` to GitHub. It is already added to `.gitignore`.

### 4. Run the Backend

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**
```bash
./mvnw spring-boot:run
```

The server starts at: **`http://localhost:8080`**

On first startup, the application automatically:
- Creates all database tables (via Hibernate DDL)
- Seeds initial categories, subcategories, and topics
- Seeds the question bank (15+ questions per topic across all categories)

### 5. Access the Application

Open your browser and go to:

```
http://localhost:8080
```

| Role | Default Credentials |
|---|---|
| Student | Register a new account via the UI |
| Admin | Set up via DB or seeder (role = ADMIN) |

---

## 🔐 Security

- **JWT Authentication** — Stateless, token-based. Token expiry configurable via `jwt.expiration`.
- **BCrypt** — All passwords are hashed with salt before storage.
- **Role-based Access** — `STUDENT`, `ADMIN`, `INSTRUCTOR` roles enforced at both controller (`@PreAuthorize`) and route level.
- **CORS** — Configured in `SecurityConfig` (can be restricted per environment).
- **Expired Token Handling** — `ExpiredJwtException` caught in filter; returns `401 Unauthorized` cleanly.

---

## 📊 Performance Engine

The `PerformanceEngineServiceImpl` analyzes every student's practice + test history to compute:

- **Overall accuracy** across both practice and mock tests
- **Difficulty breakdown** — separate accuracy for Easy / Medium / Hard
- **Topic classification** using rule-based logic:
  - 🟢 **Strong** — accuracy ≥ 70%
  - 🔴 **Weak** — accuracy < 40%
  - 📈 **Improving** — recent upward trend
  - 📉 **Declining** — recent downward trend
  - 📚 **Needs Revision** — low attempt count
- **Performance timeline** — chronological score progression
- **Actionable recommendations** — generated from classification rules

---

## 🌱 Question Bank

The question bank is seeded automatically on startup via `QuestionTopUpSeeder`. Every individual topic has a **minimum of 15 active questions**, covering:

| Domain | Topics Covered |
|---|---|
| **DSA** | Arrays, Strings, Linked List, Trees, Graphs, Sorting, Recursion, DP, Stacks & Queues |
| **Aptitude** | Number System, Percentage, Profit & Loss, Time & Work, Averages, Ratios, Geometry |
| **Reasoning** | Logical, Verbal, Blood Relations, Directions, Seating Arrangement, Coding-Decoding |
| **Verbal** | Grammar, Reading Comprehension, Vocabulary, Sentence Correction |
| **Communication** | Professional writing, Presentation, Group Discussion, Interview skills |
| **Java** | OOP, Collections, Exception Handling, Multithreading, JVM, Generics |
| **C++ / Python / JavaScript** | Language-specific fundamentals and concepts |
| **Database** | SQL queries, Normalization, Transactions, Indexes, Joins |
| **CS Fundamentals** | OS, CN, DBMS, OOPS concepts |
| **Java Backend** | Spring Boot, REST APIs, JPA, MVC pattern |

---

## 🔮 Future Enhancements

- [ ] 📧 Email OTP verification
- [ ] 🔑 Google / GitHub OAuth2 login
- [x] 🔥 Daily streaks and goals *(implemented)*
- [ ] 📱 Mobile-first responsive redesign
- [ ] ☁️ Cloud deployment (AWS / Railway / Render)
- [ ] ⚙️ CI/CD pipeline
- [ ] 📧 Daily performance digest emails
- [ ] 🎯 Adaptive difficulty — auto-adjust based on student performance

---

## 👩‍💻 Author

**Hemlata Kumawat**

B.Tech Information Technology | Java Backend Developer

[![GitHub](https://img.shields.io/badge/GitHub-Hemlata--31-black?style=flat-square&logo=github)](https://github.com/Hemlata-31/TechPrep)

---

## 📄 License

This project is licensed under the **MIT License**.
