package wawer.kamil.beerproject.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.model.User;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Configuration
public class SpringAppConfig {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean(name = "defaultMapper")
    @Primary
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "breweryRequestMapper")
    public ModelMapper getBreweryRequestMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(
                new PropertyMap<BreweryRequest, Brewery>() {
                    @Override
                    protected void configure() {
                        skip(destination.getBreweryId());
                        map().setName(source.getName());
                        map().setEmail(source.getEmail());
                        map().setPhoneNumber(source.getPhoneNumber());
                        map().getAddress().setStreet(source.getAddress().getStreet());
                        map().getAddress().setCity(source.getAddress().getCity());
                        map().getAddress().setLocalNumber(source.getAddress().getLocalNumber());
                        map().getAddress().setParcelNumber(source.getAddress().getParcelNumber());
                        map().getAddress().setZipCode(source.getAddress().getZipCode());
                        map().setWebsite(source.getWebsite());
                        map().setBeerList(source.getBeerList()
                                .stream()
                                .map(beerRequest -> modelMapper.map(beerRequest, Beer.class))
                                .collect(Collectors.toList())
                        );
                    }
                }
        );
        return modelMapper;
    }

    @Bean(name = "UserMapper")
    public ModelMapper getUserModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, User>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                map().setUsername(source.getUsername());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setGrantedAuthorities(source.getGrantedAuthorities());
                map().setAccountNonExpired(source.isAccountNonExpired());
                map().setAccountNonLocked(source.isAccountNonLocked());
                map().setCredentialsNonExpired(source.isCredentialsNonExpired());
                map().setEnabled(source.isEnabled());
            }
        });
        return modelMapper;
    }
}
