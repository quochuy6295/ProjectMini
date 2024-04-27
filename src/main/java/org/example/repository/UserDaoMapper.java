package org.example.repository;

import org.example.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDaoMapper {

    List<UserDto> getUsers();

    UserDto getUserById(@Param("id") Long id);

    void create(@Param("name") String name, @Param("email") String email, @Param("mobile") String mobile);

    void update(@org.apache.ibatis.annotations.Param("id") Long id,
                @org.apache.ibatis.annotations.Param("name") String name,
                @org.apache.ibatis.annotations.Param("email") String email,
                @org.apache.ibatis.annotations.Param("mobile") String mobile);

    void delete(@org.apache.ibatis.annotations.Param("id") Long id);
}
