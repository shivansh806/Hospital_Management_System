package com.shivansh.code.hospitalManagement.controller;

import com.shivansh.code.hospitalManagement.dto.AppointmentResponseDto;
import com.shivansh.code.hospitalManagement.dto.DoctorResponseDto;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.response.ApiResponse;
import com.shivansh.code.hospitalManagement.service.AppointmentServices;
import com.shivansh.code.hospitalManagement.service.DoctorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentServices appointmentServices;
    private final DoctorServices doctorServices;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(appointmentServices.getAllAppointmentsOfDoctor(user.getId()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> getDoctorProfile(
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        DoctorResponseDto doctorProfile =
                doctorServices.getDoctorProfile(user.getId());
        ApiResponse<DoctorResponseDto> apiResponse =
                new ApiResponse<>("Doctor Profile fetched successfully.", doctorProfile);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    @GetMapping("/test")
    public String test(){
        return "Doctor Controller is working fine..";
    }
}
