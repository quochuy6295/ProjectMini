package org.example.repository;

import org.example.entity.BaseObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/*
 * Created by Suresh Stalin on 02 / Nov / 2020.
 */


public interface AbstractBaseRepository<T extends BaseObject, ID extends Serializable>
        extends JpaRepository<T, ID> {

}
