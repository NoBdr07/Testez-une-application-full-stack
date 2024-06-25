# YogaApp - Full Stack Application - Back

This project is a full-stack application consisting of a back-end and a front-end. This back-end is built with Spring Boot, while the front-end uses Angular. Below are the steps to set up and run the application, along with instructions for running tests and generating coverage reports.

## Prerequisites

Ensure you have the following installed on your machine:
- Java 11
- NodeJS 16
- MySQL

## Database Setup

1. Open MySQL and create the database schema as shown below:

```

CREATE DATABASE yogaapp;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(40) DEFAULT NULL,
  `first_name` varchar(40) DEFAULT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `teachers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(40) DEFAULT NULL,
  `first_name` varchar(40) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sessions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `sessions_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `participate` (
  `user_id` int DEFAULT NULL,
  `session_id` int DEFAULT NULL,
  KEY `user_id` (`user_id`),
  KEY `session_id` (`session_id`),
  CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `participate_ibfk_2` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## Application Installation

1. Clone the repository from https://github.com/NoBdr07/Testez-une-application-full-stack and navigate to the back directory.

2. Install dependencies for the back-end:
```
cd back
mvn clean install
```

## Running the Application

1. Update the application.properties file in the back-end project with your database details.

2. Initialize the database with some data.

3. Start the back-end API
```
cd back
mvn spring-boot:run
```

4. Then you can try it with Postman or directly from the front-end.

## Running Tests

### Back-end Tests
#### Unit Tests

To run the back-end tests and generate a coverage report:
```
mvn clean test
```

The coverage report can be found at:
yourFolder\Testez-une-application-full-stack\back\target\site\jacoco\index.html
