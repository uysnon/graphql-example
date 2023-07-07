package com.example.graphqlexample.repository;

import com.example.graphqlexample.dto.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends
        CrudRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
}