package com.ecommerce.ecommerce.Config;

import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found ");
        }
        CustomUserDetail customUserDetail=new CustomUserDetail(user);
        return customUserDetail;
    }
}
