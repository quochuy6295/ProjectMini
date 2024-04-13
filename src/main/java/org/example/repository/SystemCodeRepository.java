package org.example.repository;

import org.example.entity.SystemCodes;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 24 / Nov / 2020.
 */

public interface SystemCodeRepository extends JpaRepository<SystemCodes, Long> {

    SystemCodes findByCodeType(String codeType);
}
