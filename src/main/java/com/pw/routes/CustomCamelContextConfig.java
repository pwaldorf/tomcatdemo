package com.pw.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomCamelContextConfig {
    
    
    @Bean    
    public CamelContext camelContextConfiguration() throws Exception {

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

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                Resource resource = ResourceHelper.fromString("newRoute.xml", newRoute);
                context.adapt(ExtendedCamelContext.class).getRoutesLoader().loadRoutes(resource);
            }
            
        });
        context.start();
        return context;


    }
}
