package com.clinic.dentistry.service;

import com.clinic.dentistry.models.User;
import com.clinic.dentistry.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers(){
        return userRepository.findByActiveTrueOrActiveFalseOrderById();
    }
    public User findUser(Long id){
        return userRepository.findUserById(id);
    }

}
