INTRO - 
This backend application, developed using Spring Boot 3.3.0 and Spring Security 6.X.X.
implements a role-based (User/Admin) registration and login system.
Upon successful registration, the application generates JWT tokens: an access token (valid for 1 hour) and a refresh token (valid for 30 days).
These tokens are used to authorize users, ensuring that only verified users with valid tokens can access certain endpoints outside of the "/api/v1/auth/**" path.

Built With - 
Springboot
JWT
Spring Security

Importatant Depenedencies used in project -
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-validation
postgresql
lombok
jjwt

Project Roadmap -

1. Set Up the Environment
Install JDK 17
Download from the official Oracle JDK website
Install an IDE
Download and install IntelliJ IDEA or Eclipse
Configure IDE for Java 17
Install necessary plugins for Spring Boot development
Clone the repo
git clone git@github.com:panthitesh6410/rakuten-task.git
Open the cloned folder using STS, IntelliJ Idea or any other IDE, and it will auto download all the configuration files.
Run the project, the base URL for the project will be
http://localhost:8080/

Use the postman collection.
