package com.shivansh.code.hospitalManagement.response;

import com.shivansh.code.hospitalManagement.dto.LoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
}
