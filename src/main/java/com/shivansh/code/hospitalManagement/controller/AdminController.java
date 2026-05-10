package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.DoctorRequestDto;
import com.shivansh.code.hospitalManagement.dto.DoctorResponseDto;
import com.shivansh.code.hospitalManagement.dto.PatientResponseDto;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AdminService;
import com.shivansh.code.hospitalManagement.service.PatientServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientServices patientServices;
    private final AdminService adminService;

    @GetMapping("/patients")
    public ResponseEntity<Page<PatientResponseDto>> findAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "Size", defaultValue = "2") Integer pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(patientServices.getAllPatients(pageNumber, pageSize));
    }

    @PostMapping("/createDoctor")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> addDoctor(@Valid @RequestBody DoctorRequestDto doctorRequestDto){
        DoctorResponseDto response = adminService.createDoctor(doctorRequestDto);
        ApiResponse<DoctorResponseDto> apiResponse =
                new ApiResponse<>("Doctor Created Successfully..", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
