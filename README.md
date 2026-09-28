# 🚀 TechPrep

**TechPrep** is a placement preparation platform designed to help students prepare for **aptitude, technical interviews, DSA, coding problems, mock tests, and company-specific assessments** in one place.

---

## ✨ Features

* 🔐 **Secure Authentication** — JWT-based authentication with role-based access
* 📚 **Topic-wise Learning** — Learn through organized topics and notes
* 📝 **Practice Questions** — Aptitude, Reasoning, Verbal, Technical, and DSA
* 💻 **Online Coding** — Solve DSA coding problems with code execution
* 🧪 **Mock Tests** — Timed tests with automatic evaluation
* 📊 **Progress Tracking** — Monitor performance and learning progress
* 🏆 **Leaderboard** — Compare performance through rankings
* 🔖 **Bookmarks** — Save questions for later practice
* 📜 **Practice History** — Review previous practice and test attempts
* 🤖 **AI Question Generation** — Generate questions using Google Gemini
* 🏢 **Company Preparation** — Practice for company-specific assessments
* 🔍 **Search & Filters** — Easily find questions by topic and category

---

## 🛠️ Tech Stack

### Frontend

* React
* Vite
* Axios
* React Router
* CSS

### Backend

* Java 17
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Hibernate

### Database

* MySQL

### AI & Code Execution

* Google Gemini API
* Judge0 API

---

## 🏗️ Architecture

```text
             React Frontend
                    ↓
          Spring Boot REST API
                    ↓
        Spring Security + JWT
                    ↓
              Service Layer
                    ↓
             JPA / Hibernate
                    ↓
                MySQL
```

### AI Integration

```text
React Frontend
      ↓
Spring Boot Backend
      ↓
Google Gemini API
      ↓
AI Generated Questions
```

### Online Code Execution

```text
Student Code
      ↓
Spring Boot Backend
      ↓
Judge0 API
      ↓
Execution Result
      ↓
Frontend
```

---

## 📂 Project Structure

```text
TechPrep/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── ...
│   ├── package.json
│   └── vite.config.js
│
├── src/
│   └── main/
│       ├── java/
│       │   └── .../techprep/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── entity/
│       │       ├── repository/
│       │       ├── service/
│       │       └── security/
│       │
│       └── resources/
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

* Java 17+
* Maven
* Node.js & npm
* MySQL
* Google Gemini API Key
* Judge0 API credentials

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/TechPrep.git
cd TechPrep
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE techprep_db;
```

Configure your database credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/techprep_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

Configure your AI and code-execution API credentials according to your environment.

> ⚠️ Never commit passwords, JWT secrets, Gemini API keys, or Judge0 credentials to GitHub.

### 3. Run the Backend

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### 4. Run the Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

## 🔐 Security

TechPrep uses:

* JWT authentication
* Spring Security
* Role-based authorization
* Protected REST APIs
* Secure handling of API credentials

---

## 🎯 Project Goal

TechPrep aims to provide students with a **single platform for complete placement preparation**, combining learning resources, practice questions, coding challenges, mock tests, company-specific preparation, performance tracking, and AI-assisted learning.

---

## 🔮 Future Enhancements

* 📧 Email OTP authentication
* 🔑 Google/GitHub OAuth2 login
* 🔥 Daily goals and streaks
* 📱 Improved mobile responsiveness
* ☁️ Cloud deployment
* ⚙️ CI/CD pipeline
* 🤖 More personalized AI learning features

---

## 👩‍💻 Author

**Hemlata Kumawat**

B.Tech Information Technology
Java Backend Developer | Spring Boot | REST APIs

---

## 📄 License

This project is licensed under the **MIT License**.
