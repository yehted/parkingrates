## Build and Running

### Run from source
`./gradlew bootRun`

### Building jar
`./gradlew build`

### Running tests
`./gradlew clean test`

### Run from docker container
- `docker build -t parkingrate .`
- `docker run -p 8080:8080 parkingrate`

## Swagger doc links
- http://localhost:8080/v2/api-docs
- http://localhost:8080/swagger-ui.html


## Notes
It is probably easiest to interact with the app via the swagger ui.

To retrieve the computed rate for a given input, use the `/requests` controller.
The body should be of the form
```json
{
  "startDate": "2015-07-01T07:00:00-05:00",
  "endDate": "2015-07-01T12:00:00-05:00"
}
```