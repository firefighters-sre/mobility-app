# Mobility App

The Mobility App is a Quarkus-based application designed to manage the next step of transportation within the facility. Based on the messages from the `entrance` topic, it decides the mode of transport, either directing individuals to the `stairs` or the `elevator`.

## TODO List
### 1.0
- [X] **Process Kafka `entrance` Topic Events**: Capture and process events from the Kafka topic named `entrance`.
- [X] **Decision Logic**: Implement logic to decide whether a message should be directed to `stairs` or `elevator` topics.
### 1.0.1
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
### 1.0.2
- [ ] **API Documentation**: Document all exposed APIs and endpoints for better clarity.
- [X] **Health Endpoint Integration**: Integrated `smallrye-health` to provide health check endpoints for application monitoring.
- [ ] **Helm Chart Creation**: Design and implement a Helm chart for streamlined deployments of the `mobility-app` on Kubernetes clusters.
### 1.0.3
- [ ] **SLOs and SLAs**: Define and implement Service Level Objectives (SLOs) and Service Level Agreements (SLAs) for the mobility services.
- [ ] **Alerting**: Set up monitoring tools to keep track of the app's performance and health.

### Backlog
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
- [ ] **Centralized Logging**: Integrate with a centralized logging system for better traceability.