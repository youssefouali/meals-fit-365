package com.mealsfitbackend.services;

import com.mealsfitbackend.dto.UserDTO;
import com.mealsfitbackend.entities.User;

public interface UserService {
    public User findByUsername(String username);

    public void save(UserDTO userDTO);

    public void save(User user);

    public User getCurrentUser();

    public long getCurrentUserId();

    public String getCurrentUserName();

    public boolean hasCurrentUserRole(String role);
}
