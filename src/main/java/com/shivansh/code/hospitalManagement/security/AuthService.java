package com.shivansh.code.hospitalManagement.security;

import com.shivansh.code.hospitalManagement.dto.*;
import com.shivansh.code.hospitalManagement.entity.Patient;
import com.shivansh.code.hospitalManagement.entity.Role;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.entity.type.AuthProviderType;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import com.shivansh.code.hospitalManagement.repository.RoleRepository;
import com.shivansh.code.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = authUtil.generateAccessToken(user);
        String refreshToken = authUtil.generateRefreshToken(user);

        return new LoginResponseDto(accessToken, refreshToken, user.getId());
    }

    public SignUpResopnseDto signUp(SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByUsername(signUpRequestDto.getUsername())
                .orElse(null);

        if(user != null) throw new IllegalArgumentException("User already Exits");

        Role patientRole = roleRepository
                .findByRole(RoleType.ROLE_PATIENT)
                .orElseThrow(()->new IllegalArgumentException("Role not found"));

        user = userRepository.save(User.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .roles(Set.of(patientRole))
                .build()
        );

        Patient patient = Patient.builder()
                .user(user)
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getUsername())
                .build();
        patientRepository.save(patient);

        return new SignUpResopnseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public LoginResponseDto handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType authProviderType = authUtil.getAuthProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.getProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, authProviderType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        User emailUser = userRepository.findByUsername(email).orElse(null);


        if(user == null && emailUser == null){
            //sign part
            String username = authUtil.getUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

            Role patientRole = roleRepository
                    .findByRole(RoleType.ROLE_PATIENT)
                    .orElseThrow(() -> new RuntimeException("Role not found"));


            user = userRepository.save(
                    User.builder()
                    .username(username)
                    .providerId(providerId)
                    .providerType(authProviderType)
                    .password(null)
                            .roles(Set.of(patientRole))
                    .build()
            );

            Patient patient = Patient.builder()
                    .user(user)
                    .name(name)
                    .email(email)
                    .build();
            patientRepository.save(patient);

        }else if(user != null){
            if(user.getRoles() == null || user.getRoles().isEmpty()){
                Role patientRole = roleRepository
                        .findByRole(RoleType.ROLE_PATIENT)
                        .orElseThrow();

                user.setRoles(Set.of(patientRole));
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("This email is already registered with provider "+emailUser.getProviderType());
        }

        return new LoginResponseDto(authUtil.generateAccessToken(user), authUtil.generateRefreshToken(user), user.getId());
    }

    public RefreshTokenResponseDto refreshAccessToken(
            RefreshTokenRequestDto request){

        String refreshToken = request.getRefreshToken();
        String username = authUtil.getUsernameFromToken(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newAccessToken = authUtil.generateAccessToken(user);
        return new RefreshTokenResponseDto(newAccessToken);
    }
}
