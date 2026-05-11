package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.*;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import com.shivansh.code.hospitalManagement.service.InsuranceServices;
import com.shivansh.code.hospitalManagement.service.PatientServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServices patientServices;
    private final InsuranceServices insuranceServices;


    @GetMapping("/my-profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(patientServices.getPatientById(user.getId()));
    }

    @PostMapping("/my-insurance")
    public ResponseEntity<ApiResponse<InsuranceResponseDto>> createInsurance(
            @Valid
            @RequestBody InsuranceRequestDto insuranceRequestDto,
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        InsuranceResponseDto insurance = insuranceServices.createInsurance(insuranceRequestDto, user.getId());

        ApiResponse<InsuranceResponseDto> apiResponse =
                new ApiResponse<>("Insurance Created Successfully", insurance);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/my-insurance")
    public ResponseEntity<ApiResponse<InsuranceResponseDto>> deleteInsurance(
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        InsuranceResponseDto insurance = insuranceServices.deleteInsurance(user.getId());

        ApiResponse<InsuranceResponseDto> apiResponse =
                new ApiResponse<>("Insurance Deleted Successfully", insurance);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
