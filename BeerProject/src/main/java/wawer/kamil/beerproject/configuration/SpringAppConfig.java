package wawer.kamil.beerproject.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wawer.kamil.beerproject.model.User;

import javax.annotation.PostConstruct;
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

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
