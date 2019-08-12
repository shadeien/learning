package com.shadeien.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

//@Service
public class SelfRefreshListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof RefreshRoutesEvent) {
            System.out.println("RefreshRoutesEvent");
            discoveryClientRouteDefinitionLocator.getRouteDefinitions();
        }
    }
}
