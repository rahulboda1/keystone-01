package com.hcsc.claim.productservice.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
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

import com.hcsc.claim.productservice.model.entity.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleResourceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void createResource() {

		Resource resource = createResourceRequest();
		HttpEntity<Resource> entity = new HttpEntity<Resource>(resource, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources"), HttpMethod.POST,
				entity, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void getResources() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources"), HttpMethod.GET,
				entity, String.class);
		String expected = "[{\"id\":1,\"name\":\"Jeff\",\"description\":\"Doctor\",\"subName\":\"Doc-01\",\"subId\":102},"
				+ "{\"id\":2,\"name\":\"John\",\"description\":\"Patient\",\"subName\":\"Pat-01\",\"subId\":103}]";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void getResourcebyId() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/1"),
				HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void updateResource() {

		Resource resource = createResourceRequest();
		HttpEntity<Resource> entity = new HttpEntity<Resource>(resource, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/1"),
				HttpMethod.PUT, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
	/*@Test
	public void deleteResources() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources"),
				HttpMethod.DELETE, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}*/

/*	@Test
	public void deleteResourcesbyId() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v2/resources/1"),
				HttpMethod.DELETE, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}*/

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private Resource createResourceRequest() {
		Resource resource = new Resource(1L,"rahul", "rahul kumar", "rk", 102L);
		return resource;
	}

}
