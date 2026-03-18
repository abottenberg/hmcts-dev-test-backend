# HMCTS Dev Test Backend
This is the backend for the brand new HMCTS case management system.
It provides RESTful endpoints for managing tasks, including:
- Retrieving all tasks
- Retrieving a single task by ID
- Creating a new task
- Updating task status
- Deleting a task

The backend is built using Node.js, Express, and TypeScript.

## Tech Stack

- Java 17
- Spring Boot
- PostgreSQL
- Gradle
- Docker
- JUnit
- Mockito

## Running the Service

You should be able to run `./gradlew build` to start with to ensure it builds successfully. Then from that you
can run the service in IntelliJ (or your IDE of choice) or however you normally would.

To run the service, you will need to have a PostgreSQL database running. You can use Docker to quickly set up a
PostgreSQL instance with the following command:

`docker run --name postgres-cases`
`-e POSTGRES_DB=casesdb`
`-e POSTGRES_USER=postgres`
`-e POSTGRES_PASSWORD=postgres`
`-p 5432:5432`
`-d postgres`

To start the Spring Boot application, run the following command in the terminal:

`./gradlew bootRun`
This will start the application on `http://localhost:4000`.

## Testing

To run the tests, use the following command:

`./gradlew test`


## API Endpoints

- `GET /tasks`: Retrieve all tasks
- `GET /tasks/{id}`: Retrieve a single task by ID
- `POST /tasks`: Create a new task
- `POST /tasks/{id}/update-status`: Update task status
- `POST /tasks/{id}/delete`: Delete a task

