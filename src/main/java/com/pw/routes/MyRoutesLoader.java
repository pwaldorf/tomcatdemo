package com.pw.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyRoutesLoader extends RouteBuilder {

    @Autowired
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        // XML DSL Example
        String newRoute = new StringBuilder("<route id=\"testdata\">")
            .append("<from uri=\"timer://foo1?period=5000\"></from>")
            .append("<setBody>")
            .append(    "<constant>Hello My XML World</constant>")
            .append("</setBody>")
            .append("<log message=\"Body ${body}\" />")
        //    .append("<to uri=\"jmsProducerTransacted:queue:mailbox\"/>")
            .append("<log message=\"Sent JMS Test Message\" />")
            .append("</route>")
            .toString();

        System.out.println(newRoute);
        Resource resource = ResourceHelper.fromString("newRoute.xml", newRoute);
        camelContext.adapt(ExtendedCamelContext.class).getRoutesLoader().loadRoutes(resource);
    }
    
}
