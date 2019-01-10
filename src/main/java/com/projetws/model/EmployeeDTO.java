package com.projetws.model;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmployeeDTO
{
    ModelMapper modelMapper = new ModelMapper();
    
    @NotNull
    private String firstName;
    
    @NotNull	
    private String lastName;

    @JsonIgnore
    private Department department;

    @NotNull
    private String departmentName;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
	
	public String getDepartmentName()
	{
		return department.getDepartmentName();
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}
	
}