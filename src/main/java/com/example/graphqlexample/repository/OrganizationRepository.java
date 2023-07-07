package com.example.graphqlexample.repository;

import com.example.graphqlexample.dto.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends
        CrudRepository<Organization, Integer>, JpaSpecificationExecutor<Organization> {
}