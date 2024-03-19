package com.pw.routes;

/*
 *
 *   Loading Routes from Strings with DefaultCamel Context not managed by Spring
 * 
 *   There should be no camelcontext defined in the spingxml to run this
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class TestRouteDefaultCamelContext {
    
    @Bean    
    public CamelContext camelContextConfiguration() throws Exception {

        // Example of Routes that can be loaded from a database table
        // Route Templates, Route Policies, Error handlers and other resources can be loaded too
        String resourceRoute1 = new StringBuilder("<route id=\"testfromresource1\">")
            .append("<from uri=\"timer://foo10?repeatCount=1\"></from>")
            .append("<setBody>")
            .append(    "<constant>Test Load Route From Resource1</constant>")
            .append("</setBody>")
            .append("<log message=\"Body ${body}\" />")
            .append("</route>")
            .toString();

        String resourceRoute2 = new StringBuilder("<route id=\"testfromresource2\">")
            .append("<from uri=\"timer://foo11?period=8000\"></from>")
            .append("<setBody>")
            .append(    "<constant>Test Load Route From Resource2</constant>")
            .append("</setBody>")
            .append("<log message=\"Body ${body}\" />")                
            .append("</route>")
            .toString();

        // Add Routes to an arraylist to load all at once
        List<Resource> resources = new ArrayList<>();
        resources.add(ResourceHelper.fromString("newRoute1.xml", resourceRoute1));
        resources.add(ResourceHelper.fromString("newRoute2.xml", resourceRoute2));

        // Create camel context
        CamelContext context = new DefaultCamelContext();                       
        
        // Load routes into camel context
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                
                context.adapt(ExtendedCamelContext.class).getRoutesLoader().loadRoutes(resources);
            }
            
        });

        // Start camel context
        context.start();        
        return context;
        
    }
}
