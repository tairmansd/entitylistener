package com.bats.entitylistener.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bats.entitylistener.dao.EmployeeDAO;
import com.bats.entitylistener.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
	@Autowired
	private EmployeeDAO dao;

	@Override
	public List<Employee> findEmployeesByIds(List<Integer> ids)
	{
		List<Employee> result = new ArrayList<>();
		
		if(ids != null && ids.size() > 0) {
			result.addAll(dao.findByEmpNoIn(ids));
		} else {
			result.addAll(dao.findAll());
		}
		
		return result;
	}
	
}
