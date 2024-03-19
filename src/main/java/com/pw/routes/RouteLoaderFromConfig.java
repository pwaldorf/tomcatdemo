package com.pw.routes;

/*
 *
 *   Loading Routes from from the classpath when using spring managed camelcontext
 *
 *   This will load from contextScan, packageScan and DefaultCamelContext
 * 
 */

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.impl.engine.DefaultResourceResolvers;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteLoaderFromConfig {

    @Autowired
    private CamelContext camelContext;


    @PostConstruct
    void loadRoutes() {
        System.out.println("Loading from RouteLoaderFromConfig");
        loadRoute("camel/TestRouteJavaConfig.java");
    }

    private void loadRoute(String name) {
        ExtendedCamelContext ecc = camelContext.adapt(ExtendedCamelContext.class);
        RoutesLoader loader = ecc.getRoutesLoader();
        try (DefaultResourceResolvers.ClasspathResolver resolver = new DefaultResourceResolvers.ClasspathResolver()) {
            resolver.setCamelContext(camelContext);
            Resource resource = resolver.resolve("classpath:" + name);
            System.out.println("Loading route from resource " + name);
            System.out.println("Loading route from " + resource.getLocation());
            loader.loadRoutes(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }
    
}
