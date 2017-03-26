package com.cognizant.microservices.consul;

import com.google.inject.Inject;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;

public class ConsulLifecycleListener extends AbstractLifeCycle.AbstractLifeCycleListener {

    private ConsulRegistrator consulRegistrator;

    @Inject
    public ConsulLifecycleListener(ConsulRegistrator consulRegistrator) {
        this.consulRegistrator = consulRegistrator;
    }

    @Override
    public void lifeCycleStarted(LifeCycle event) {
        consulRegistrator.register();
    }

    @Override
    public void lifeCycleStopping(LifeCycle event) {
        System.out.println("Stopping");
        consulRegistrator.deregister();
    }
}
