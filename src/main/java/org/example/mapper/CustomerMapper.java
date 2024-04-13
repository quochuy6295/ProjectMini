package org.example.mapper;

import org.example.dto.CustomerInfo;
import org.example.entity.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 18 / Oct / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface CustomerMapper {

    CustomerMapper INSTANCE =  Mappers.getMapper(CustomerMapper.class);
    Customer customerInfoToCustomer(CustomerInfo customerInfo);
    @InheritInverseConfiguration
    CustomerInfo customerToCustomerInfo(Customer customer);
}
