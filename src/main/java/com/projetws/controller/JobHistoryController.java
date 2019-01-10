package com.projetws.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.projetws.model.Department;
import com.projetws.model.DepartmentRepository;
import com.projetws.model.Employee;
import com.projetws.model.EmployeeRepository;
import com.projetws.model.Job;
import com.projetws.model.JobHistory;
import com.projetws.model.JobHistoryPK;
import com.projetws.model.JobHistoryRepository;
import com.projetws.model.JobRepository;
import com.projetws.tools.SecurityTools;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/jobHistory")
@Api(value = "jobHistory")
public class JobHistoryController
{
	@Autowired
	JobHistoryRepository jobHistoryRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	JobRepository jobRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	@RequestMapping("/all")
	public String getAllJobHistories(Model m)
	{
		List<JobHistory> jobHistories= jobHistoryRepository.findAll();
		List<Department> departments = departmentRepository.findAll();
		List<Job> jobs = jobRepository.findAll();
		List<Employee> employees = employeeRepository.findAll()
;		
		m.addAttribute("jobHistories", jobHistories);
		m.addAttribute("departments", departments);
		m.addAttribute("jobs", jobs);
		m.addAttribute("employees", employees);
		return "jobHistories";
	}
	
	@RequestMapping(value="/updateJobHistory", method=RequestMethod.POST)
	public String updateJobHistory(@RequestParam("employeeId") Long employeeId, 
								   @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
								   @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, 
								   @RequestParam("departmentId") Long departmentId,
								   @RequestParam("jobId") String jobId)
	{
		if(!SecurityTools.hasRole("ROLE_ALL"))
			return "redirect:/jobHistory/all";
		
		java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
		JobHistory jobHistory = jobHistoryRepository.findByIdEmployeeIdAndIdStartDate(employeeId, startDateSql);
;		if (jobHistory != null)
		{
			Department department = departmentRepository.findByDepartmentId(departmentId);
			Job job = jobRepository.findByJobId(jobId);
			if (department != null && job != null)
			{
				jobHistory.setDepartment(department);
				jobHistory.setJob(job);
				jobHistory.setEndDate(endDate);
				jobHistoryRepository.save(jobHistory);
			}
		}
		
		return "redirect:/jobHistory/all";
	}
}
