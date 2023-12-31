# Task Management
This project helps you create, manage and modify your tasks. You can make your daily tasks even easier by dividing them into tasks in this project.You must register to create and manage tasks

## Technologies

[Liquibase](https://docs.liquibase.com/home.html)

[Spring-Data-JPA](https://spring.io/projects/spring-data-jpa)

[Spring-Events](https://www.baeldung.com/spring-events)

[Java-Mail](https://www.baeldung.com/java-email)

## Setup
Clone task-management repository . After cloning repository, setup db. Replace the environment variables in application.yml with your own.Lastly run the application.

# API Documentation

## Register User

### Request

POST - api/v1/users/sign-up

### Request Body
{
"name": "string",
"surname": "string",
"contactNumber": "string",
"username": "string",
"email": "string",
"password": "string",
"address": "string",
"birthAt": "2023-12-02T07:46:41.889Z",
"isRememberMe": true
}

### Response Body

{
"token": "example",
"createDate": 1701504170897,
"expirationDate": 7732642633370897
}

## Create Task

### Request

POST - api/v1/tasks

### Request Body
{
"name": "string",
"priority": "NOT_URGENT",
"status": "TO_DO",
"categoryName": "string",
"description": "string",
"deadline": "2023-12-02T08:04:55.962Z"
}

### Response Body
{
"id": "35fad95e51734bb6b03ed6ffc9b0be66",
"name": "string",
"priority": "NOT_URGENT",
"status": "TO_DO",
"description": "string",
"deadline": "2023-12-02T08:05:56.091",
"isCompleted": false
}

## Get Task

### Request

GET - api/v1/tasks

## Response Body 
{
"id": "35fad95e51734bb6b03ed6ffc9b0be66",
"name": "string",
"priority": "NOT_URGENT",
"status": "TO_DO",
"description": "string",
"deadline": "2023-12-02T08:05:56.091",
"isCompleted": false
}
