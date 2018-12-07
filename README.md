Consul Demo
-----------------------------

This is a demo project to demonstrate a Consul API library using Ratpack and to experiment with an embedded Consul Server dependency for testing.


### Start Consul Docker Image
```
docker-compose up -d
```

### Start the App
```
./gradlew run -t
```

### Run Tests

```
./gradlew check
```