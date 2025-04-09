# DSA Final Project - Binary Search Tree Application
# BACK END
### NAME - Steven Norris
### DATE - April 9th 2025

## About this repository

**DSA Final Project** is a Spring Boot-based backend application that enables users to create, view, and balance Binary Search Trees (BSTs). The application accepts comma-separated numbers to build a BST, stores both the unbalanced and balanced versions, and exposes RESTful endpoints for interacting with these trees.

## Overview

This project demonstrates efficient backend development using Spring Boot. Key features include:
- **BST Creation:** Build a BST from user-supplied numbers.
- **Tree Balancing:** Rebalance an unbalanced BST for optimal search efficiency.
- **REST APIs:** Expose endpoints for creating, retrieving, and balancing trees.
- **Robust Error Handling:** Includes proper error checks and CORS configuration.
- **Testing:** Comprehensive unit and integration tests are in place.

## Getting Started

### Prerequisites
- **Java 17+**  
- **Maven** for building the project  
- **H2 Database** (default in-memory)  
- **Git** for version control

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/dsa-final-project.git
   cd dsa-final-project
   ```
2. **Build the project with Maven:**
   ```bash
   mvn clean install
   ```

### Running Locally
To launch the backend:
```bash
mvn spring-boot:run
```
The application will be available at: [http://localhost:8080](http://localhost:8080)

## Usage Examples

### API Endpoints
- **Create BST**  
  **POST** `/api/trees`  
  **Request Body:**
  ```json
  { "numbers": "5,2,7" }
  ```
  **Response:** A `TreeRecord` containing the original input and JSON representation of the BST.

- **Get All Unbalanced BSTs**  
  **GET** `/api/trees`  
  **Response:** A list of `TreeRecord` objects.

- **Balance BST**  
  **POST** `/api/balance/{treeId}`  
  **Response:** Returns the balanced BST as a `BalancedTreeRecord`.

- **Get All Balanced BSTs**  
  **GET** `/api/balanced`  
  **Response:** A list of `BalancedTreeRecord` objects.

## Project Structure

```
src/
├── config/                  # Application configurations (e.g., CORS settings).
├── controller/              # REST controllers for API endpoints.
├── model/                   # Domain models and BST logic.
├── repository/              # Spring Data JPA repositories.
├── service/                 # Business logic for BST management.
└── DsaFinalApplication.java # Application entry point.
```

## Configuration

- **CORS Settings:** Configured in `WebConfig.java` to allow requests from `http://localhost:3000`.
- **Database:** Uses H2 (in-memory) by default. Configure `application.properties` for alternative data sources if needed.
- **Environment Variables:**  
  Set `REACT_APP_API_URL` on the frontend to point to your backend (if running separately).

## Testing Instructions

- **Unit & Integration Tests:**  
  Run tests with:
  ```bash
  mvn test
  ```
  Tests cover model logic, repository operations, service layer functionality, and REST API endpoints.


