package org.example.service.bo;

import org.example.dto.VendorInfo;
import org.example.entity.Vendor;
import org.example.exception.ResourceNotFoundException;
import org.example.mapper.VendorMapper;
import org.example.message.ResponseMessage;
import org.example.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */


@Service
public class VendorService extends UserService {

    private final VendorRepository repository;

    @Autowired
    VendorService(VendorRepository repository) {
        this.repository = repository;
    }


    @Transactional
    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        Vendor vendor = null;
        if (id.contains("VEN")) {
            vendor = repository.findByVendorCode(id);
        } else {
            vendor = repository.findById(Long.parseLong(id)).orElse(null);
        }
        ResponseMessage responseMessage = new ResponseMessage();
        if (vendor != null) {
            VendorInfo vendorInfo = VendorMapper.INSTANCE.vendorToVendorInfo(vendor);
            responseMessage.setResponseClassType(vendorInfo);
        } else {
            throw new ResourceNotFoundException("Vendor not found");
        }
        return responseMessage;
    }
}
