package com.bats.entitylistener.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bats.entitylistener.entity.Employee;
import com.bats.entitylistener.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController
{
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.GET, path="/search")
	public List<Employee> getEmployees(@RequestParam(required=false) List<Integer> id) {
		return employeeService.findEmployeesByIds(id);
	}
}

