package com.shivansh.code.hospitalManagement.security;

import com.shivansh.code.hospitalManagement.dto.LoginRequestDto;
import com.shivansh.code.hospitalManagement.dto.LoginResponseDto;
import com.shivansh.code.hospitalManagement.dto.SignUpResopnseDto;
import com.shivansh.code.hospitalManagement.dto.SingUpRequestDto;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public SignUpResopnseDto signUp(SingUpRequestDto signUpRequestDto){
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
}