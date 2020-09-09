package wawer.kamil.beerproject;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEncryptableProperties
public class BeerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerProjectApplication.class, args);
    }


}
