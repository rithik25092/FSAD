package com.campus.eventmanagement.config;

import com.campus.eventmanagement.model.Event;
import com.campus.eventmanagement.model.EventType;
import com.campus.eventmanagement.model.Role;
import com.campus.eventmanagement.model.User;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Admin
        User admin;
        if (!userRepository.existsByEmail("admin@campus.edu")) {
            admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@campus.edu");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setDepartment("IT Administration");
            admin = userRepository.save(admin);
        } else {
            admin = userRepository.findByEmail("admin@campus.edu").get();
        }

        // Create Technical Events
        if (eventRepository.findAll().isEmpty()) {
            Event e1 = createEvent("AI & Machine Learning Bootcamp", "A comprehensive 2-day bootcamp covering neural networks, deep learning frameworks (TensorFlow, PyTorch), and real-world AI applications. Open to all skill levels. Bring your laptop!", EventType.TECHNICAL, "Computer Science", LocalDateTime.now().plusDays(5).plusHours(2), "Tech Hall A", 100, admin);
            Event e2 = createEvent("Hackathon 2026: Code for Future", "Join developers, designers, and thinkers in a 48-hour hackathon. Build innovative solutions for climate change, education, and healthcare. Food and energy drinks provided!", EventType.TECHNICAL, "Engineering", LocalDateTime.now().plusDays(10), "Main Innovation Center", 200, admin);
            Event e3 = createEvent("Cybersecurity Strategies in the Modern Web", "Expert guest speakers will discuss current web vulnerabilities, penetration testing techniques, and how to secure cloud infrastructure.", EventType.SEMINAR, "Computer Science", LocalDateTime.now().plusDays(15).plusHours(4), "Auditorium 1", 150, admin);
            Event e4 = createEvent("Robotics Workshop: Build Your First Drone", "Hands-on workshop where participants will assemble and program their own autonomous drones. Materials provided by the university.", EventType.WORKSHOP, "Electrical Engineering", LocalDateTime.now().plusDays(7).plusHours(3), "Robotics Lab", 30, admin);
            Event e5 = createEvent("Full Stack Web Development with React & Node", "Learn the fundamentals of the MERN stack in this intensive coding session. We will build a complete web application from scratch.", EventType.WORKSHOP, "Software Engineering", LocalDateTime.now().plusDays(12).plusHours(5), "Lab 201", 50, admin);
            Event e6 = createEvent("Cloud Architecture on AWS", "An overview of designing scalable systems using Amazon Web Services. Covers EC2, S3, RDS, and serverless functions via Lambda.", EventType.SEMINAR, "Computer Science", LocalDateTime.now().plusDays(20), "Tech Hall B", 120, admin);
            Event e7 = createEvent("Quantum Computing Basics", "Introduction to qubits, entanglement, and quantum gates using Qiskit. No prior quantum physics knowledge required.", EventType.SEMINAR, "Physics & CS", LocalDateTime.now().plusDays(25), "Science Auditorium", 80, admin);

            // Create Non-Technical Events
            Event e8 = createEvent("Annual Campus Cultural Festival", "The biggest cultural night of the year! Enjoy music, dance performances, and art showcases from various student clubs.", EventType.CULTURAL, "Student Council", LocalDateTime.now().plusDays(8).plusHours(6), "Campus Grounds", 500, admin);
            Event e9 = createEvent("Inter-Department Basketball Tournament", "Compete against rival departments in our annual 3v3 basketball tournament. Winning team gets the Chancellor's Trophy!", EventType.SPORTS, "Athletics", LocalDateTime.now().plusDays(14), "Sports Arena", 300, admin);
            Event e10 = createEvent("Leadership and Communication Workshop", "Develop essential soft skills that employers look for. Interactive sessions on public speaking, conflict resolution, and leadership styles.", EventType.WORKSHOP, "Business", LocalDateTime.now().plusDays(18).plusHours(1), "Business School Room 102", 60, admin);
            Event e11 = createEvent("Startup Pitch Competition", "Have a great business idea? Pitch it to local angel investors and university alumni. Cash prizes for the top 3 teams.", EventType.SEMINAR, "Entrepreneurship", LocalDateTime.now().plusDays(22).plusHours(4), "Innovation Center", 150, admin);
            Event e12 = createEvent("Mental Health & Wellbeing Seminar", "Learn practical strategies for managing stress, avoiding burnout, and maintaining a healthy work-life balance during exams.", EventType.SEMINAR, "Student Affairs", LocalDateTime.now().plusDays(6).plusHours(5), "Counseling Center", 100, admin);
            Event e13 = createEvent("Photography & Visual Arts Walk", "Join the Arts club for a guided campus walk during golden hour. Bring your camera or phone to learn composition techniques.", EventType.CULTURAL, "Arts Department", LocalDateTime.now().plusDays(9).plusHours(7), "Campus Main Gate", 40, admin);
            Event e14 = createEvent("Charity 5K Run", "Run for a cause! All registration fees will be donated to the local food bank. Open to staff, students, and local community.", EventType.SPORTS, "Athletics", LocalDateTime.now().plusDays(30), "Starting Line: Track Stadium", 1000, admin);

            eventRepository.saveAll(List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14));
        }
    }

    private Event createEvent(String title, String desc, EventType type, String dept, LocalDateTime date, String venue, int maxCap, User admin) {
        Event e = new Event();
        e.setTitle(title);
        e.setDescription(desc);
        e.setEventType(type);
        e.setDepartment(dept);
        e.setEventDate(date);
        e.setVenue(venue);
        e.setMaxCapacity(maxCap);
        e.setCurrentRegistrations(0);
        e.setCreatedBy(admin);
        return e;
    }
}
