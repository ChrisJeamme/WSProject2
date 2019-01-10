package com.projetws.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.projetws.model.Job;
import com.projetws.model.JobRepository;
import com.projetws.tools.TestTools;

public class JobControllerTest
{
	@Autowired
	@MockBean
	JobRepository jobRepository;
	
//	@Test
//	public void testList()
//	{
//		TestTools.getRequest("http://localhost:8080/job/list");
//		
//		fail("Not yet implemented");
//	}

	@Test 
	public void testUpdate()
	{
		System.err.println(jobRepository);
		Boolean exist = jobRepository.existsByJobId("TEST");
		
		if(!exist)
		{
			Job testJob = new Job();
			testJob.setJobId("TEST");
			testJob.setMaxSalary(new BigDecimal(0));
			testJob.setMinSalary(new BigDecimal(0));
			jobRepository.save(testJob);
		}

		TestTools.putRequest("http://localhost:8080/job/update/TEST",
				"{\r\n" + 
				"    \"jobTitle\" : \"testOk\"\r\n" + 
				"}");

		assertNotNull(jobRepository.findByJobId("TEST"));
		assertEquals(jobRepository.findByJobId("TEST").getJobTitle(), "testOk");
	}

}
