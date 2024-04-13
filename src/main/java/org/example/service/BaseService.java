package org.example.service;

import org.example.message.ResponseMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseService {

    public abstract ResponseMessage findResourceById(String id) throws Exception;

    public abstract ResponseMessage findAll() throws Exception;

    public UserDetails getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        return userDetails;
    }
}
