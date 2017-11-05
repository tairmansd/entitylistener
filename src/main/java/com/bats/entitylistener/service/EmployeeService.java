package com.bats.entitylistener.service;

import java.util.List;

import com.bats.entitylistener.entity.Employee;

public interface EmployeeService
{
	List<Employee> findEmployeesByIds(List<Integer> ids);	
}
