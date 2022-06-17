package wawer.kamil.beerproject.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wawer.kamil.beerproject.model.email.EmailType;
import wawer.kamil.beerproject.utils.EnvironmentProperties;
import wawer.kamil.beerproject.utils.link.LinkProvider;
import wawer.kamil.beerproject.utils.link.impl.RegistrationLinkProvider;
import wawer.kamil.beerproject.utils.link.impl.UpdateRegistrationTokenLinkProvider;

import java.util.*;

@Configuration
@AllArgsConstructor
public class LinkPatternProviderConfiguration {

    private final EnvironmentProperties properties;

    @Bean
    public Map<EmailType, LinkProvider> getLinkPatterProviderMap() {
        Map<EmailType, LinkProvider> map = new EnumMap<>(EmailType.class);
        map.put(EmailType.REGISTRATION_CONFIRMATION, new RegistrationLinkProvider(properties));
        map.put(EmailType.UPDATE_REGISTRATION_TOKEN, new UpdateRegistrationTokenLinkProvider(properties));
        return Collections.unmodifiableMap(map);
    }
}
