# Mobility App

The Mobility App is a Quarkus-based application designed to manage the next step of transportation within the facility. Based on the messages from the `entrance` topic, it decides the mode of transport, either directing individuals to the `stairs` or the `elevator`.

## Key Features

- **Entrance Event Processing**: Captures and processes events from the Kafka topic named `entrance`.
- **Stairs or Elevator Decision**: Depending on various factors, directs individuals to either the `stairs` or `elevator` topics.

## TODO List
- [X] **Process Kafka `entrance` Topic Events**: Capture and process events from the Kafka topic named `entrance`.
- [X] **Decision Logic**: Implement logic to decide whether a message should be directed to `stairs` or `elevator` topics.
### 1.1
- [ ] **Monitoring & Alerting**: Set up monitoring tools to keep track of the app's performance and health.
- [ ] **Centralized Logging**: Integrate with a centralized logging system for better traceability.
- [ ] **API Documentation**: Document all exposed APIs and endpoints for better clarity.
- [ ] **Helm Chart Creation**: Design and implement a Helm chart for streamlined deployments of the `mobility-app` on Kubernetes clusters.
- [ ] **Enhanced Decision Logic**: Improve the logic that decides whether a person should take the stairs or elevator.
- [ ] **Security**: Implement security best practices and possibly add authentication mechanisms.
- [ ] **Load Testing**: Conduct performance and load tests to ensure the app's scalability.
- [ ] **Integration Testing**: Conduct tests to ensure smooth integration with other microservices like `concierge-app` and `building-app`.

## Payload Example

Here's an example of a typical payload that the Mobiliyy App expects:

```json
{
    "recordId": 123456,
    "personId": 12345,
    "entryTime": "2023-10-08T09:00:00Z",
    "exitTime": "2023-10-08T17:00:00Z",
    "destination": "5"
}
```
## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/mobility-app-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Camel Jackson ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/jackson.html)): Marshal POJOs to JSON and back using Jackson
- Camel Rest ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/rest.html)): Expose REST services and their OpenAPI Specification or call external REST services
