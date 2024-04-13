package org.example.controller;

import org.example.dto.OrganizationInfo;
import org.example.message.ResponseMessage;
import org.example.service.bo.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

@RestController
@RequestMapping("api/public/orgs")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> saveOrg(@RequestBody OrganizationInfo organizationInfo) throws Exception{
        ResponseMessage responseMessage = organizationService.save(organizationInfo);
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }
}
