package com.kollab.service;

import com.kollab.dto.UserDto;
import com.kollab.entity.User;

import java.util.List;

public interface UserInterface {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
