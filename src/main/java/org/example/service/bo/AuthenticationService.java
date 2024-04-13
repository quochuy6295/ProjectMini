package org.example.service.bo;

import io.jsonwebtoken.Claims;
import org.example.common.Utils;
import org.example.common.staticdata.TokenType;
import org.example.dto.AuthenticationResponseInfo;
import org.example.dto.GrandAuthorityRoleInfo;
import org.example.entity.JwtToken;
import org.example.entity.User;
import org.example.mapper.JwtResponseMapper;
import org.example.repository.JwtTokenRepository;
import org.example.repository.UserRepository;
import org.example.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(emailId);
        GrandAuthorityRoleInfo grandAuthorityRoleSuperAdmin = new GrandAuthorityRoleInfo("SUPER_ADMIN");
        GrandAuthorityRoleInfo grandAuthorityRoleEmployee = new GrandAuthorityRoleInfo("ROLE_EMPLOYEE");
        List<GrandAuthorityRoleInfo> authorityRoleInfos = new ArrayList<>();
        authorityRoleInfos.add(grandAuthorityRoleSuperAdmin);
        authorityRoleInfos.add(grandAuthorityRoleEmployee);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), authorityRoleInfos);
        return userDetails;
    }

    public JwtToken saveJwt(AuthenticationResponseInfo authenticationResponseDto) {

        JwtToken jwtToken = JwtResponseMapper.INSTANCE.authResponseToJwt(authenticationResponseDto);
        JwtToken jwtTokenResponse = jwtTokenRepository.save(jwtToken);
        return jwtTokenResponse;
    }

    public AuthenticationResponseInfo generateAuthResponse(UserDetails userDetails, TokenType tokenType) {

        JwtToken jwtToken = jwtTokenRepository.findByUserName(userDetails.getUsername());
        AuthenticationResponseInfo authenticationResponseDTO = null;
        if(tokenType.equals(TokenType.ACCESS_TOKEN)) {
            String accessToken = jwtUtils.generateAccessToken(userDetails);
            final Claims accessclaims = jwtUtils.extractAllClaims(accessToken);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            final Claims refreshClaims = jwtUtils.extractAllClaims(refreshToken);
            authenticationResponseDTO = new AuthenticationResponseInfo();
            authenticationResponseDTO.setAccessToken(accessToken);
            authenticationResponseDTO.setRefreshToken(refreshToken);
            authenticationResponseDTO.setUserName(userDetails.getUsername());
            authenticationResponseDTO.setAccessTokenExpiration(Utils.convertToLocalDateTime(accessclaims.getExpiration().getTime()));
            authenticationResponseDTO.setRefreshTokenExpiration(Utils.convertToLocalDateTime(refreshClaims.getExpiration().getTime()));
            if (jwtToken != null) {
                authenticationResponseDTO.setId(jwtToken.getId());
            }
        }
        if(tokenType.equals(TokenType.REFRESH_TOKEN)) {
            String accessToken = jwtUtils.generateAccessToken(userDetails);
            final Claims accessclaims = jwtUtils.extractAllClaims(accessToken);
            authenticationResponseDTO = new AuthenticationResponseInfo();
            authenticationResponseDTO.setAccessToken(accessToken);
            authenticationResponseDTO.setRefreshToken(jwtToken.getRefreshToken());
            authenticationResponseDTO.setUserName(userDetails.getUsername());
            authenticationResponseDTO.setAccessTokenExpiration(Utils.convertToLocalDateTime(accessclaims.getExpiration().getTime()));
            authenticationResponseDTO.setRefreshTokenExpiration(jwtToken.getRefreshTokenExpiration());
            authenticationResponseDTO.setId(jwtToken.getId());
        }

        return authenticationResponseDTO;
    }
}
