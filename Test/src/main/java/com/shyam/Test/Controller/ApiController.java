package com.shyam.Test.Controller;

import com.shyam.Test.Entity.User;
import com.shyam.Test.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ApiController {

    private AuthService authService;

    public ApiController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/users")
    public ResponseEntity<List<User>> getAllUsers()
    {

        return ResponseEntity.ok(authService.allUser());

    }


}
