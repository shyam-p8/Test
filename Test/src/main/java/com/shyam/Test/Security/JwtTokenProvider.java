package com.shyam.Test.Security;


import com.shyam.Test.Entity.User;
import com.shyam.Test.Exception.BlogAPIException;
import com.shyam.Test.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long expireTimeValue;
    private final UserRepository userRepository;

    public JwtTokenProvider(UserRepository userRepository) {
            this.userRepository = userRepository;
            }

            //Generate JWT Token
    public String generateToken(Authentication authentication)  {

            String username = authentication.getName();
            Optional<User> user = userRepository.findByEmail(username);
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + expireTimeValue);

            String token = Jwts.builder()
            .setSubject(username)
            .claim("Roles",user.get().getRoles().stream().collect(Collectors.toSet()))
            .setIssuedAt(currentDate)
            .setExpiration(expireDate)
            .signWith(key())
            .compact();
        System.out.println(getUsername(token));
            return token;

            }

            private Key key()   {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
            }

            //get username from JWT token
            public String getUsername(String token) {
            Claims claims =Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody();
            String username = claims.getSubject();
            return username;
            }

            //Validate the token
            public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);

            return true;
        }catch (MalformedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT Token");
        }catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        }catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
        }catch (IllegalArgumentException ex)    {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT claims String is empty.");
        }

    }

}
