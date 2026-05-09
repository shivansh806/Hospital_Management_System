package com.shivansh.code.hospitalManagement.dto;

import com.shivansh.code.hospitalManagement.entity.type.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String email;
    private GenderType gender;
}
