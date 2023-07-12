package com.shyam.Test.Controller;


import com.shyam.Test.Dto.JWTAuthResponse;
import com.shyam.Test.Dto.LoginDto;
import com.shyam.Test.Dto.RegisterDto;
import com.shyam.Test.Entity.User;
import com.shyam.Test.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class HomePage {

    private AuthService authService;

    public HomePage(AuthService authService) {
        this.authService = authService;
    }




    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
       // System.out.println(loginDto+" user name= "+loginDto.getUsername()+" pass ="+loginDto.getPassword());

        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
    {
      String response =   authService.register(registerDto);
      return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser()
    {

        return ResponseEntity.ok(authService.allUser());

    }

    // this api for get the list of all registered user
     @GetMapping("/alluser")
        public ResponseEntity<List<User>> allUserList()
        {
           return ResponseEntity.ok(authService.allUser());
        }




    @RequestMapping("/api/page")
    public String TestPage1()
    {
        return " this is test page for url : localhost:8080/api/page";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/api1/page1")
    public String TestPage11()
    {
        return " this is test page1 for url : localhost:8080/api1/page1";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/api1/page2")
    public String TestPage22()
    {
        return " this is test page2 for url : localhost:8080/api1/page2";
    }


    @RequestMapping("/api1/page3")
    public String TestPage3()
    {
        return " this is test page2 for url : localhost:8080/api1/page3";
    }
}
