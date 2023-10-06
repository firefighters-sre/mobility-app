package com.redhat.quarkus.routes;

import com.redhat.quarkus.model.MoveLog;

import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MobilityRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.topic.entrance.name}}")
            .routeId("FromEntranceToElevator")
            .unmarshal().json(MoveLog.class)
            .log("Redirect \"${body}\" to Elevator")
            .marshal().json()
            .to("kafka:{{kafka.topic.elevator.name}}");
    }
}
