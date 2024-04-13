package org.example.repository;

import org.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


/*
 * Created by Suresh Stalin on 17 / Oct / 2020.
 */

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);

    @Query(value = "select * from user_role where role_id = ?1",nativeQuery = true)
    Object[] getSuperAdmin(long roleId);
}
