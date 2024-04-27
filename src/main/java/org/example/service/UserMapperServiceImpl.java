package org.example.service;

import org.example.dto.UserDto;
import org.example.repository.UserDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMapperServiceImpl implements UserMapperService{
    private final UserDaoMapper userDao;

    public UserMapperServiceImpl(UserDaoMapper userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public UserDto getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void create(String name, String email, String mobile) {
        userDao.create(name, email, mobile);
    }

    @Override
    public void update(Long id, String name, String email, String mobile) {
        userDao.update(id, name, email, mobile);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
