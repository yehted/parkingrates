## Build and Running

### Run from source
./gradlew bootRun

### Building jar
./gradlew build

### Running tests
./gradlew clean test

### Run from docker container
docker build -t parkingrate .
docker run -p 8080:8080 parkingrate

## Swagger doc links
- http://localhost:8080/v2/api-docs
- http://localhost:8080/swagger-ui.html