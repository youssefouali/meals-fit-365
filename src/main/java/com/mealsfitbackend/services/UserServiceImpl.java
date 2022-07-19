package com.mealsfitbackend.services;

import com.mealsfitbackend.Repositories.RoleRepository;
import com.mealsfitbackend.Repositories.UserRepository;
import com.mealsfitbackend.dto.UserDTO;
import com.mealsfitbackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void save(UserDTO userDTO) {
        User user = new User();
        // assign user details to the user object
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        // give user default role of "user"
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        // save user in the database
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName);
    }

    @Override
    public long getCurrentUserId() {
        User user = getCurrentUser();
        return user.getId();
    }

    @Override
    public boolean hasCurrentUserRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
        return hasUserRole;
    }

    @Override
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
