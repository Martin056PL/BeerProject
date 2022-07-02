package wawer.kamil.beerproject.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EnvironmentProperties {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final Environment environment;
    private String port;

    private static final String DEFAULT_LOCALHOST = "127.0.0.1";

    public String getPort() {
        return Optional.ofNullable(port).orElse(environment.getProperty("local.server.port"));
    }

    public String getHostname() {
        // TODO There is something wrong with getting this hostname. It returns ip address which is not the same as localhost. INVESTIGATE IT!
        return DEFAULT_LOCALHOST;
    }

    public String getServerUrlPrefix() {
        return "http://" + getHostname() + ":" + getPort();
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public String getFullPathToApi() {
        return getServerUrlPrefix() + getContextPath();
    }
}