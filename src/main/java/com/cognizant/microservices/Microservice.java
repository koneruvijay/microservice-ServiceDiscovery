package com.cognizant.microservices;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import com.cognizant.microservices.consul.ConsulLifecycleListener;
import com.typesafe.config.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import java.util.EnumSet;
import java.util.List;

public abstract class Microservice
{

    public void run()
    {
        List<Module> serviceModules = Lists.asList(new ChassisModule(), getModules());

        Injector injector =  Guice.createInjector(Stage.PRODUCTION, serviceModules);

        int port = injector.getInstance(Config.class).getInt("microservice.port");

        final Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        context.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));

        context.addFilter(GuiceFilter.class, "/*", EnumSet.of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));

        context.addServlet(DefaultServlet.class, "/*");

        try
        {
            server.addLifeCycleListener(injector.getInstance(ConsulLifecycleListener.class));
            server.setStopAtShutdown(true);
            server.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        try
        {
            server.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public abstract Module[] getModules();
}
