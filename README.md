# Time Shift application

Build a REST application from scratch that could serve as a work planning service.

Business requirements:

* A worker has shifts
* A shift is 8 hours long
* A worker never has two shifts on the same day
* It is a 24 hour timetable 0-8, 8-16, 16-24
* Preferably write a couple of units tests

# Workflows

Start application:

```
./gradlew bootRun
```

Swagger UI:

```
http://localhost:8080/swagger-ui/
```

Example API requests:

Add worker:

```
curl -X POST "http://localhost:8080/api/v1/timeplan/schedule/2021-05-01/TIME_0_8/DonaldT" -H  "accept: */*" -d ""

```

Fetch worker time table:

```
curl -X GET "http://localhost:8080/api/v1/timeplan/worker/DonaldT" -H  "accept: */*"
{
  "2021-05-01": "TIME_0_8",
  "2021-05-02": "TIME_8_16"
}
```

Remove worker from time table:

```
curl -X DELETE "http://localhost:8080/api/v1/timeplan/schedule/2021-05-02/TIME_8_16/DonaldT" -H  "accept: */*"
```

Fetch time table:

```
curl -X GET "http://localhost:8080/api/v1/timeplan" -H  "accept: */*"

{
  "timeTable": {
    "2021-05-01": {
      "assignments": {
        "TIME_16_24": [],
        "TIME_0_8": [
          {
            "id": "DonaldT"
          }
        ],
        "TIME_8_16": []
      }
    }
  }
}

```

# Tech stack

* Java 11
* Spring Boot
* Swagger

