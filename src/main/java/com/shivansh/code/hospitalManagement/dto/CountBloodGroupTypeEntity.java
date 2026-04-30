package com.shivansh.code.hospitalManagement.dto;

import com.shivansh.code.hospitalManagement.entity.type.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountBloodGroupTypeEntity {
    private BloodGroup bloodGroup;
    private Long id;
}
