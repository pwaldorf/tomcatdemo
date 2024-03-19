package com.pw.routes;

/*  
 *
 *   This example loads routes from a java config file in the resource directory of the project
 *
 *   The example uses a DefaultCamelContext, which is not managed by Spring
 *
 *   There should be no camelcontext defined in the spingxml to run this
 *
 */

import org.apache.camel.CamelContext; 
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultResourceResolvers;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RouteLoaderFromClassPath {

    @Bean    
    public CamelContext camelContextConfiguration() throws Exception {

        CamelContext context = new DefaultCamelContext();
  
        // Load routes from xml, yml or java config file in resource directory of project
        System.out.println("Loading from RouteLoaderFromClassPath");
        loadRoute(context, "camel/TestRouteJavaConfig.java");     

        context.start();        
        return context;
        
    }

    private void loadRoute(CamelContext context, String name) {
        ExtendedCamelContext ecc = context.adapt(ExtendedCamelContext.class);
        RoutesLoader loader = ecc.getRoutesLoader();
        try (DefaultResourceResolvers.ClasspathResolver resolver = new DefaultResourceResolvers.ClasspathResolver()) {
            resolver.setCamelContext(context);
            Resource resource = resolver.resolve("classpath:" + name);
            System.out.println("Loading route from resource " + name);
            System.out.println("Loading route from " + resource.getLocation());
            loader.loadRoutes(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }
    
}
