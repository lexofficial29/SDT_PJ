# HireMeNow

## Team Members
- Matei Eduard Gabriel
- Oblu Alexandra Mihaela

## Description
HireMeNow is a web-based freelance marketplace tailored for students seeking small freelance gigs, internships, or part-time work opportunities. The platform bridges the gap between students looking to gain experience and employers or startups searching for affordable and motivated talent.

Unlike general freelance platforms, HireMeNow focuses on simplicity, accessibility, and learning value, offering a user-friendly environment where students can build experience, showcase CVs.

### The system will allow:

1. Students

- Create accounts, manage personal profiles.
- Browse job listings with advanced search and filtering.
- Apply for gigs or internships directly through the platform.
- Track application statuses.

2. Employers

- Register and post new job listings or internship offers.
- Review applications, filter candidates, and manage ongoing postings.
- Close completed job listings or mark them as filled.

3. Administrator

- Manage users (students and employers).
- Moderate job postings and reported content.
- Monitor system health and ensure quality and compliance of listings.

### Project goals
- Enable students to easily find freelance jobs and internships relevant to their studies.
- Provide employers with an accessible recruitment tool for entry-level positions.
- Encourage professional growth and experience building for students.
- Build an extendable and modular system suitable for future scalability.

## Design patterns

### Factory Method

Category: Creational  

Usage in HireMeNow: User creation (Student, Employer, Admin)  

Justification:  

Different user types have shared properties but distinct behavior and permissions. The Factory Method centralizes and abstracts object creation, allowing the system to instantiate the correct user subclass depending on the role selected during registration.  

Why it fits:  

It prevents the use of repetitive if/else logic and makes adding new user types (e.g., “Recruiter” or “Partner Organization”) simple and maintainable.

### Singleton

Category: Creational  

Usage in HireMeNow: Database connection and configuration management  

Justification:  

The platform requires shared access to global resources like the database or configuration settings. Singleton ensures there’s only one instance of these components throughout the application.  

Why it fits:  

It provides controlled access, prevents multiple simultaneous connections, and maintains consistency across the system.

### Strategy

Category: Behavioral  

Usage in HireMeNow: Job search, filtering, and sorting algorithms  

Justification:  

Users can search or filter jobs by various criteria—date posted, category, pay rate, or location. Strategy allows defining a family of search/filter algorithms that can be selected dynamically at runtime.  

Why it fits:  

Adding new filters or sorting criteria doesn’t require changing the core code—just define a new strategy class.  

### Facade

Category: Structural  

Usage in HireMeNow: Simplified access to complex subsystems (e.g., when posting or applying for jobs)  

Justification:  

Behind the scenes, posting a job involves multiple subsystems (validation, database operations, etc.). The Facade pattern provides a unified interface that coordinates these operations.  

Why it fits:  

It keeps the system modular and easy to use from the outside while hiding implementation complexity.
