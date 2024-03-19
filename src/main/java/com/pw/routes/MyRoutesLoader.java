package com.pw.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MyRoutesLoader extends RouteBuilder {

    private boolean autoStart = true;

    @Override
    public void configure() throws Exception {

            from("timer:foo3?period=6000")
            .routeId("PJW1").autoStartup(autoStart)
            .setBody(constant("Hello My XML World"))
            .log("Body ${body}");
    }
    
}
