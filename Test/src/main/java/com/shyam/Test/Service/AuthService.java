package com.shyam.Test.Service;

import com.shyam.Test.Dto.LoginDto;
import com.shyam.Test.Dto.RegisterDto;
import com.shyam.Test.Entity.User;

import java.util.List;

public interface AuthService {

     String login(LoginDto loginDto);

     String register(RegisterDto registerDto);

     List<User> allUser();
}
