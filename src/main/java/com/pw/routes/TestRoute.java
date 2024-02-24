package com.pw.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:foo?period=5000")
        .setBody(constant("Hello Again!"))
        .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"), "Sending message ${body}");
        
     
        // from("jms2:queue:mailbox")        
        // .transacted("txRequired")        
        // .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"),"Sending message ${body}")
        // .log("Headers ${headers}")
        // .setHeader("OriginalMessageID").simple("${headerAs('JMSMessageID', String)}")        
        // .setHeader("OriginalCorrelationID").simple("${headerAs('JMSCorrelationID', String)}")
        // .setHeader("OriginalDestination", simple("${headerAs('JMSDestination', String)}"))        
        // .removeHeaders("JMS*")
        // .log("Headers ${headers}");
        // //.to("kafka:mailbox?brokers=192.168.6.1:29093");        

    }
}
