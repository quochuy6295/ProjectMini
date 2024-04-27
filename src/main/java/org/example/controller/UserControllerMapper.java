package org.example.controller;

import org.example.dto.UserDto;
import org.example.service.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class UserControllerMapper {

    private final UserMapperService userMapperService;

    @Autowired
    public UserControllerMapper(UserMapperService userMapperService) {
        this.userMapperService = userMapperService;
    }

    public List<UserDto> fetchUsers() {
        return userMapperService.getUsers();
    }

    public UserDto fetchUserById(Long id) {
        return userMapperService.getUserById(id);
    }

    public void createUser(String name, String email, String mobile) {
        try {
            if (Objects.equals("", name)) {
                throw new Exception("Empty mandatory field - name");
            }
            if ("".equals(email)) {
                throw new Exception("Empty mandatory field - email");
            }
            if ("".equals(mobile)) {
                throw new Exception("Empty mandatory field - mobile");
            }

            userMapperService.create(name, email, mobile);
            System.out.println("User: [" + name + ", " + email + ", " + mobile + "] -> Success: OK");
        } catch (Exception e) {
            System.out.println("Error --> " + e.getMessage());
        }
    }

    public void updateUser(Long id, String name, String email, String mobile) {

        try {
            if (id == null) {
                throw new Exception("Empty mandatory field - id");
            }
            if ("".equals(name)) {
                throw new Exception("Empty mandatory field - name");
            }
            if ("".equals(email)) {
                throw new Exception("Empty mandatory field - email");
            }
            if ("".equals(mobile)) {
                throw new Exception("Empty mandatory field - mobile");
            }

            userMapperService.update(id, name, email, mobile);
            System.out.println("User: [" + id + ", " + name + ", " + email + ", " + mobile + "] -> Update: OK");

        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            userMapperService.delete(id);
            System.out.println("User ID: [" + id + "] -> Delete: OK");

        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
        }
    }
}
