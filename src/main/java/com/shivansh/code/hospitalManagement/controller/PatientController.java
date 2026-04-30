package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.*;
import com.shivansh.code.hospitalManagement.repository.PatientRepository;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import com.shivansh.code.hospitalManagement.service.InsuranceServices;
import com.shivansh.code.hospitalManagement.service.PatientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServices patientServices;
    private final InsuranceServices insuranceServices;


    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile(){
        Long patientId = 2L;
        return ResponseEntity.status(HttpStatus.OK).body(patientServices.getPatientById(patientId));
    }

    @PostMapping("/{patientId}/insurance")
    public ResponseEntity<ApiResponse<InsuranceResponseDto>> createInsurance(
            @PathVariable Long patientId,
            @RequestBody InsuranceRequestDto insuranceRequestDto
    ){
        InsuranceResponseDto insurance = insuranceServices.createInsurance(insuranceRequestDto, patientId);

        ApiResponse<InsuranceResponseDto> apiResponse =
                new ApiResponse<>("Insurance Created Successfully", insurance);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/{patientId}/insurance")
    public ResponseEntity<ApiResponse<InsuranceResponseDto>> deleteInsurance(
            @PathVariable Long patientId
    ){

        InsuranceResponseDto insurance = insuranceServices.deleteInsurance(patientId);

        ApiResponse<InsuranceResponseDto> apiResponse =
                new ApiResponse<>("Insurance Deleted Successfully", insurance);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
