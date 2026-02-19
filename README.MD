# 🍽️ Meal Tracker API

A RESTful backend application that allows users to search foods, register consumed meals, and calculate daily nutrient intake.

This project was built to practice and demonstrate:

- Spring Boot
- Spring Data JPA
- JWT Authentication
- External API Consumption using WebFlux
- Pagination
- Custom JPQL queries
- Date filtering with `LocalDateTime`
- Data aggregation (daily nutrients)
- Docker usage
- Integration testing

---

## 🚀 Features

### 🔎 Search for a Specific Food

Search foods using the USDA FoodData Central API.

**Endpoint**

GET `/foods/search?name=chicken`

**Example Response**

```json
[
  {
    "name": "Chicken Breast",
    "calories": 165,
    "protein": 31,
    "fat": 3.6,
    "carbohydrates": 0
  }
]

### Register Consumed Food

Allows a user to register a consumed meal, including quantity and timestamp.

Endpoint: POST /entries

Request Body

{
  "foodName": "Apple",
  "quantity": 2,
}

The system:

- Searches the food via USDA API

- Persists a FoodEntry

- Associates nutrients

- Links it to the authenticated user

### Get All Food Entries for a Specific Day

Retrieve all meals consumed by a user on a specific date.

Endpoint: GET /entries?date=2026-02-17

Internal Logic

Converts LocalDate into:

2026-02-17T00:00:00

2026-02-18T00:00:00

Filters using a time range query

Supports pagination

Example Response

{
  "content": [
    {
      "foodName": "Apple",
      "quantity": 2,
      "consumedAt": "2026-02-17T14:30:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1
}

### Get Total Nutrients for a Day

Calculates total nutrients consumed on a given date.

Endpoint: GET /entries/nutrients?date=2026-02-17

Logic

Fetch all entries for the day

Iterate over nutrients of each food

Group nutrients by name

Sum total values

Example Response

[
  {
    "name": "Protein",
    "unitName": "g",
    "totalValue": 120.5
  },
  {
    "name": "Carbohydrates",
    "unitName": "g",
    "totalValue": 85.0
  }
]

## Architecture

Layered architecture: Controller → Service → Repository → Database

- Controller: Handles HTTP requests

- Service: Business logic

- Repository: JPA data access

- Entities: Domain model

## Security

- JWT-based authentication

- Stateless session management

- Role-based authorization

- Protected endpoints

## 🛠 Technologies

- Java 21

- Spring Boot

- Spring Data JPA

- PostgreSQL

- Spring Security

- Redis (optional caching)

- Docker

- USDA FoodData Central API

## ⚙️ Environment Variables

The application requires the following environment variables:

| Variable        | Description                              |
|----------------|------------------------------------------|
| USDA_API_KEY  | API key for USDA FoodData Central        |
| DB_URL        | PostgreSQL connection URL                |
| DB_USERNAME   | Database username                        |
| DB_PASSWORD   | Database password                        |
| JWT_SECRET    | Secret used to sign JWT tokens           |

## 🐳 Running with Docker

If using Docker Compose:

```bash
docker compose up --build
```

Make sure environment variables are defined in .env file or Docker Compose environment section

##🧪 Testing

- Unit tests with JUnit and Mockito

