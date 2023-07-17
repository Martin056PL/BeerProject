package wawer.kamil.beerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
@EnableAspectJAutoProxy
public class BeerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerProjectApplication.class, args);
    }
}
