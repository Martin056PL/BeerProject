package wawer.kamil.beerproject.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.model.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimeZone;

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

    @Bean(name = "UserToUserMapper")
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

    @Bean(name = "BreweryMapper")
    public ModelMapper getBreweryModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Brewery, Brewery>() {
            @Override
            protected void configure() {
                map().setBreweryId(source.getBreweryId());
                map().setName(source.getName());
                map().setEmail(source.getEmail());
                map().setPhoneNumber(source.getPhoneNumber());
                map().setAddress(source.getAddress());
                map().setWebsite(source.getWebsite());
                map().setBeerList(source.getBeerList());
                map().setBreweryImage(source.getBreweryImage());
            }
        });
        return modelMapper;
    }
}
