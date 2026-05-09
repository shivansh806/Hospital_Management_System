package com.shivansh.code.hospitalManagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DoctorRequestDto {
    private String username;
    private String password;

    private String name;
    private String email;
    private String specialization;
}
