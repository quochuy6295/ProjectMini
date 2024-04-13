package org.example.service.bo;

import org.example.dto.EmployeeInfo;
import org.example.entity.Employee;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.EmployeeMapper;
import org.example.message.ResponseMessage;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */

@Service
public class EmployeeService extends UserService{

    private final EmployeeRepository repository;

    @Autowired
    EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseMessage findResourceById(String id) throws Exception {

        UserDetails userDetails = getContext();

        Employee employee = null;
        if(id.contains("EMP")) {
            employee = repository.findByEmployeeCode(id);
        }
        else {
            employee = repository.findById(Long.parseLong(id)).orElse(null);
        }
        ResponseMessage responseMessage = new ResponseMessage();
        if (employee != null) {
            EmployeeInfo employeeDTO = EmployeeMapper.INSTANCE.employeeToEmployeeInfo(employee);
            responseMessage.setResponseClassType(employeeDTO);
        } else {
            throw new ResourceNotFoundException("Employee not found");
        }
        return responseMessage;
    }
}
