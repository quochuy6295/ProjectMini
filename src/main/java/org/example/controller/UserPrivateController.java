package org.example.controller;

import org.example.common.staticdata.UserType;
import org.example.dto.CustomerInfo;
import org.example.dto.EmployeeInfo;
import org.example.dto.VendorInfo;
import org.example.message.ResponseMessage;
import org.example.service.bo.CustomerService;
import org.example.service.bo.EmployeeService;
import org.example.service.bo.RegistrationService;
import org.example.service.bo.VendorService;
import org.example.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 * Created by Suresh Stalin on 17 / Nov / 2020.
 */

@RestController
@RequestMapping("api/private/users")
@Validated
public class UserPrivateController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final VendorService vendorService;
    private final RegistrationService registrationService;
    private final UserValidator userValidator;

    @Autowired
    public UserPrivateController(CustomerService customerService,
                                 EmployeeService employeeService,
                                 VendorService vendorService,
                                 RegistrationService registrationService,
                                 UserValidator userValidator) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.vendorService = vendorService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }


    @PostMapping("/employees") // http://localhost:9091/api/public/users/employees
    public ResponseEntity<ResponseMessage<?>> saveEmployee(@Valid @RequestBody EmployeeInfo requestBody) throws Exception {
        requestBody.setType(UserType.EMPLOYEE.name());
        userValidator.validate(requestBody);
        ResponseMessage responseMessage = registrationService.doRegistration(requestBody);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }

    @PostMapping("/customers") // http://localhost:9091/api/public/users/customers
    public ResponseEntity<ResponseMessage<?>> saveCustomer(@Valid @RequestBody CustomerInfo requestBody) throws Exception {
        requestBody.setType(UserType.CUSTOMER.name());
        userValidator.validate(requestBody);
        ResponseMessage responseMessage = registrationService.doRegistration(requestBody);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }

    @PostMapping("/vendors")
    public ResponseEntity<ResponseMessage<?>> saveVendor(@Valid @RequestBody VendorInfo requestBody) throws Exception {
        requestBody.setType(UserType.VENDOR.name());
        userValidator.validate(requestBody);
        ResponseMessage responseMessage = registrationService.doRegistration(requestBody);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ResponseMessage<?>> viewCustomer(@PathVariable String id) throws Exception {
        ResponseMessage responseMessage = customerService.findResourceById(id);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ResponseMessage<?>> viewEmployee(@PathVariable String id) throws Exception {
        ResponseMessage responseMessage = employeeService.findResourceById(id);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/vendors/{id}")
    public ResponseEntity<ResponseMessage<?>> viewVendor(@PathVariable String id) throws Exception {
        ResponseMessage responseMessage = vendorService.findResourceById(id);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.OK);
    }

}
