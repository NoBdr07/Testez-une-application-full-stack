# YogaApp - Full Stack Application

This project is a full-stack application consisting of a back-end and a front-end. The back-end is built with Spring Boot, while the front-end uses Angular. Below are the steps to set up and run the application, along with instructions for running tests and generating coverage reports.

## Prerequisites

Ensure you have the following installed on your machine:
- Java 11
- NodeJS 16
- MySQL
- Angular CLI 14

## Database Setup
Open MySQL and create the database schema as shown below:

```sql

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

1. Clone the repository and navigate to the project directory.
2. Install dependencies for the front-end:

   ```
   cd front
   npm install
   ```

3. Install dependencies for the back-end:

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

4. Start the front-end application:

```
cd front
ng serve
```

## Running Tests

### Front-end Tests

#### Unit and Integration Tests

Run the following command to execute tests and generate a coverage report:

```
npx jest --coverage
```

The coverage report can be found at: yourFolder/Testez-une-application-full-stack\front\coverage\jest\lcov-report\index.html

#### End-to-end Tests

To run the E2E tests with Cypress, execute:
```
npx cypress open
```

To generate a coverage report for E2E tests:
```
npm run e2e
npm run e2e:coverage
```

The E2E coverage report can be found at:
yourFolder\Testez-une-application-full-stack\front\coverage\lcov-report\index.html

### Back-end Tests
#### Unit Tests

To run the back-end tests and generate a coverage report:
```
mvn clean test
```

The coverage report can be found at:
yourFolder\Testez-une-application-full-stack\back\target\site\jacoco\index.html
