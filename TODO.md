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
- [X] **Route Documentation**: Document all routes for better clarity.
- [X] **Health Endpoint Integration**: Integrated `smallrye-health` to provide health check endpoints for application monitoring.
- [X] **Helm Chart Creation**: Design and implement a Helm chart for streamlined deployments of the `mobility-app` on Kubernetes clusters.
### 1.0.3
- [X] **Implement Basic SLOs, SLAs, and Alerting**
  - [X] **Availability SLO**: Ensure 99.9% uptime over a 10-minute window and create Prometheus rules for alerting.
  - [X] **Latency SLO**: Ensure API response times are under 200ms and event processing times are within 500ms.
  - [X] **Error Rate SLO**: Ensure less than 0.1% of all API requests result in errors.
  - [X] **Availability SLA**: Implement a service credit system for downtime that falls below the agreed availability of 99.8% in a 10-minute window.
  - [X] **Latency SLA**: Implement a service credit for average response time exceeding 200ms for over an hour.
### 1.0.4
- [X] **SLO/SLA prevention Automation**: Implement automation routines to monitor and alert on SLO/SLA disruption. 
  - [X] **Automated Scaling**:
    - [X] **Horizontal Pod Autoscaling (HPA)**: Dynamically scale the number of running pods based on observed CPU utilization or other select metrics.
  - [X] **Self-Healing Systems**:
    - [X] **Liveness and Readiness Probes**: Implement probes to check the health of the application and restart pods that are not responsive.
    - [X] **PodDisruptionBudget (PDB)**: Ensure high availability during voluntary disruptions by defining the minimum available replicas.
### 1.0.5
- [X] **SLOs and SLAs**: Define and implement Service Level Objectives (SLOs) and Service Level Agreements (SLAs) for the mobility services.
  - [X] **Elevator Response Time SLO**: Ensure elevators respond and arrive within 30 seconds of a request during fire incidents over a 10-minute window.
  - [X] **Stairs Traffic Flow SLO**: Monitor the flow of individuals using stairs to avoid bottlenecks or overcrowding over a 10-minute window.
  - [X] **High External Redeliveries SLO**: Alert for high external redeliveries over a 10-minute window.
  - [X] **Elevator Response Time SLA**: If the average elevator response time exceeds 40 seconds over a 30-minute window, a service credit system will be triggered.
- [X] **Stairs Traffic Flow SLA**: Implement a service credit system if congestion in stairwells persists for over 15 minutes.
### 1.0.6
- [ ] **Elevator Capacity Efficiency**:
  - **Objective**: Elevators should operate at no more than 80% capacity during evacuations to ensure safety and swift movement.
  - **Metric**: Track the number of individuals in an elevator during evacuations and compare it to the elevator's maximum capacity.

- [ ] **Stairs Traffic Flow**:
  - **Objective**: Maintain a steady flow of individuals using stairs, avoiding bottlenecks or overcrowding.
  - **Metric**: Monitor the rate at which individuals enter and exit stairwells, ensuring no significant pauses or accumulations.

- [ ] **Stairs Safety Compliance**:
  - **Objective**: Ensure all stairwell users comply with safety guidelines (e.g., using handrails, orderly movement).
  - **Metric**: Use security cameras or other sensors to detect non-compliance events in stairwells.

- [ ] **Backup Systems for Elevators**:
  - **Objective**: In case of primary elevator system failure, backup systems should activate within 1 minute.
  - **Metric**: Monitor the downtime of primary elevator systems and the activation time of backup systems.

- [ ] **Stairs Lighting and Signage**:
  - **Objective**: Maintain 100% uptime for emergency lighting and signage in stairwells during fire incidents.
  - **Metric**: Continuously monitor the operational status of lighting and signage in stairwells

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