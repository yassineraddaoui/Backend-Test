package com.todo.user;


import java.util.List;

public interface UserService {
    Long createUser(UserDto user);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

}
