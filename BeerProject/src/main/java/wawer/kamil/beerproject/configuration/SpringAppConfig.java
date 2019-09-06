package wawer.kamil.beerproject.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
