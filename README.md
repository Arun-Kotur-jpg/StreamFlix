# StreamFlix (Hotstar Clone)

StreamFlix is a full-stack streaming platform clone built with Java, Spring Boot, and MySQL. It features a modern, responsive UI inspired by platforms like Hotstar and Netflix.

## 🛠️ Technology Stack & Modules

### 1. Backend (Core Logic & APIs)
- **Java 21**: The core programming language.
- **Spring Boot 3.3.0**: The main framework handling server execution, configuration, and dependency injection.
- **Spring MVC**: Handles web requests, routing, and returning views to the user.
- **Spring Security**: Secures the application. It uses a **Dual-Chain Security Architecture**:
  - *Session-based Authentication* for the web application (Thymeleaf views).
  - *Stateless JWT (JSON Web Tokens)* for REST API endpoints.

### 2. Database & Storage (Data Layer)
- **MySQL 8.0**: The relational database used to store all persistent data.
- **Spring Data JPA & Hibernate**: The Object-Relational Mapping (ORM) framework that translates Java Objects into MySQL database tables automatically without writing raw SQL queries.

### 3. Frontend (User Interface)
- **Thymeleaf**: A server-side Java template engine used to dynamically inject data (like movie lists) into HTML pages.
- **Bootstrap 5**: CSS framework used for responsive layouts, grids, and UI components.
- **Vanilla JavaScript & CSS**: Custom animations, glassmorphism effects, and the video player logic.

---

## 🗄️ Database Architecture (MySQL)

The application stores data in a local MySQL database named `streamflix_db`. It consists of 4 main tables:

1. **`users`**: Stores registered users.
   - Includes fields like `name`, `email`, `password` (hashed using BCrypt), and `role` (USER or ADMIN).
2. **`movies`**: Stores the movie catalog.
   - Contains metadata like `title`, `description`, `genre`, `rating`, and media links (`thumbnail_url`, `video_url`).
3. **`watchlists`**: A relational mapping table that connects Users to Movies they want to watch later.
4. **`watch_history`**: Tracks which movies a user has watched and the timestamp of when they watched it.

---

## ⚙️ How the Project Works (Data Flow)

When a user interacts with the application, data flows through several layers:

1. **The View (Frontend)**: The user clicks "Play" or "Add to Watchlist" on the website.
2. **The Controller (`@Controller` / `@RestController`)**: Spring Boot intercepts this request. The Controller checks what the user is asking for. If it requires data, it calls the Service layer.
3. **The Service (`@Service`)**: This is where the "Business Logic" lives. It validates the user, checks if the movie exists, and applies business rules.
4. **The Repository (`@Repository`)**: The Service asks the Repository to save or fetch data. The Repository extends Spring Data JPA, which automatically generates the complex MySQL queries.
5. **The Database (MySQL)**: The actual data is inserted, updated, or retrieved from the hard drive, and the result flows all the way back up to the user's screen!

---

## 🚀 How to Run the Project

1. **Ensure Prerequisites**: Java 21 and MySQL 8 are installed.
2. **Database Setup**: Create a MySQL database named `streamflix_db`.
3. **Run Application**: Execute `.\mvnw.cmd spring-boot:run` in the terminal.
4. **Initialize Data**: Pipe the `schema.sql` file into MySQL to load the 20 sample movies.
5. **Access**: Open your browser and navigate to `http://localhost:8080`.
