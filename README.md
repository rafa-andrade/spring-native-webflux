# Spring Native Webflux Project
Proof of concept project using Spring Boot 3, Spring Native and Spring Webflux

### Prerequisites and Dependencies
- Java 17
- GraalVM
- Docker
- Docker Compose
- 
### Build the project
```
./mvnw clean install
```

### Local environment setup (mongo and mongo-express)
```
docker-compose -f docker-compose.override.yml up
```

### Create docker image (Spring Native)
```
./mvnw spring-boot:build-image -Pnative
```

### Local start env
```
docker-compose up
```

### API Documentation
http://localhost:8080/swagger-ui.html