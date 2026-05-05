# FULL-STACK-APPLICATION-DEVELOPMENT-10211CS224-VTU25092

# Campus Event Management System

A full-stack Spring Boot application for managing and registering for campus events. It features role-based access for Admins and Students, event registration, a feedback system, graphical dashboards, and automated email notifications.

## 🎯 Features

**Admin:**
- Dashboard with key statistics and utilization metrics
- Create, Read, Update, Delete (CRUD) campus events
- View all event registrations and attendee lists
- Analytics and rating visualizations
- Automatic emails on event cancellation

**Student:**
- Personal dashboard showing upcoming events and summary
- Browse and filter all campus events (by type, department, date)
- Register for events (with capacity handling)
- Cancel registration
- Submit feedback and ratings post-event
- Automated email confirmations for registration

## 🛠️ Tech Stack

- **Backend:** Java 17, Spring Boot 3 (Web, Data JPA, Security, Mail, Validation)
- **Database:** MySQL
- **Frontend:** Thymeleaf, Vanilla HTML/CSS, Bootstrap 5 CDN
- **Build Tool:** Maven
- **Lombok** used for boilerplate code reduction.

## 🚀 Setup Instructions

1. **Database Setup**
   - Install MySQL Database
   - Create a database called `campus_events_db`:
     ```sql
     CREATE DATABASE campus_events_db;
     ```

2. **Configuration**
   - Open `src/main/resources/application.properties`
   - Update your MySQL credentials:
     ```properties
     spring.datasource.username=root
     spring.datasource.password=your_mysql_password
     ```
   - Update your email SMTP credentials (required for automatic emails):
     ```properties
     spring.mail.username=your_email@gmail.com
     spring.mail.password=your_gmail_app_password
     ```
     *(Note: If using Gmail, you must generate an "App Password" in your Google Account security settings).*

3. **Run the Application**
   - Open a terminal in the project root directory
   - Execute the following command (requires Java 17+):
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Access the Application**
   - In your browser, navigate to: `http://localhost:8080`
   - The database is pre-seeded with some initial users and events.
   - **Default Admin Account:**
     - Email: `admin@campus.edu`
     - Password: `admin`
   - **Default Student Account:**
     - Email: `student@campus.edu`
     - Password: `student`

## 📁 Project Structure

Code is structured using Standard Layered Architecture (MVC format):
- `model`: JPA database entities (`User`, `Event`, `Registration`, `Feedback`)
- `repository`: Spring Data JPA interfaces
- `service`: Business logic layer (event handling, validations, email sending)
- `controller`: Spring MVC controllers managing web requests and routing
- `dto`: Data Transfer Objects for specific forms and stats
- `config`: Beans configuration (Security, DataSeeder, Base configurations)
