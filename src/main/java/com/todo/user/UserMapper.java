package com.todo.user;

import com.todo.company.Company;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .company(Company.builder()
                        .id(userDto.getCompanyId())
                        .build())
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .companyId(user.getCompany().getId())
                .role(user.getRole().toString())
                .build();
    }
}
