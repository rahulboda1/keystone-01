package com.hcsc.claim.simple.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.hcsc.claim.simple.model.entity.Resource;

/**
 * @author Rahul
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleResourceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	HttpHeaders headers = new HttpHeaders();

	/**
	 * Test case for create Resource
	 */
	@Test
	public void createResourceTest() {
	  // Given
		Resource resource = createResourceRequest();
		HttpEntity<Resource> entity = new HttpEntity<Resource>(resource, headers);
		// When
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources"), HttpMethod.POST,
				entity, String.class);
		//Then
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	/**
	 * Test case for get Resources
	 */
	@Test
	public void getResourcesTest() {
	  // Given
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		// When
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources"), HttpMethod.GET,
				entity, String.class);
		//Then
		String expected = "[{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102},"
				+ "{\"id\":2,\"name\":\"John\",\"description\":\"Patient\",\"subName\":\"Pat-01\",\"subId\":103}]";
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
		
	}

	/**
	 * Test case for get Resource by Id
	 */
	@Test
	public void getResourceByIdTest() {
	  // Given
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		// When
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/1"),
				HttpMethod.GET, entity, String.class);
		//Then
		String expected = "{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102}";
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(expected, response.getBody());
	}
	
	/**
	 * Test case for Resource Not Found
	 */
	@Test
	public void getResourceByIdNotFountTest() {
	  // Given
	  HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	  // When
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/4"),
				HttpMethod.GET, entity, String.class);
		//Then
		String expected = "Invalid ID";
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(expected, response.getBody());
	}

	/**
	 * Test case for update Resource
	 */
	@Test
	public void updateResource() {
	  // Given
		Resource resource = updateResourceRequest();
		// When
		ResponseEntity<Resource> responseByID = restTemplate.exchange(createURLWithPort("/api/v2/resources/1"),
				HttpMethod.GET,null, Resource.class);
		
		HttpEntity<Resource> entity = new HttpEntity<Resource>(resource, headers);		
		
		ResponseEntity<Resource> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/"+responseByID.getBody().getId()),
				HttpMethod.PUT, entity, Resource.class);
		//Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());		
		assertEquals(resource.getName(),response.getBody().getName());
	}
	
	/**
	 * Test case for delete Resource
	 */
	@Test
	public void deleteResourceByIdTest() {
	  // Given
		Resource resource = createResourceRequest();
		HttpEntity<Resource> createEntity = new HttpEntity<Resource>(resource, headers);
		// When
		ResponseEntity<Resource> createdResponse = restTemplate.exchange(createURLWithPort("/api/v2/resources"), HttpMethod.POST,
				createEntity, Resource.class);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/"+createdResponse.getBody().getId()),
				HttpMethod.DELETE, null, String.class);
		//Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	/**
	 * @return
	 */
	private Resource createResourceRequest() {
		Resource resource = new Resource("rahul", "rahul kumar", "rk", 102L);
		return resource;
	}
	
	/**
	 * @return
	 */
	private Resource updateResourceRequest() {
		Resource resource = new Resource(1L,"rahulU", "rahul kumarU", "rkU", 102L);
		return resource;
	}

}
