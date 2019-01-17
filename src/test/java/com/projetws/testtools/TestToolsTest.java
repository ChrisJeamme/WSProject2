package com.projetws.testtools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.projetws.tools.TestTools;

public class TestToolsTest
{

	@Test
	public void testPutRequest()
	{
		String result = TestTools.putRequest("https://jsonplaceholder.typicode.com/posts/1","{\"a\":\"b\"}");
		System.out.println(result);
		String truth =  "{\n" + 
						"  \"a\": \"b\",\n" + 
						"  \"id\": 1\n" + 
						"}";
		assertEquals(truth, result);
	}

	@Test
	public void testGetRequest()
	{
		String result = TestTools.getRequest("https://jsonplaceholder.typicode.com/todos/1");
		System.out.println(result);
		String truth =  "{\n" + 
						"  \"userId\": 1,\n" + 
						"  \"id\": 1,\n" + 
						"  \"title\": \"delectus aut autem\",\n" + 
						"  \"completed\": false\n" + 
						"}";
		assertEquals(truth, result);
	}

}
