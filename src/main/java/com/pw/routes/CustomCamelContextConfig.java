package com.pw.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultResourceResolvers;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.xml.CamelContextFactoryBean;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CustomCamelContextConfig {

    @Autowired
    ApplicationContext applicationContext;
    
    @Bean    
    public CamelContext camelContextConfiguration() throws Exception {

        // XML DSL Example
        // String newRoute = new StringBuilder("<route id=\"testdata\">")
        //     .append("<from uri=\"timer://foo1?repeatCount=1\"></from>")
        //     .append("<setBody>")
        //     .append(    "<constant>Hello My XML World</constant>")
        //     .append("</setBody>")
        //     .append("<log message=\"Body ${body}\" />")        
        //     .append("<log message=\"Sent JMS Test Message\" />")
        //     .append("</route>")
        //     .toString();

            // String newRoute1 = new StringBuilder("<route id=\"testdata2\">")
            // .append("<from uri=\"timer://foo2?period=8000&repeatCount=1\"></from>")
            // .append("<setBody>")
            // .append(    "<constant>Hello My NEW World</constant>")
            // .append("</setBody>")
            // .append("<log message=\"Body ${body}\" />")        
            // .append("<log message=\"Sent JMS Test Message\" />")
            // .append("</route>")
            // .toString();

        //System.out.println(newRoute);

        // List<Resource> resources = new ArrayList<>();

        // resources.add(ResourceHelper.fromString("newRoute.xml", newRoute));
        // //resources.add(ResourceHelper.fromString("newRoute.xml", newRoute1));

        CamelContext context = new SpringCamelContext(applicationContext);
        context.setSourceLocationEnabled(true);
        context.setTracing(true);
        context.setDebugging(true);        
                
        // loadRoute(context, "MyRoutesLoader.java");     
        // System.out.println("pjw start");

        // context.addRoutes(new RouteBuilder() {

        //     @Override
        //     public void configure() throws Exception {
                
        //         context.adapt(ExtendedCamelContext.class).getRoutesLoader().loadRoutes(resources);
        //     }
            
        // });
        context.start();        
        return context;
        
    }

    // private void loadRoute(CamelContext context, String name) {
    //     ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
    //     RoutesLoader loader = ecc.getRoutesLoader();
    //     try (DefaultResourceResolvers.ClasspathResolver resolver = new DefaultResourceResolvers.ClasspathResolver()) {
    //         resolver.setCamelContext(context);
    //         Resource resource = resolver.resolve("classpath:" + name);
    //         System.out.println("Loading route from resource " + name);
    //         System.out.println("Loading route from " + resource.getLocation());
    //         loader.loadRoutes(resource);
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }        
    // }
}
