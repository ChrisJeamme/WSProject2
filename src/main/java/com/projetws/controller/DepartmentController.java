package com.projetws.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.Department;
import com.projetws.model.DepartmentRepository;
import com.projetws.model.Location;
import com.projetws.model.LocationRepository;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@Api(value = "departments")
@RequestMapping("/department")
public class DepartmentController
{

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	LocationRepository locationRepository;

	@RequestMapping("/all")
	public String getAllDepartment(Model m)
	{
		List<Department> departments = departmentRepository.findAll();
		List<Location> locations = locationRepository.findAll();
		
		m.addAttribute("departments", departments);
		m.addAttribute("locations", locations);
		return "departments";
	}
	
	@RequestMapping(value="/updateDepartment", method=RequestMethod.POST)
	public String updateDepartment(@RequestParam("departmentId") Long departmentId, 
								   @RequestParam("departmentName") String departmentName, 
								   @RequestParam("managerId") Long managerId,
								   @RequestParam("locationId") Long locationId)
	{

		if(!SecurityTools.hasRole("ROLE_ALL"))
			return "redirect:/department/all";
		
		Department department = departmentRepository.findByDepartmentId(departmentId);
		if(department != null)
		{
			Location location = locationRepository.findByLocationId(locationId);
			if(location!= null)
			{
				department.setDepartmentName(departmentName);
				department.setManagerId(BigDecimal.valueOf(managerId));
				department.setLocation(location);
				departmentRepository.save(department);
			}
		}
		return "redirect:/department/all";
	}
	
}
