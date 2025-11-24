# HireMeNow Microservices Platform

HireMeNow is a simple job application platform built using a microservices architecture with Spring Boot. It consists of three independent services:

- **Auth Service**: Handles user registration and retrieval (students, employers, admins).
- **Job Service**: Manages job postings.
- **Application Service**: Handles job applications, validating users and jobs via inter-service REST calls.

The system uses MariaDB as a shared database and Docker for containerization. Inter-service communication is done via REST APIs using RestTemplate.

## Prerequisites

- Docker and Docker Compose installed (version 3.8+).
- Postman (or any API client) for testing.
- Java 17 and Maven (optional, if building locally without Docker).

## Project Structure

```
HireMeNow_V2/
├── auth_service/          # Auth service source code
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── job_service/           # Job service source code
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── application_service/   # Application service source code
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── docker-compose.yml     # Docker Compose configuration
├── postman_collection.json  # Postman collection for testing
└── README.md              # This file
```

## How to Run the System

1. **Clone the Repository** :
   ```
   git clone https://github.com/lexofficial29/SDT_PJ/tree/milestone4
   cd HireMeNow_V2
   ```

2. **Build and Start the Services**:

   Use Docker Compose to build and run all services and the database in one command:
   ```
   docker-compose up --build
   ```
   - This will:
     - Build Docker images for each service.
     - Start MariaDB on port 3307 (mapped from container port 3306).
     - Start Auth Service on port 8081.
     - Start Job Service on port 8082.
     - Start Application Service on port 8083.
   - The services depend on MariaDB, so they will wait until the DB is ready.
   - Use `docker-compose up -d --build` to run in detached mode (background).
   - First run may take a few minutes due to Maven builds and DB initialization.

3. **Verify Services Are Running**:
   - Check logs: `docker-compose logs -f`
   - Test root endpoints:
     - Auth: `curl http://localhost:8081/` (should return "Auth service is running!")
     - Job: `curl http://localhost:8082/` (should return "Job service is running!")
     - Application: `curl http://localhost:8083/` (should return "Application service is running!")

4. **Stop the System**:
   ```
   docker-compose down
   ```

## Database Access (Optional)

- Connect to MariaDB using this command in the termnal:
    ```
    docker exec -it hiremenow-mariadb mysql -u hmuser -phmpass hiremenow
    ```

- Tables: `users` (auth), `jobs` (job), `applications` (application).


## Testing with Postman

1. Import the provided collection: `postman_collection.json` into Postman.
2. The collection includes folders for each service with requests for all core features and edge cases:
   - **Auth Service**: Register users (student/employer/admin), list users, duplicate email error.
   - **Job Service**: Create job, list jobs, get by ID, missing title error.
   - **Application Service**: Apply to job, list applications (all/by student/by job), non-existent student/job errors.
3. Run the requests in sequence:
   - Start with registering users and creating jobs.
   - Then apply and query applications.
4. All URLs use `localhost` ports as exposed by Docker (8081, 8082, 8083).

### Example Workflow in Postman:
- Register a student (POST /api/auth/register).
- Create a job (POST /api/jobs).
- Apply (POST /api/applications with studentId and jobId).
- Fetch applications (GET /api/applications).

## Troubleshooting

- **Build Errors**: Ensure Maven can resolve dependencies; check internet connection.
- **DB Connection Issues**: Verify MariaDB is up first (`depends_on` should handle this).
- **Port Conflicts**: Change host ports in `docker-compose.yml` if 8081-8083 or 3307 are in use.
- **Logs**: Use `docker logs hiremenow-auth` (or similar) for service-specific logs.
- **Rebuild**: If code changes, run `docker-compose up --build` again.
