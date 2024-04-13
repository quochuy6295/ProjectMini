package org.example.mapper;

import org.example.dto.AuthenticationResponseInfo;
import org.example.entity.JwtToken;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*
 * Created by Suresh Stalin on 10 / Nov / 2020.
 */

@Mapper(implementationPackage = "mapper.impl")
public interface JwtResponseMapper {

    JwtResponseMapper INSTANCE = Mappers.getMapper(JwtResponseMapper.class);

    JwtToken authResponseToJwt(AuthenticationResponseInfo authenticationResponseDTO);

    @InheritInverseConfiguration
    AuthenticationResponseInfo jwtToAuthResponse(JwtToken jwtToken);
}
