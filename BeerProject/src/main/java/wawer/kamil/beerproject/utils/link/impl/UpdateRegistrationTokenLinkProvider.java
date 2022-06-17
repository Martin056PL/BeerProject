package wawer.kamil.beerproject.utils.link.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.utils.EnvironmentProperties;
import wawer.kamil.beerproject.utils.link.LinkProvider;

@Component
@RequiredArgsConstructor
public class UpdateRegistrationTokenLinkProvider implements LinkProvider {

    private final EnvironmentProperties env;

    @Override
    public String getLink() {
        return env.getServerUrlPrefix() + env.getContextPath() + "registration/refreshToken?id=%s";
    }
}
