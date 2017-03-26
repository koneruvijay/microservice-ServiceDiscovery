package com.cognizant.microservices.discovery;

import com.google.common.net.HostAndPort;

public interface ServiceDiscovery {

    HostAndPort discover(String service);
}
