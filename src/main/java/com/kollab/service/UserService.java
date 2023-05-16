package com.kollab.service;

import com.kollab.dto.user.UserDto;
import com.kollab.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
