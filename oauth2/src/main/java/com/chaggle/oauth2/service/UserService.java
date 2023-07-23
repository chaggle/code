package com.chaggle.oauth2.service;

import com.chaggle.oauth2.pojo.User;
import com.chaggle.oauth2.repo.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(user.getPassword())
            );
        }
        throw new UsernameNotFoundException("username not found!");
    }
}
