# YogaApp - Full Stack Application - Front

This project is a full-stack application consisting of a back-end and a front-end. The back-end is built with Spring Boot, while the front-end uses Angular. Below are the steps to set up and run the application, along with instructions for running tests and generating coverage reports.

## Prerequisites

Ensure you have the following installed on your machine:
- NodeJS 16
- Angular CLI 14

## Application Installation

1. Clone the repository from https://github.com/NoBdr07/Testez-une-application-full-stack and navigate to the front directory.
2. Install dependencies for the front-end:

   ```
   cd front
   npm install
   ```

## Running the Application

1. Chose to use the back-end or replace it with the tool of your choice.

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
Then you'll have an interface where you can choose which test you want to run.

To generate a coverage report for E2E tests, run the app with ng serve, then run this command :
```
npm run e2e
```
A window will open with the cypress interface. Launch the all.cy file and wait until all the tests pass. Then closed the two cypress windows. Then run this command :
```
npm run e2e:coverage
```
The E2E coverage report can be found at:
yourFolder\Testez-une-application-full-stack\front\coverage\lcov-report\index.html
