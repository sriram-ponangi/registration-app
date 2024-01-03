# REGISTRATION-APP

## Description
Registration App is a microservice written using Spring Boot to simulate user registration.

## API Documentation:
After starting the registration app you can:
- Access the API documentation in JSON here: http://localhost:8089/api/v3/api-docs
- Access the Swagger UI for API documentation here: http://localhost:8089/api/swagger-ui/index.html


## Requirements:

> ### Task-1: API Creation /api/v1/user/registration
- Expose a REST API to accept a payload containing username, password, and IP address to register the user.

> ### Task-2: Request Payload Validation
- All parameters in the request payload (username, password, and IP address) must not be blank (!= empty and null). Return error messages if not valid.
- Password needs to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set (_ # $ % .). Return error messages if not valid.
- Call this API end point to get geolocation for the provided IP address: https://ip-api.com/docs/api:json
- If the IP is not in Canada, return error message that user is not eligible to register.


> ### Task-3: Generate Response

- When all validation is passed, return a random uuid and a welcome message with username and City Name (resolved using ip-geolocation api)

> ### Task-4: Document the API specifications
- The API needs to have OpenAPI specification.
- Project must use Maven or Gradle to build. Generate a spring boot project here: https://start.spring.io/

> ### Task-5: Write Unit Tests
- Need to have JUnit Tests



Need to run this in jenkins container:
ssh-keyscan github.com >> ~/.ssh/known_hosts

And approve the jenkins dsl script
