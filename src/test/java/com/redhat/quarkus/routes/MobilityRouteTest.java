package com.redhat.quarkus.routes;

import com.redhat.quarkus.model.MoveLog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.quarkus.test.CamelQuarkusTestSupport;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MobilityRouteTest extends CamelQuarkusTestSupport {

  @Inject
  ProducerTemplate producerTemplate;

  @Inject
  CamelContext context;

  @Override
  protected CamelContext createCamelContext() throws Exception {
      return this.context;
  }

  @Test
  void testRedirectToElevator() throws Exception {

    String moveLog = "{\"personId\":1,\"destination\":\"1\",\"preferredRoute\":\"elevator\"}";

    final MockEndpoint mockElevator = getMockEndpoint("mock:{{kafka.topic.elevator.name}}");
    mockElevator.expectedMessageCount(1);

    final MockEndpoint mockStairs = getMockEndpoint("mock:{{kafka.topic.stairs.name}}");
    mockStairs.expectedMessageCount(0);

    // Send a message to the route
    final Exchange exchange = this.createExchangeWithBody(moveLog);
        
    this.producerTemplate.send("direct:{{kafka.topic.entrance.name}}", exchange);

    mockElevator.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getDestination().equals(1));
    mockElevator.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getPersonId().equals(1L));
    mockElevator.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getPreferredRoute().equals("elevator"));
    
    mockElevator.assertIsSatisfied();
    mockStairs.assertIsSatisfied();

  }

  @Test
  void testRedirectToStairs() throws Exception {

    String moveLog = "{\"personId\":1,\"destination\":\"1\",\"preferredRoute\":\"stairs\"}";

    final MockEndpoint mockElevator = getMockEndpoint("mock:{{kafka.topic.elevator.name}}");
    mockElevator.expectedMessageCount(0);

    final MockEndpoint mockStairs = getMockEndpoint("mock:{{kafka.topic.stairs.name}}");
    mockStairs.expectedMessageCount(1);

    // Send a message to the route
    final Exchange exchange = this.createExchangeWithBody(moveLog);
        
    this.producerTemplate.send("direct:{{kafka.topic.entrance.name}}", exchange);

    mockElevator.assertIsSatisfied();

    mockStairs.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getDestination().equals(1));
    mockStairs.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getPersonId().equals(1L));
    mockStairs.expectedMessagesMatches(e -> !e.getIn().getBody(MoveLog.class).getPreferredRoute().equals("stairs"));
    
    mockStairs.assertIsSatisfied();

  }

  @Override
  protected RouteBuilder createRouteBuilder() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        
        from("direct:{{kafka.topic.entrance.name}}")
            .routeId("FromEntranceToElevatorOrStairs")
            .unmarshal().json(JsonLibrary.Jackson, MoveLog.class)
            .log("Received movement request with Person ID: ${body.personId}") 
            .choice()
              .when(simple("${body.preferredRoute} == 'elevator'"))
                .to("direct:elevatorProcessing")
              .endChoice()
              .when(simple("${body.preferredRoute} == 'stairs'"))
                .to("direct:stairsProcessing")
              .endChoice()
            .end();
        
        from("direct:elevatorProcessing")
            .routeId("ElevatorProcessing")
            .log("Redirecting to Elevator ${body.destination}.")
            // .delay(simple("${body.destination.toLong} * 1000")) // 1 second per floor
            .marshal().json()
            .to("mock:{{kafka.topic.elevator.name}}");
        
        from("direct:stairsProcessing")
            .routeId("StairsProcessing")
            .log("Redirecting to Stairs ${body.destination}")
            // .delay(simple("${body.destination.toLong} * 3000")) // 3 seconds per floor
            .marshal().json()
            .to("mock:{{kafka.topic.stairs.name}}");
      }
    };
  }
}