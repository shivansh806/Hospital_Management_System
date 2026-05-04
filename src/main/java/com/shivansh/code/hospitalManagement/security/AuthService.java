package com.shivansh.code.hospitalManagement.security;

import com.shivansh.code.hospitalManagement.dto.LoginRequestDto;
import com.shivansh.code.hospitalManagement.dto.LoginResponseDto;
import com.shivansh.code.hospitalManagement.dto.SignUpResopnseDto;
import com.shivansh.code.hospitalManagement.dto.SignUpRequestDto;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.entity.type.AuthProviderType;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public SignUpResopnseDto signUp(SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByUsername(signUpRequestDto.getUsername())
                .orElse(null);

        if(user != null) throw new IllegalArgumentException("User already Exits");

        user = userRepository.save(User.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build()
        );
        return new SignUpResopnseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType authProviderType = authUtil.getAuthProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.getProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, authProviderType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null){
            //sign part
            String username = authUtil.getUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = userRepository.save(
                    User.builder()
                    .username(username)
                    .providerId(providerId)
                    .providerType(authProviderType)
                    .password(null)
                    .build()
            );
        }else if(user != null){
            if( email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("This email is already registered with provider "+emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResponseDto);
    }
}
