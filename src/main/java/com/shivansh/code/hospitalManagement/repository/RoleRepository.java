package com.shivansh.code.hospitalManagement.repository;

import com.shivansh.code.hospitalManagement.entity.Role;
import com.shivansh.code.hospitalManagement.entity.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleType role);
}
