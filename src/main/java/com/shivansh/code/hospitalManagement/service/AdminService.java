package com.shivansh.code.hospitalManagement.service;

import com.shivansh.code.hospitalManagement.dto.DoctorRequestDto;
import com.shivansh.code.hospitalManagement.dto.DoctorResponseDto;
import com.shivansh.code.hospitalManagement.entity.Doctor;
import com.shivansh.code.hospitalManagement.entity.Role;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import com.shivansh.code.hospitalManagement.repository.DoctorRepository;
import com.shivansh.code.hospitalManagement.repository.RoleRepository;
import com.shivansh.code.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DoctorResponseDto createDoctor(DoctorRequestDto request){

        Role doctorRole = roleRepository.findByRole(RoleType.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Doctor role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .roles(Set.of(doctorRole))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setSpecialization(request.getSpecialization());

        doctor.setUser(user);
        doctorRepository.save(doctor);

      return modelMapper.map(doctor, DoctorResponseDto.class);
    }
}
