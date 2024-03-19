package com.pw.routes;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.impl.engine.DefaultResourceResolvers;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
public class RouteLoader { //} implements CamelContextAware {

    @Autowired
    private CamelContext camelContext;

    // @Override
    // public CamelContext getCamelContext() {
    //     return camelContext;
    // }

    // @Override
    // public void setCamelContext(CamelContext camelContext) {
    //     this.camelContext = camelContext;
    // }


    @PostConstruct
    void loadRoutes() {
        loadRoute("MyRoutesLoader.java");
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
