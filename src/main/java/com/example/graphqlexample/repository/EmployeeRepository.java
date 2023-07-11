package com.example.graphqlexample.repository;

import com.example.graphqlexample.dto.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends
        CrudRepository<Employee, Integer>,
        JpaSpecificationExecutor<Employee>,
        PagingAndSortingRepository<Employee, Integer> {
}