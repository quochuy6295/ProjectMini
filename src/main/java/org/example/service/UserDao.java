package org.example.service;
import org.example.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    List<UserDto> getUsers();

    UserDto getUserById(@Param("id") Long id);

    void create(@Param("name") String name,
                @Param("email") String email,
                @Param("mobile") String mobile);

    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("email") String email,
                @Param("mobile") String mobile);

    void delete(@Param("id") Long id);
}
