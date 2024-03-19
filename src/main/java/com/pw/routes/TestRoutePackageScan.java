package com.pw.routes;

/*
 *  This is to load routes when camel context is managed by spring and parameter packageScan is set
 *     This will not be loaded if contextScan is set
 * 
 *   <camel:camelContext>
 *       <!-- Used to scan for routes not managed by spring. Routes managed by spring @component are not loaded.-->
 *       <camel:packageScan>
 *           <camel:package>com.pw.routes</camel:package>
 *           <camel:includes>**.*</camel:includes>
 *       </camel:packageScan>
 *   </camel:camelContext>    
 * 
 */

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;


public class TestRoutePackageScan extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:foo2?period=5000")
        .routeId("packagescantest")
        .setBody(constant("Test RouteBuilder to test Camel Loading Route with PackageScan!"))
        .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"), "Sending message ${body}");
     
    }
}
