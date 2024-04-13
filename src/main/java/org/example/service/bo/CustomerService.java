package org.example.service.bo;

import org.example.dto.CustomerInfo;
import org.example.entity.Customer;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.CustomerMapper;
import org.example.message.ResponseMessage;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService extends UserService {
    private final CustomerRepository repository;

    @Autowired
    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        Customer customer = null;
        if(id.contains("CUS")) {
            customer = repository.findByCustomerCode(id);
        }
        else {
            customer = repository.findById(Long.parseLong(id)).orElse(null);
        }
        ResponseMessage responseMessage = new ResponseMessage();
        if (customer != null) {
            CustomerInfo customerDTO = CustomerMapper.INSTANCE.customerToCustomerInfo(customer);
            responseMessage.setResponseClassType(customerDTO);
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }
        return responseMessage;
    }
}
