CREATE DATABASE campus_portal;
USE campus_portal;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    role ENUM('student','admin')
);

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    event_date DATE,
    venue VARCHAR(100)
);

CREATE TABLE registrations (
    reg_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    event_id INT,
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (event_id) REFERENCES events(event_id)
);

INSERT INTO events (title, description, event_date, venue) VALUES
('Tech Symposium', 'Annual technical event', '2026-03-10', 'Auditorium'),
('Cultural Fest', 'Dance and music performances', '2026-03-15', 'Main Ground');
