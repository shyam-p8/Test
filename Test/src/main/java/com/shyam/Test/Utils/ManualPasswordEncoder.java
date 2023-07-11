package com.shyam.Test.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ManualPasswordEncoder {

    public static void main(String[] args)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("shyam"));
        System.out.println(passwordEncoder.encode("shyam1"));
    }
}
