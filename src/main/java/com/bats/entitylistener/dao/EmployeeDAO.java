package com.bats.entitylistener.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bats.entitylistener.entity.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>
{
	List<? extends Employee> findByEmpNoIn(List<Integer> ids);	
}
