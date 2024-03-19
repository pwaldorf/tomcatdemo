package com.pw.routes;

/*
 *  This is to load routes when camel context is fully managed by spring and parameter contextScan is set
 *     This will not be loaded if packageScan is set
 * 
 *   <camel:camelContext>
 *       <camel:contextScan/> <!-- used to scan camel context for @component routes-->
 *   </camel:camelContext>    
 * 
 */

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TestRouteContextScan extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:foo1?period=5000")
        .routeId("contextscantest")
        .setBody(constant("Test RouteBuilder to test Camel Loading Route with ContextScan!"))
        .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"), "Sending message ${body}");
    }
    
}
