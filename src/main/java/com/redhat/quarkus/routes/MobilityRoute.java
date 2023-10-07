package com.redhat.quarkus.routes;

import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MobilityRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.topic.entrance.name}}")
            .routeId("FromEntranceToElevatorOrStairs")
            .choice()
                .when(simple("${body.preferredRoute} == 'elevator'"))
                    .log("Redirect \"${body}\" to Elevator")
                    .delay(simple("${body.destination} * 10000")) // 30 seconds per floor
                    .marshal().json()
                    .to("kafka:{{kafka.topic.elevator.name}}")
                .endChoice()
                .when(simple("${body.preferredRoute} == 'stairs'"))
                    .log("Redirect \"${body}\" to Stairs")
                    .delay(simple("${body.destination} * 30000")) // 10 seconds per floor
                    .marshal().json()
                    .to("kafka:{{kafka.topic.stairs.name}}")
                .endChoice()
            .end();
    }
}
