package com.clinic.dentistry.service;

import com.clinic.dentistry.models.*;
import com.clinic.dentistry.repo.OutpatientCardRepository;
import com.clinic.dentistry.repo.UserRepository;
import com.fasterxml.jackson.core.Base64Variant;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User findUser(Long id){
        return userRepository.findUserById(id);
    }

    public List<User> findAllActiveUsers(){
        return userRepository.findByActiveTrue();
    }

    public User getByLogin(String username) {
        return userRepository.findUserByUsername(username);
    }
}
