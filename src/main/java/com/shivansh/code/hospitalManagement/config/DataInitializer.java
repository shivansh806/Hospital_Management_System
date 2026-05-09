package com.shivansh.code.hospitalManagement.config;

import com.shivansh.code.hospitalManagement.entity.Role;
import com.shivansh.code.hospitalManagement.entity.User;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import com.shivansh.code.hospitalManagement.repository.RoleRepository;
import com.shivansh.code.hospitalManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.shivansh.code.hospitalManagement.entity.type.PermissionType;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {
        return args -> {

            // CREATE ROLES
            for (RoleType roleType : RoleType.values()) {

                if (roleRepository.findByRole(roleType).isEmpty()) {

                    Role role = Role.builder()
                            .role(roleType)
                            .build();

                    roleRepository.save(role);
                }
            }

            // ASSIGN PERMISSIONS

            // ADMIN
            Role adminRole = roleRepository
                    .findByRole(RoleType.ROLE_ADMIN)
                    .orElseThrow();

            adminRole.setPermissions(Set.of(
                    PermissionType.DOCTOR_READ,
                    PermissionType.DOCTOR_WRITE,
                    PermissionType.PATIENT_READ,
                    PermissionType.PATIENT_WRITE
            ));

            roleRepository.save(adminRole);

            // DOCTOR
            Role doctorRole = roleRepository
                    .findByRole(RoleType.ROLE_DOCTOR)
                    .orElseThrow();

            doctorRole.setPermissions(Set.of(
                    PermissionType.PATIENT_READ,
                    PermissionType.APPOINTMENT_READ,
                    PermissionType.APPOINTMENT_UPDATE
            ));

            roleRepository.save(doctorRole);

            // PATIENT
            Role patientRole = roleRepository
                    .findByRole(RoleType.ROLE_PATIENT)
                    .orElseThrow();

            patientRole.setPermissions(Set.of(
                    PermissionType.APPOINTMENT_CREATE,
                    PermissionType.APPOINTMENT_READ,
                    PermissionType.INSURANCE_READ
            ));

            roleRepository.save(patientRole);

            // CREATE ADMIN USER
            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
