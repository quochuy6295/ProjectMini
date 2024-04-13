package org.example.service.bo;

import org.example.common.CodeGenerator;
import org.example.common.staticdata.CodeType;
import org.example.common.staticdata.Constants;
import org.example.common.staticdata.ROLES;
import org.example.common.staticdata.UserType;
import org.example.dto.OrganizationInfo;
import org.example.dto.UserRoleInfo;
import org.example.entity.Organization;
import org.example.entity.Role;
import org.example.exception.DuplicateKeyFoundException;
import org.example.mapper.OrganizationMapper;
import org.example.message.ResponseMessage;
import org.example.repository.OrganizationRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

@Service
public class OrganizationService extends BaseService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseMessage save(OrganizationInfo organizationInfo) throws Exception {
        ResponseMessage response = roleService.findResourceById(Constants.SUPER_ADMIN_ROLE_ID);
        UserRoleInfo userRoleInfo = (UserRoleInfo) response.getResponseClassType();
        if (userRoleInfo != null) {
            throw new DuplicateKeyFoundException
                    ("The Super Admin already created: can't create more than one Super Admin Role");
        }
        Organization organization = OrganizationMapper.INSTANCE
                .organizationInfoToOrganization(organizationInfo);
        String orgCode = codeGenerator.newCode(CodeType.ORG_CODE);
        organization.setOrgCode(orgCode);
        organization.getUser().setUserType(UserType.OWNER.name());
        organization.getUser().setPassword(passwordEncoder.encode(organization.getUser().getPassword()));
        List<Role> roles = new ArrayList<>();
        Role role = roleService.findByName(ROLES.SUPER_ADMIN_ROLE.name());
        roles.add(role);
        organization.getUser().setRoles(roles);
        Organization newOrganization = organizationRepository.save(organization);

        OrganizationInfo newOrganizationInfo = OrganizationMapper.INSTANCE
                .organizationToOrganizationInfo(newOrganization);
        ResponseMessage responseMessage = ResponseMessage.withResponseData(newOrganizationInfo,
                Constants.SUCCESS_STATUS,Constants.INFO_TYPE);
        return responseMessage;
    }

    @Override
    public ResponseMessage findResourceById(String id) throws Exception {
        return null;
    }

    @Override
    public ResponseMessage findAll() throws Exception {
        return null;
    }
}
