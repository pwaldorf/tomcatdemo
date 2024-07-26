package com.pw.routes;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.apache.commons.lang3.function.Failable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.pw.routes.entity.ResourceAttr;

@Component
public class TestRouteDefaultCamelContext2 {

    private String user;

    private String server;

    private String port;

    private String password;

    private String location;

    private String filename;

    private String groupcount;

    private String delimiter;

    private String sshKeyFile;

    @Bean
    public CamelContext camelContextConfiguration() throws Exception {

        // Create camel context
        CamelContext context = new DefaultCamelContext();

        List<ResourceAttr> resourceAttrs = new ArrayList<>();

        try {
            for(ResourceAttr resourceAttr : resourceAttrs) {
                // Add Route File Engine
                String sshKeyFileName = getResourcePath(sshKeyFile);
                Long lastRecord = loadLastCurr(resourceAttr.getFileName());
                context.addRoutes(createRouteBuilder(resourceAttr, sshKeyFileName, lastRecord));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating new router file route", e);
        }

        // Start camel context
        context.start();
        return context;

    }

    private String getResourcePath(String fileName) {
        return TestRouteDefaultCamelContext2.class.getClassLoader().getResource(fileName).getPath();
    }

    private RouteBuilder createRouteBuilder(ResourceAttr resourceAttr, String sshKeyFileName, Long lastRecord) {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                templatedRoute("gwhFileRouteTemplate")
                    .routeId("gwhFile" + resourceAttr.getFileName())
                    .parameter("user", user)
                    .parameter("sftpServer", server)
                    .parameter("sftpPort", port)
                    .parameter("location", resourceAttr.getLocation())
                    .parameter("filename", resourceAttr.getFileName())
                    .parameter("groupcount", groupcount)
                    .parameter("lastRecord", Long.toString(lastRecord))
                    .parameter("sshKeyFileName", sshKeyFileName);
            }
        };
    }

    private long loadLastCurr(String fileName) {
        return 0L;
    }

}
