package com.shyam.Test.Service;

import com.shyam.Test.Dto.LoginDto;
import com.shyam.Test.Dto.RegisterDto;

public interface AuthService {

     String login(LoginDto loginDto);

     String register(RegisterDto registerDto);
}
