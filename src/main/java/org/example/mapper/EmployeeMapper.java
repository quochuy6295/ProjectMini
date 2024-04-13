package org.example.mapper;

import org.example.dto.EmployeeInfo;
import org.example.entity.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 17 / Oct / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface EmployeeMapper {


    EmployeeMapper INSTANCE =  Mappers.getMapper(EmployeeMapper.class);
    Employee employeeInfoToEmployee(EmployeeInfo employeeInfo);
    @InheritInverseConfiguration
    EmployeeInfo employeeToEmployeeInfo(Employee employee);


//    @AfterMapping
//    default void afterMapping(@MappingTarget Employee employee, EmployeeDto employeeDto) {
//        employeeDto.setId(employee.getId());
//        employeeDto.setFlowType(Constants.EMPLOYEE_FLOW_TYPE);
//    }
}
