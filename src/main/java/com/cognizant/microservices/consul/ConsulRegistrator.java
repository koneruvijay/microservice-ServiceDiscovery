package com.cognizant.microservices.consul;

import com.google.inject.Inject;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.Registration;
import com.typesafe.config.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ConsulRegistrator {

    private AgentClient agentClient;

    private Config config;

    private String host, name, id;
    private int port;

    @Inject
    public ConsulRegistrator(Consul consul, Config config) {
        agentClient = consul.agentClient();
        host = config.getString("discovery.advertised.host");
        port = config.getInt("discovery.advertised.port");
        name = config.getString("microservice.name");
        id = String.format("%s-%s", name, UUID.randomUUID().toString());
    }

    public void register() {
        try {
            URL healthUrl = new URL("http", host, port, "/health");
            Registration registration = new Registration();

            registration.setAddress(host);
            registration.setPort(port);
            registration.setId(id);
            registration.setName(name);


            Registration.Check check = new Registration.Check();

            check.setHttp(healthUrl.toExternalForm());
            check.setInterval("5s");

            registration.setCheck(check);

            agentClient.register(registration);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deregister() {
        agentClient.deregister(id);
    }
}
