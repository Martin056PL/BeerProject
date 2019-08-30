package wawer.kamil.beerproject.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wawer.kamil.beerproject.domain.User;

import java.util.Date;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login (@RequestBody User user){
        long currentTimeMiliseconds = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles","user")
                .setIssuedAt(new Date(currentTimeMiliseconds))
                .setExpiration(new Date(currentTimeMiliseconds + 600000))
                .signWith(SignatureAlgorithm.HS512,user.getPassword())
                .compact();
    }


}
