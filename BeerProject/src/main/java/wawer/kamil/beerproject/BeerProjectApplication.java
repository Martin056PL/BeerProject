package wawer.kamil.beerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BeerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerProjectApplication.class, args);
    }


}
