package wawer.kamil.beerproject.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.exceptions.InternalException;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
public class EnvironmentProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final Environment environment;
    private String port;
    private String hostname;

    public String getPort() {
        if (port == null)
            port = environment.getProperty("local.server.port");
        return port;
    }

    public String getHostname() throws UnknownHostException {
        // TODO There is something wrong with getting this hostname. It returns ip address which is not the same as localhost. INVESTIGATE IT!
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    public String getServerUrlPrefix() {
        try {
            return "http://" + getHostname() + ":" + getPort();
        } catch (UnknownHostException e) {
            throw new InternalException(e.getMessage());
        }
    }

    public String getContextPath(){
        return this.contextPath;
    }
}