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
            .choice()
                .when(simple("${body.preferredRoute} == 'elevator'"))
                    .log("Redirect \"${body}\" to Elevator")
                    .delay(simple("${body.destinatio.toLong} * 1000")) // 1 second per floor
                    .marshal().json()
                    .to("kafka:{{kafka.topic.elevator.name}}")
                .endChoice()
                .when(simple("${body.preferredRoute} == 'stairs'"))
                    .log("Redirect \"${body}\" to Stairs")
                    .delay(simple("${body.destination.toLong} * 3000")) // 3 seconds per floor
                    .marshal().json()
                    .to("kafka:{{kafka.topic.stairs.name}}")
                .endChoice()
            .end();
    }
}
