package org.example.service.bo;

import org.example.entity.User;
import org.example.message.ResponseMessage;
import org.example.repository.UserRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService extends BaseService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        User user = userRepository.findById(Long.parseLong(id)).orElse(null);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(user, "", "");
        return responseMessage;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }
}
