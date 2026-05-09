package com.shivansh.code.hospitalManagement.entity;

import com.shivansh.code.hospitalManagement.entity.type.PermissionType;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType role;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<PermissionType> permissions = new HashSet<>();
}
