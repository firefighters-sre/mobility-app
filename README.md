# Mobility App

The Mobility App is a Quarkus-based application designed to manage the next step of transportation within the facility. Based on the messages from the `entrance` topic, it decides the mode of transport, either directing individuals to the `stairs` or the `elevator`.

## Key Features

- **Entrance Event Processing**: Captures and processes events from the Kafka topic named `entrance`.
- **Stairs or Elevator Decision**: Depending on various factors, directs individuals to either the `stairs` or `elevator` topics.

## Camel Route Logic

The Mobility App uses Apache Camel to handle incoming events and route individuals based on their preferences:

- **Route: From Entrance to Elevator or Stairs**: 
  - **Source**: Kafka topic `entrance`.
  - **Description**: Processes new events indicating an individual's desired movement method.
  - **Example Event**:
    ```json
    {
        "personId": 12345,
        "preferredRoute": "elevator",
        "destination": "5"
    }
    ```
  - **Actions**:
    - If `preferredRoute` is `elevator`, directs to Kafka topic `elevator`.
    - If `preferredRoute` is `stairs`, directs to Kafka topic `stairs`.

## Event Processing

In the Concierge App, event processing is a crucial feature, especially when dealing with real-time data streams. Here's a breakdown of the Kafka topics the app interacts with:

In the Mobility App, event processing is vital, especially when dealing with real-time data streams that determine the flow of people within the building. Below is a breakdown of the Kafka topics the app interacts with and how it processes incoming and outgoing messages:

#### Input Topics:
- **`entrance`**: This topic receives processed entry-related events from the Concierge App. The Mobility App processes these events to decide the best route (stairs or elevator) for each individual.

#### Output Topics:
- **`elevator`**: Individuals preferring the elevator are directed here.
- **`stairs`**: Individuals preferring the stairs are directed here.
- 
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
