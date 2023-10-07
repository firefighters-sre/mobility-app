package com.redhat.quarkus.routes;

import com.redhat.quarkus.model.MoveLog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.quarkus.test.CamelQuarkusTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MobilityRouteTest extends CamelQuarkusTestSupport {

  @Inject
  MobilityRoute mobilityRoute;

  @Inject
  ProducerTemplate producerTemplate;

  @Override
  public boolean isUseAdviceWith() {
    return true;
  }

  // @Override
  // public boolean isUseRouteBuilder() {
  // return false;
  // }

  @Test
  void testRedirectToElevator() throws Exception {
    AdviceWith.adviceWith(this.context, "FromEntranceToElevatorOrStairs", a -> {
      a.mockEndpointsAndSkip("kafka:{{kafka.topic.entrance.name}}");
      a.mockEndpointsAndSkip("kafka:{{kafka.topic.elevator.name}}");
      a.mockEndpointsAndSkip("kafka:{{kafka.topic.stairs.name}}");
    });

    MoveLog log = new MoveLog();
    log.setPreferredRoute("elevator");
    log.setDestination("A"); // Let's say 3 floors

    final MockEndpoint mockEntrance = getMockEndpoint("mock:kafka:{{kafka.topic.entrance.name}}");
    mockEntrance.expectedMessageCount(1);

    final MockEndpoint mockElevator = getMockEndpoint("mock:kafka:{{kafka.topic.elevator.name}}");
    mockElevator.expectedMessageCount(1);

    final MockEndpoint mockStairs = getMockEndpoint("mock:kafka:{{kafka.topic.stairs.name}}");
    mockStairs.expectedMessageCount(0);

    // Send a message to the route
    final Exchange exchange = this.createExchangeWithBody(log);
        
    this.producerTemplate.send("mock:kafka:{{kafka.topic.entrance.name}}", exchange);

    mockEntrance.assertIsSatisfied();
    // mockElevator.assertIsSatisfied();
    // mockStairs.assertIsSatisfied();

    // Check if the message is as expected
    // MoveLog processedLog = mockElevator.getExchanges().get(0).getIn().getBody(MoveLog.class);
    // Assertions.assertEquals(log.getPreferredRoute(), processedLog.getPreferredRoute());
  }

  @Test
  void testRedirectToStairs() throws Exception {
    // Similar to the previous test but for stairs
  }

  @Override
  protected RouteBuilder createRouteBuilder() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {

        from("kafka:{{kafka.topic.entrance.name}}")
            .routeId("FromEntranceToElevatorOrStairs")
            .unmarshal().json(MoveLog.class)
            .log("REDIRECT \"${body}\"")
            .choice()
              .when(simple("${body.preferredRoute} == 'elevator'"))
                .log("Redirect \"${body}\" to Elevator")
                // .delay(simple("${body.destination} * 30000")) // 30 seconds per floor
                .marshal().json()
              .to("kafka:{{kafka.topic.elevator.name}}")
            .endChoice()
              .when(simple("${body.preferredRoute} == 'stairs'"))
                .log("Redirect \"${body}\" to Stairs")
                // .delay(simple("${body.destination} * 10000")) // 10 seconds per floor
                .marshal().json()
                .to("kafka:{{kafka.topic.stairs.name}}")
            .endChoice()
          .end();
      }
    };
  }

  // @Override
  // protected void doPostSetup() throws Exception {
  // context.getRouteDefinition("FromEntranceToElevatorOrStairs")
  // .adviceWith(context, new RouteBuilder() {
  // @Override
  // public void configure() throws Exception {
  // replaceFromWith("direct:kafka:{{kafka.topic.entrance.name}}");
  // }
  // });
  // context.start();
  // }
}
