package com.example.graphqlexample.dto.input;

import lombok.Data;

@Data
public class EmployeeInput {
    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private int age;
    private int organizationId;
    private int departmentId;
}
