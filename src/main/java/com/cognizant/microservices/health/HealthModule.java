package com.cognizant.microservices.health;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class HealthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HealthResource.class).in(Scopes.SINGLETON);
    }
}
