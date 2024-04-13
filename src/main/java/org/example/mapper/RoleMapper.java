package org.example.mapper;
import org.example.dto.RoleInfo;
import org.example.entity.Employee;
import org.example.entity.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 19 / Oct / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface RoleMapper {

    RoleMapper INSTANCE =  Mappers.getMapper(RoleMapper.class);
    Employee roleInfoToRole(RoleInfo roleInfo);
    @InheritInverseConfiguration
    RoleInfo roleToRoleInfo(Role role);

}
