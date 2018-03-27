package com.hcsc.claim.module1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcsc.claim.module1.api.SimpleResource;
import com.hcsc.claim.module1.model.Resource;
import com.hcsc.claim.module1.repository.ClaimRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SimpleResource.class, secure = false)
public class SimpleResourceTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ClaimRepository repository;
	
	@Test
	public void getAllResourcesTest() throws Exception {

		String jsonInString = "[{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102},{\"id\":2,\"name\":\"John\",\"description\":\"Patient\",\"subName\":\"Pat-01\",\"subId\":103}]";
		List<Resource> listOfResource = buildMockResponseList(jsonInString);
		Mockito.when(repository.findAll()).thenReturn(listOfResource);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/simple/v1/resources")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(jsonInString, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getResourceTest() throws Exception {
		
		String jsonInString = "{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102}";
		Resource resource = buildMockResponse(jsonInString);
		Mockito.when(repository.findOne(anyLong())).thenReturn(resource);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/simple/v1/resources/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(jsonInString, result.getResponse().getContentAsString(), false);

	}
	
	@Test
	public void createResourceTest() throws Exception {
		
		String jsonInString = "{\"id\":0,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102}";
		Resource resource = buildMockResponse(jsonInString);
		Mockito.when(repository.save(resource)).thenReturn(resource);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/simple/v1/resources")
				.accept(MediaType.APPLICATION_JSON).content(jsonInString)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		System.out.println(response.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(response.getContentAsString(), "Resource Created");
	}
	
	@Test
	public void deleteAllResourceTest() throws Exception {
		doNothing().when(repository).deleteAll();
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/simple/v1/resources");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void deleteResourceTest() throws Exception {

		String jsonInString = "{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102}";
		Resource resource = buildMockResponse(jsonInString);
		
		Mockito.when(repository.findOne(resource.getId())).thenReturn(resource);
		doNothing().when(repository).delete(resource.getId());
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/simple/v1/resources/{id}",resource.getId());
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void updateResourceTest() throws Exception {

		String jsonInString = "{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102}";
		Resource resource = buildMockResponse(jsonInString);
		Mockito.when(repository.findOne(resource.getId())).thenReturn(resource);
		Mockito.when(repository.save(resource)).thenReturn(resource);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/simple/v1/resources/{id}",resource.getId()).accept(MediaType.APPLICATION_JSON).content(jsonInString)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertNotNull(response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		Resource res = buildMockResponse(response.getContentAsString());
		assertEquals(res.getId(),Long.valueOf("1"));
		assertEquals(res.getName(),"Jeff");
		assertEquals(res.getDescription(),"Doctor");
		assertEquals(res.getSubName(),"Doc-01");
		assertEquals(res.getSubId(),"102");
		
	}
	
	private Resource buildMockResponse(String jsonInString)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInString, Resource.class);
	}

	private List<Resource> buildMockResponseList(String jsonInString)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInString, new TypeReference<List<Resource>>() {
		});
	}


}