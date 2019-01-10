package com.projetws.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projetws.model.Department;
import com.projetws.model.DepartmentRepository;
import com.projetws.model.Employee;
import com.projetws.model.EmployeeDTO;
import com.projetws.model.EmployeeRepository;
import com.projetws.model.EmployeeRole;
import com.projetws.model.Job;
import com.projetws.model.JobRepository;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/employee")
@Api(value = "employees")
public class EmployeeController
{
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	JobRepository jobRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	
	@RequestMapping("/all")
	public String getAllEmployees(Model m)
	{
		List<Employee> employees = employeeRepository.findByOrderBySalary();
		List<Job> jobs = jobRepository.findAll();
		List<Department> departements = departmentRepository.findAll();
		
		m.addAttribute("employees", employees);
		m.addAttribute("jobs", jobs);
		m.addAttribute("departements", departements);
		return "employees";
	}
	
	@RequestMapping(value="/updateEmployee", method=RequestMethod.POST)
	public String updateEmployee(@RequestParam("employeeId") Long employeeId, 
								 @RequestParam("employeeFirstName") String firstName, 
								 @RequestParam("employeeLastName") String lastName,
								 @RequestParam("employeeCommissionPct") BigDecimal employeeCommissionPct,
								 @RequestParam("employeeSalary") BigDecimal employeeSalary,
								 @RequestParam("employeeEmail") String employeeEmail,
								 @RequestParam("employeeManagerId") BigDecimal employeeManagerId,
								 @RequestParam("employeeHireDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date employeeHireDate,
								 @RequestParam("employeePhoneNumber") String employeePhoneNumber,
								 @RequestParam("departementId") Long departementId,
								 @RequestParam("jobId") String jobId)
	{
		if(!SecurityTools.hasRole("ROLE_EDITOR"))
			return "redirect:/employee/all";
		
		Employee employee= employeeRepository.findByEmployeeId(employeeId);
		if(employee != null)
		{
			Job job = jobRepository.findByJobId(jobId);
			Department department = departmentRepository.findByDepartmentId(departementId);
			if(job!= null && department != null)
			{
				employee.setFirstName(firstName);
				employee.setLastName(lastName);
				employee.setCommissionPct(employeeCommissionPct);
				employee.setSalary(employeeSalary);
				employee.setEmail(employeeEmail);
				employee.setManagerId(employeeManagerId);
				employee.setHireDate(employeeHireDate);
				employee.setPhoneNumber(employeePhoneNumber);
				employee.setDepartment(department);
				employee.setJob(job);
				updateEmployeeRights(employee);
				employeeRepository.save(employee);
			}
		}
		return "redirect:/employee/all";
	}
	
	@RequestMapping(value = "/firstName/{firstName}", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeDTO> getByFirstName(@PathVariable String firstName)
	{
		ModelMapper mapper = new ModelMapper();
		
		List<EmployeeDTO> employeeDTOList = new ArrayList<>();
		List<Employee> employeeList = employeeRepository.findAll();
		
		for(Employee e : employeeList)
		{
			if(e.getFirstName().equals(firstName))
				employeeDTOList.add(mapper.map(e,  EmployeeDTO.class));
		}
		
		return employeeDTOList;
	}
	
	@RequestMapping(value = "/salaryDistribution", method = RequestMethod.GET)
	public String getsalaryDistributionPage(Model m)
	{
		List<Employee> employees = employeeRepository.findByOrderBySalary();
		m.addAttribute("employees", employees);
		
		HashMap<String, Integer> salaryRangeList = new LinkedHashMap<>();
		BigDecimal salaryMin = employees.get(0).getSalary();
		BigDecimal salaryMax = employees.get(employees.size()-1).getSalary();
		BigDecimal salaryGap = salaryMax.subtract(salaryMin);
		BigDecimal rangeSize = salaryGap.divide(new BigDecimal(10));
		
		BigDecimal lowerSalary = salaryMin;
		BigDecimal upperSalary = lowerSalary.add(rangeSize);
		int nbEmployeesInRange = 0;
		int i=0;
		while (i<employees.size())
		{
			if(employees.get(i).getSalary().compareTo(upperSalary) <= 0) 
			{
				nbEmployeesInRange++;
				i++;
			}
			else 
			{
				String key = lowerSalary.toString()+" - "+upperSalary.toString();
				salaryRangeList.put(key, nbEmployeesInRange);
				nbEmployeesInRange = 0;
				lowerSalary = upperSalary;
				upperSalary = lowerSalary.add(rangeSize);
			}
		}
		String key = lowerSalary.toString()+" - "+upperSalary.toString();
		salaryRangeList.put(key, nbEmployeesInRange);
		nbEmployeesInRange = 0;
		lowerSalary = upperSalary;
		upperSalary = lowerSalary.add(rangeSize);
		
		
		m.addAttribute("salaryRangeList", salaryRangeList);
		return "salary_distribution";
	}
	
	@RequestMapping("/updateRoles")
	public String updateRoles()
	{
		List<Employee> employees = employeeRepository.findAll();

		for (Employee employee : employees)
		{
			updateEmployeeRights(employee);
		}
		employeeRepository.saveAll(employees);
		
		return "employees";
	}
	
	private void updateEmployeeRights(Employee employee)
	{
		Set<EmployeeRole> roles = new HashSet<>();
		if (employee.getJob().getJobTitle().equals("President"))
		{
			roles.add(EmployeeRole.ROLE_DEFAULT);
			roles.add(EmployeeRole.ROLE_EDITOR);
			roles.add(EmployeeRole.ROLE_CONSULT);
			roles.add(EmployeeRole.ROLE_ALL);
		}
		else if (employee.getDepartment().getDepartmentName().equals("Accounting") || employee.getDepartment().getDepartmentName().equals("Finance"))
		{
			roles.add(EmployeeRole.ROLE_DEFAULT);
			roles.add(EmployeeRole.ROLE_EDITOR);
		}
		else if (employee.getDepartment().getDepartmentName().equals("Sales"))
		{
			roles.add(EmployeeRole.ROLE_DEFAULT);
			roles.add(EmployeeRole.ROLE_CONSULT);
		}
		else 
		{
			roles.add(EmployeeRole.ROLE_DEFAULT);
		}
		employee.setRoles(roles);
	}
	
	@RequestMapping("/generateUsernameAndPassword")
	public String generateUsernameAndPassword()
	{
		List<Employee> employees = employeeRepository.findAll();

		for (Employee employee : employees)
		{
			employee.setUserName(employee.getFirstName().toLowerCase()+"."+employee.getLastName().toLowerCase());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			employee.setPassword(encoder.encode("password"));
		}
		employeeRepository.saveAll(employees);
		
		return "employees";
	}
	
	
}
