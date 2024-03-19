package com.pw.routes;

import org.apache.camel.builder.RouteBuilder;

public class TestRouteJavaConfig extends RouteBuilder {

    @Override
    public void configure() throws Exception {
            
        from("timer:foo20?period=5000")
        .routeId("routefromjavaconfig")
        .setBody(constant("Test route from java config"))
        .log("Body ${body}");
    }
    
}
