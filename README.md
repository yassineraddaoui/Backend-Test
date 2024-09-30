# To-Do App API

This is a RESTful API for managing tasks within a SaaS application for companies. It provides endpoints for handling tasks related to users, companies, and their relationships.

## Table of Contents
- [Features](#features)
- [Endpoints](#endpoints)
- [Running the Application](#running-the-application)
- [Data Initialization](#data-initialization)
- [Possible Enhancements](#possible-enhancements)

## Features
- Manage tasks for users and companies
- Create, update, delete, and retrieve tasks
- Company-specific and user-specific task management
- In-memory datastore 

## Endpoints

### General Task Management
- `POST /api/v1/tasks` - Create a new task
- `GET /api/v1/tasks` - Retrieve all tasks
- `GET /api/v1/tasks/{id}` - Retrieve a task by its ID
- `PUT /api/v1/tasks` - Update a task
- `DELETE /api/v1/tasks/{id}` - Delete a task by its ID

### User-Specific Task Management
- `GET /api/v1/user/{userId}/tasks` - Retrieve all tasks for a specific user
- `GET /api/v1/user/{userId}/tasks/{taskId}` - Retrieve a specific task for a user
- `PUT /api/v1/user/{userId}/tasks` - Update a task by a specific user
- `DELETE /api/v1/user/{userId}/tasks/{taskId}` - Delete a task by a specific user

### Company-Specific Task Management
- `GET /api/v1/company/{companyId}/tasks` - Retrieve all tasks for a specific company
- `GET /api/v1/company/{companyId}/tasks/{taskId}` - Retrieve a specific task for a company
- `PUT /api/v1/company/{companyId}/tasks` - Update a task for a specific company
- `DELETE /api/v1/company/{companyId}/user/{userId}/tasks` - Delete all tasks for a company and a specific user

## Running the Application
To run the application, make sure you have the following setup:
            
             Java 17 or higher
## Data Initialization
The `data.sql` file is located in the `src/main/resources` directory. It contains SQL statements for initializing the database with predefined tasks and users, which can be used for testing and verification purposes.
## Possible Enhancements

Here are some potential improvements that can be made to the current project:

- **Pagination**: Implement pagination to efficiently handle large datasets when retrieving tasks.
- **Logging**: Add structured logging for better traceability and easier debugging.
- **Validation**: Introduce input validation to ensure data integrity before processing requests.
- **Authorization**: Implement role-based access control (RBAC) to manage user permissions and secure API endpoints.
