# F1 API

## Introduction

The F1 API is a RESTful API that provides access to Formula 1 data. 

It allows users to retrieve information about events and drivers. Also it provides API for the betting system. 

## How to run
1. Run maven clean install to build the project:
    ```bash 
    ./mvnw clean install
    ```

2. Run docker compose to start the Postgresql database:
    ```bash
    docker compose -f 'docker/docker-compose.yml' up -d
    ```

3. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```

4. Access the API using Postman
   1. Get F1 events with drivers:
        ```http
        GET http://localhost:8080/api/v1/events
        ```
   2. Place a bet:
        ```http
        POST http://localhost:8080/api/v1/bets
        Content-Type: application/json
        {
            "userId": 1,
            "eventId": 1,
            "driverId": 1,
            "amount": 100
        }
        ```
   3. Finish event:
        ```http
        PATCH http://localhost:8080/api/v1/events/1
        Content-Type: application/json
        {
            "winnerDriverId": 1
        }
        ```

5. Access the database to check the outcome of the bets

   Connection string: `jdbc:postgresql://localhost:5432/f1`

   Username: `f1`
   
   Password: `f1`