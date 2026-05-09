package com.shivansh.code.hospitalManagement.config;

import com.shivansh.code.hospitalManagement.entity.Role;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import com.shivansh.code.hospitalManagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles(){
        return args -> {
            for(RoleType roleType: RoleType.values()){

                if(roleRepository.findByRole(roleType).isEmpty()){
                    roleRepository.save(
                            Role.builder()
                                    .role(roleType)
                                    .build()
                    );
                }
            }
        };
    }
}
