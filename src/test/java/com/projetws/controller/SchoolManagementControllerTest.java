package com.projetws.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public class SchoolManagementControllerTest
{
	@InjectMocks
	private SchoolManagementController schoolManagementController;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception
	{
        MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(schoolManagementController).build();
	}

	@Test
	public void testGetActualConnectedUser() throws Exception
	{
//		Model m = mock(Model.class);
//		Principal p = mock(Principal.class);
//		SchoolManagementController c = mock(SchoolManagementController.class);
//		
//		c.getActualConnectedUser(null,m);
	}
	
	@Test
	public void testNewSchool() throws Exception
	{
		// Without argument
		this.mockMvc.perform(get("/newSchool")).andExpect(status().is(400)); // Error 400 because no argument
		// Not connected
		this.mockMvc.perform(get("/newSchool")
				.param("schoolName", "test"))
		.andExpect(status().is(302)); // Error 302 because redirection to login
		// Case ok
//		Principal principal = mock(Principal.class);
//		Mockito.when(principal.getName()).thenReturn("me");
//		this.mockMvc.perform(get("/newSchool").principal(principal).param("schoolName", "test")).andExpect(status().isOk());
	}

	@Test
	public void testNewSchoolClass() throws Exception
	{
		// Without argument
		this.mockMvc.perform(get("/newSchoolClass")).andExpect(status().is(400)); // Error 400 because no argument
		// Not connected
		this.mockMvc.perform(get("/newSchoolClass")
				.param("schoolClassName", "test")
				.param("year", "2019")
				.param("schoolId", "1"))
		.andExpect(status().is(302)); // Error 302 because redirection to login
	}

	@Test
	public void testNewChild() throws Exception
	{
		// Without argument
		this.mockMvc.perform(get("/newChild")).andExpect(status().is(400)); // Error 400 because no argument
		// Not connected
		this.mockMvc.perform(get("/newChild")
				.param("firstName", "Jo")
				.param("lastName", "Lopez")
				.param("schoolClassId", "1")
				.param("emailParent", "aa@aa.fr")
				.param("phoneNumberParent", "0601234567"))
		.andExpect(status().is(302)); // Error 302 because redirection to login
	}

	@Test
	public void testGetSchoolManagement() throws Exception
	{
		Principal principal = mock(Principal.class);
		Model m = mock(Model.class);

		Mockito.when(principal.getName()).thenReturn("me");
		
		this.mockMvc.perform(get("/schoolManagement").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void testGetSchoolClassManagement()
	{
		fail("Not yet implemented");
	}

}
