package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.LoginRequestDto;
import com.shivansh.code.hospitalManagement.dto.LoginResponseDto;
import com.shivansh.code.hospitalManagement.dto.SignUpResopnseDto;
import com.shivansh.code.hospitalManagement.dto.SignUpRequestDto;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginRes = authService.login(loginRequestDto);
        ApiResponse<LoginResponseDto> apiResponse =
                new ApiResponse<>("Login Successfully", loginRes);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResopnseDto>>
          signup(@RequestBody SignUpRequestDto signupRequestDto){
        SignUpResopnseDto newSignUp =
                authService.signUp(signupRequestDto);
        ApiResponse<SignUpResopnseDto> apiResponse =
                new ApiResponse<>("SignUp Successfully.", newSignUp);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
