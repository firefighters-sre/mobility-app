package com.redhat.quarkus.routes;

import com.redhat.quarkus.model.MoveLog;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MobilityRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.topic.entrance.name}}")
            .routeId("FromEntranceToElevatorOrStairs")
            .unmarshal().json(JsonLibrary.Jackson, MoveLog.class)
            .log("Received movement request with Person ID: ${body.personId}") 
            .choice()
                .when(simple("${body.preferredRoute} == 'elevator'"))
                    .to("direct:elevatorProcessing")
                .endChoice()
                .when(simple("${body.preferredRoute} == 'stairs'"))
                    .to("direct:stairsProcessing")
            .end();
        
        from("direct:elevatorProcessing")
            .routeId("ElevatorProcessing")
            .to("micrometer:timer:elevatorTimerRequest?action=start")
            .log("Redirecting to Elevator ${body.destination}.")
            // .delay(simple("${body.destination.toLong} * 1000")) // 1 second per floor
            .marshal().json()
            .to("kafka:{{kafka.topic.elevator.name}}")
            .to("micrometer:timer:elevatorTimerRequest?action=stop")
            .to("micrometer:counter:elevatorCounter");
        
        from("direct:stairsProcessing")
            .routeId("StairsProcessing")
            .to("micrometer:timer:stairsTimerRequest?action=start")
            .log("Redirecting to Stairs ${body.destination}")
            // .delay(simple("${body.destination.toLong} * 3000")) // 3 seconds per floor
            .marshal().json()
            .to("kafka:{{kafka.topic.stairs.name}}")
            .to("micrometer:timer:stairsTimerRequest?action=stop")
            .to("micrometer:counter:stairsCounter");
    }
}
