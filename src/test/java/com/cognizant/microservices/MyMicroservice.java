package com.cognizant.microservices;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.typesafe.config.Config;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.UUID;

public class MyMicroservice extends Microservice {

    public static void main(String... args) {
        new MyMicroservice().run();
    }

    @Override
    public Module[] getModules() {
        return new Module[] {
            new RequestScopeModule() {
                @Override
                protected void configure()
                {
                    bind(ConfigurationResource.class).in(Scopes.SINGLETON);
                }
            }
        };
    }

    @Path("/")
    public static class ConfigurationResource {

        @Inject
        private Config config;

        @GET
        @Produces("application/json")
        public List<User> getResource() {

            List<User> users = new LinkedList<User>();
            User user1 = new User();
            user1.setUserId(UUID.randomUUID());
            users.add(user1);
            User user2 = new User();
            user2.setUserId(UUID.randomUUID());
            users.add(user2);
            return users;
           //return ImmutableMap.of("sample.config", config.getString("sample.config"),
             //       "microservice.name", config.getString("microservice.name"));
        }
    }
}
