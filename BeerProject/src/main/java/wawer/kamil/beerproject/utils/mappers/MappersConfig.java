package wawer.kamil.beerproject.utils.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.model.user.User;

@Configuration
public class MappersConfig {

    @Bean
    public EntityMapper<Brewery, BreweryRequest, BreweryResponse> getBreweryMapper() {
        return new EntityMapper<>(Brewery.class, BreweryResponse.class);
    }

    @Bean
    public EntityMapper<Beer, BeerRequest, BeerResponse> getBeerMapper() {
        return new EntityMapper<>(Beer.class, BeerResponse.class);
    }

    @Bean
    public EntityMapper<User, UserRequest, UserResponse> geUserMapper() {
        return new EntityMapper<>(User.class, UserResponse.class);
    }

}
