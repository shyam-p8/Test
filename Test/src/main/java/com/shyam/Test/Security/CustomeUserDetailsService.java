package com.shyam.Test.Security;

import com.shyam.Test.Entity.User;
import com.shyam.Test.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

   private UserRepository userRepository;

    public CustomeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // find the user from database and load
       User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user not found for username: "+ username));
     // user have set of roles so convert roles into authorities
        Set<GrantedAuthority> authorities = user.getRoles().
                stream().
                map((role)-> new SimpleGrantedAuthority(role.getName())).
                collect(Collectors.toSet());

        //now pass user id, password and authorities to new object of UserDetails for return from this method
        //passwordEncoder.encode(user.getPassword())

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
