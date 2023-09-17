# Drone Dispatcher Service

## Overview

This repository contains a Drone Dispatcher Service developed using Spring Boot 3 and Spring Data JPA. 

Rigorous testing has been integrated into the project using Mockito, JUnit 5, and WireMock. 

Additionally, comprehensive API documentation is available through Swagger UI.

## Technologies

- **Spring Boot 3**: Leveraging the latest Spring Boot framework ensures high performance and maintainability.

- **Spring Data JPA**: Spring Data simplifies data access, making it easier to work with databases.

- **H2 Database**: By default service uses H2 as the lightweight in-memory database.

- **Validation**: javax.validation ensures data integrity and consistency throughout the application.

- **Lombok**: Lombok reduces boilerplate code, improving code readability and maintainability.

- **Testing**: Rigorous testing methodologies using Mockito, JUnit 5, and WireMock guarantee the reliability of the service.

- **API Documentation**: Swagger has been integrated for clear and comprehensive API documentation, making it easy for users and developers to understand and interact with your service.

## Getting Started

Follow these steps to set up and run the Drone Dispatcher Service on your local machine using Gradle:

1. Clone this repository to your local system using `git clone`.

2. Navigate to the project directory.

3. Build and run the application using Gradle:

   ```shell
   ./gradlew bootRun
   ```

4. Access the API documentation at http://localhost:8080/swagger-ui/index.html to explore the available endpoints and requests examples and interact with the service.

**Initial Data**: Upon application startup, 10 initial drones with predefined deliveries will be added to the system.

## Running Tests

To run both integration and unit tests for the Drone Dispatcher Service, use the following Gradle command:

```shell
./gradlew test
```

## Environment Variables

To configure the Drone Dispatcher Service, you can set the following environment variables:

| Variable Name                       | Description                                                             | Predefined value in application.yml  |
|-------------------------------------|-------------------------------------------------------------------------|--------------------------------------|
| `DATABASE_URL`                      | URL for the database                                                    | jdbc:h2:mem:drones                   |
| `DATABASE_USERNAME`                 | Database username                                                       | sa                                   |
| `DATABASE_PASSWORD`                 | Database password                                                       | pa$$word                             |
| `LOGS_LOCATION`                     | Log file location (logs rotations performs on daily basis)              | ./logs/drones-dispatcher-service.log |
| `DISPATCHER_CHECK_MS`               | How often dispatcher checks drone's states and move them (milliseconds) | 10000                                |
| `DISCHARGE_WHEN_MOVE_PERCENT`       | Level of drone discharging on every move (percent)                      | 5                                    |
| `DISCHARGE_WHEN_IDLE_PERCENT`       | Level of drone discharging in the idle state. 0 - for disable (percent) | 2                                    |
| `CHARGING_ON_EVERY_STEP_BY_PERCENT` | Charging speed (percent)                                                | 5                                    |
| `BATTERY_LEVEL_THRESHOLD`           | Threshold of low battery level (percent)                                | 25                                   |

## Usage

How to interact with the API using JSON payloads and the full list of methods with examples you can check via [Swagger UI](http://localhost:8080/swagger-ui/index.html).

#### Create a Drone example (POST)

To create a new drone, send a POST request with a JSON payload containing the drone's details:

```http
POST /api/v1/drones
Content-Type: application/json

{
  "serialNumber": "DRN_12345",
  "name": "Delivery Drone 1",
  "model": "Cruiserweight",
  "weightLimit": 250
}
```
Ensure that you include the appropriate headers, such as `Content-Type: application/json`, when making the request.

## Support

If you encounter any issues or have questions, please create feel free to contact me at [alex.khlizov@gmail.com](mailto:alex.khlizov@gmail.com).