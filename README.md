# Mobility App

The Mobility App is a Quarkus-based application designed to manage the next step of transportation within the facility. Based on the messages from the `entrance` topic, it decides the mode of transport, either directing individuals to the `stairs` or the `elevator`.

## Key Features

- **Entrance Event Processing**: Captures and processes events from the Kafka topic named `entrance`.
- **Stairs or Elevator Decision**: Depending on various factors, directs individuals to either the `stairs` or `elevator` topics.

## TODO List
- [X] **Process Kafka `entrance` Topic Events**: Capture and process events from the Kafka topic named `entrance`.
- [X] **Decision Logic**: Implement logic to decide whether a message should be directed to `stairs` or `elevator` topics.
### 1.1
- [X] **Monitoring**: Set up monitoring tools to keep track of the app's performance and health.
  - [X] **Elevator Usage Count**: Number of times the elevator is used within a time frame.
  - [X] **Elevator Average Wait Time**: Average time people wait for the elevator.
  - [X] **Stairs Usage Count**: Number of times the stairs are used within a time frame.
  - [X] **Total People Moved**: Total number of people moved using either the stairs or the elevator.
  - [X] **Average Movement Time**: Average time taken for a person to move from one point to another.
  - [X] **Mobility Efficiency**: Ratio of people moved to the total number of people waiting or intending to move.
- [X] **Logging**: Set up monitoring tools to keep track of the app's performance and health.
  - [X] **Elevator Called**: Log each time the elevator is called, including the floor number.
  - [X] **Elevator Trip Completion**: Log each time the elevator completes a trip, including start and end floors and trip duration.
  - [X] **System Alerts**: Log any system alerts or warnings related to mobility.
- [ ] **SLOs and SLAs**: Define and implement Service Level Objectives (SLOs) and Service Level Agreements (SLAs) for the mobility services.
- [ ] **Alerting**: Set up monitoring tools to keep track of the app's performance and health.
- [ ] **Helm Chart Creation**: Design and implement a Helm chart for streamlined deployments of the `mobility-app` on Kubernetes clusters.
- [ ] **Centralized Logging**: Integrate with a centralized logging system for better traceability.
- [ ] **API Documentation**: Document all exposed APIs and endpoints for better clarity.
### 1.2
- [ ] **Monitoring**: Set up monitoring tools to keep track of the app's performance and health.
  - [ ] **Elevator Capacity Usage**: Percentage utilization of elevator capacity during trips.
  - [ ] **Elevator Travel Time**: Time taken for elevator trips between floors.
  - [ ] **Stairs Congestion Level**: Measure of how congested the stairs are, possibly based on people count over time.
- [ ] **Logging**: Set up monitoring tools to keep track of the app's performance and health. 
  - [ ] **Elevator Trip Completion**: Log each time the elevator completes a trip, including start and end floors and trip duration.
  - [ ] **Elevator Anomalies**: Log any elevator anomalies or issues, e.g., if it stops unexpectedly.
  - [ ] **Stair Access Points**: Log when stair access points (e.g., doors) are opened and closed.
  - [ ] **Stair Usage Estimate**: Potentially log periodic counts or estimates of people using stairs.
  - [ ] **Manual Overrides**: Log any manual overrides or changes to the mobility system (e.g., shutting down an elevator for maintenance).
- [ ] **Enhanced Decision Logic**: Improve the logic that decides whether a person should take the stairs or elevator.
- [ ] **Security**: Implement security best practices and possibly add authentication mechanisms.
- [ ] **Load Testing**: Conduct performance and load tests to ensure the app's scalability.
- [ ] **Integration Testing**: Conduct tests to ensure smooth integration with other microservices like `concierge-app` and `building-app`.

## Payload Example

Here's an example of a typical payload that the Mobiliyy App expects:

```json
{
    "personId": 12345,
    "destination": "5",
    "preferredRoute": "elevator"
}
```

## Monitoring and Metrics 📊

You can access the captured metrics in real-time by navigating to the endpoint `/q/metrics`.

### Key Metrics:

### Elevator Metrics:

- **`elevatorTimerRequest`**:
  - **Description**: This metric measures the time taken for elevator-related requests, giving insight into elevator processing times.
  - **Details**: Provides timing metrics related to elevator requests.

- **`elevatorCounter`**:
  - **Description**: Keeps a count of the number of times the elevator is used.
  - **Details**: Provides a count of elevator usage.

### Stairs Metrics:

- **`stairsTimerRequest`**:
  - **Description**: This metric measures the time taken for stairs-related requests.
  - **Details**: Provides timing metrics related to stairs requests.

- **`stairsCounter`**:
  - **Description**: Keeps a count of the number of times the stairs are used.
  - **Details**: Provides a count of stair usage.

### General Metrics:

- **`camel_exchanges_succeeded_total`**:
  - **Description**: Number of successfully completed exchanges.
  - **Details**: Gives an indication of how many message exchanges were successful.

- **`camel_exchanges_failed_total`**:
  - **Description**: Number of failed exchanges.
  - **Details**: Provides insight into failures in the system. Can be used to detect anomalies.

- **`camel_exchanges_inflight`**:
  - **Description**: Number of inflight message exchanges.
  - **Details**: Indicates messages currently being processed. A spike might indicate congestion or processing delays.

- **`camel_exchanges_external_redeliveries_total`**:
  - **Description**: Number of external initiated redeliveries (e.g., from a JMS broker).
  - **Details**: Insight into how often messages are being redelivered, potentially due to processing issues.

- **`camel_routes_running_routes`**:
  - **Description**: Indicates how many routes are currently active.
  - **Details**: Provides a measure of system health. A drop might indicate issues with certain routes.

- **`camel_exchanges_failures_handled_total`**:
  - **Description**: Number of failures that were handled.
  - **Details**: Shows how many failures were caught and handled by the system.

- **`camel_route_policy_seconds`** (and its related metrics):
  - **Description**: Provides detailed performance metrics about route processing times.
  - **Details**: Gives insights into the performance and efficiency of routes.

- **`camel_exchange_event_notifier_seconds`** (and its related metrics):
  - **Description**: Time taken to send messages to endpoints.
  - **Details**: Provides detailed performance metrics about message handling times.

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
