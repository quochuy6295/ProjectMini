package org.example.service;

import org.example.dto.UserDto;

import java.util.List;

public interface UserMapperService {

    List<UserDto> getUsers();

    UserDto getUserById(Long id);

    void create(String name, String email, String mobile);

    void update(Long id, String name, String email, String mobile);

    void delete(Long id);
}
