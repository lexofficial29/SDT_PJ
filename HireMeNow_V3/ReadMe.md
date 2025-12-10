# HireMeNow – Full Microservices Implementation (Milestone 5)

HireMeNow is a real microservices platform with **asynchronous communication via RabbitMQ** and a **fully automated CI/CD pipeline**.

## 1. Message Queue Integration & Architecture (RabbitMQ)

### Role in the architecture
When a student applies for a job:
- `application-service` → validates student & job → saves to DB → **immediately returns 202 Accepted**
- A message is published to RabbitMQ (`application.exchange` → routing key `application.submitted`)
- `job-service` has an independent `@RabbitListener` that consumes the message **asynchronously**

### Why we added RabbitMQ
| Benefit              | How it is achieved                                                                 |
|----------------------|-------------------------------------------------------------------------------------|
| Instant response     | Client gets **202 Accepted** in < 100 ms (see curl demo)              |
| Decoupling           | Application service does **not** wait for job-service                 |
| Scalability          | `job-service` instances can be scaled independently                  |
| Fault tolerance      | If `job-service` is down → messages stay safely in the queue         |
| Better performance   | Heavy work (email, analytics, notifications) runs in background      |

### How it works
```
Client
  ↓ POST /api/applications
application-service → validates + saves → returns 202 instantly → publishes event
                             ↓
                    RabbitMQ (application.exchange → application.submitted)
                             ↓
                  job-service (@RabbitListener) → processes asynchronously
```

## 2. Complete CI/CD Pipeline (GitHub Actions)

**File:** `.github/workflows/ci-cd.yml`

### What the pipeline does automatically on **every push/PR**
1. Starts MariaDB + RabbitMQ (with health checks)
2. Builds and starts all three services via Docker Compose
3. Sends a real application request
4. Waits and **verifies** that the async message appears in `job-service` logs
5. Fails loudly if anything is wrong

### How to observe it
1. Go to your GitHub repo → **Actions** tab
2. Click the latest workflow run
3. All steps are green → **undeniable proof** the entire system (including async RabbitMQ) works perfectly

→ Green checkmark = It's all working.

# How to Run Locally

## Prerequisites
- Docker & Docker Compose
- Postman (or any API client) for testing.
- Java 17 + Maven (only if building locally)

## Project Structure
```
HireMeNow_V3/
├── auth_service/
├── job_service/           ← contains RabbitMQ listener
├── application_service/   ← publishes events
├── docker-compose.yml
├── postman_collection.json 
├── .github/workflows/ci-cd.yml
└── README.md
```

## How to Run
1. **Clone the Repository** :
   ```
   git clone https://github.com/lexofficial29/SDT_PJ/tree/milestone5
   cd HireMeNow_V3
   ```

2. **Build and Start the Services**:

   Use Docker Compose to build and run all services and the database in one command:
   ```
   docker-compose up --build
   ```

3. **Add users and jobs using Postman**

    - Open Postman → Import → select `postman_collection.json`
    - Register a student (POST /api/auth/register).
    - Register an employer (POST /api/auth/register).
    - Create a job (POST /api/jobs).

4. **Test async flow**

    ```
    curl -X POST http://localhost:8083/api/applications \
    -H "Content-Type: application/json" \
    -d '{"studentId":1,"jobId":1}'
    ```
    Returns 202 Accepted immediately

5. **Check if the async event was recieved**
    ```
    docker logs -f hiremenow-job
    ```
    You will see:  
    ASYNC EVENT RECEIVED → Student 1 applied to Job 1  
    Heavy processing finished for application ...

### RabbitMQ Management UI
Open http://localhost:15672 → login: **guest / guest** → see queues and messages live.

## Services & Ports
| Service              | Port   | Health check                          |
|----------------------|--------|---------------------------------------|
| MariaDB              | 3307   | OK                                    |
| RabbitMQ             | 5672   | OK (management UI: http://localhost:15672) |
| Auth Service         | 8081   | `curl http://localhost:8081/`         |
| Job Service          | 8082   | `curl http://localhost:8082/`         |
| Application Service  | 8083   | `curl http://localhost:8083/`         |

## Stop Everything
```bash
docker-compose down -v
```
