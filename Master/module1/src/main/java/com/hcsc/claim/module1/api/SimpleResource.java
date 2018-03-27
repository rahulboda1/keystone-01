package com.hcsc.claim.module1.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcsc.claim.module1.model.Resource;
import com.hcsc.claim.module1.repository.ClaimRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/simple/v1")
public class SimpleResource {

	@Autowired
	ClaimRepository repository;

	/**
	 * Return all Resources
	 * 
	 * @return
	 */
	@GetMapping("/resources")
	@ApiOperation(value = "Get all resources", nickname = "getResources")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Showing All Resources", response = Resource.class) })
	public ResponseEntity<?> getResources() {
		List<Resource> res = (List<Resource>) repository.findAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Return specific Resource based on id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/resources/{id}")
	@ApiOperation(value = "Get a particular resource", nickname = "getResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<?> getResource(@PathVariable Long id) {
		Resource res = null;
		res = repository.findOne(id);
		if (ObjectUtils.isEmpty(res)) {
			return new ResponseEntity<>("Data Not Found for Id = " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Resource>(res, HttpStatus.OK);
	}

	/**
	 * Create New Resource.
	 * 
	 * @return
	 */
	@PostMapping("/resources")
	@ApiOperation(value = "Create new resource", nickname = "CreateResources")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Resource succesfully created", response = Resource.class) })
	public ResponseEntity<?> createResources(@RequestBody Resource resource) {
		repository.save(resource);
		return new ResponseEntity<>("Resource Created", HttpStatus.CREATED);
	}

	/**
	 * Method Not Allowed
	 * 
	 */
	@PostMapping("/resources/{id}")
	@ApiOperation(value = "Method Not Allowed", nickname = "CreateResources")
	@ApiResponses(value = { @ApiResponse(code = 405, message = "Method Not Allowed") })
	public ResponseEntity<String> createResource(@RequestBody Resource resource, @PathVariable Long id) {
		String message = "Method Not Allowed";
		return new ResponseEntity<String>(message, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Deletes all Resources
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/resources")
	@ApiOperation(value = "Delete all resources", nickname = "DeleteResources")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted", response = Resource.class) })
	public ResponseEntity<String> deleteResources() {
		repository.deleteAll();
		return new ResponseEntity<String>("All Resources Deleted", HttpStatus.OK);
	}

	/**
	 * Update Resources
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/resources")
	@ApiOperation(value = "Update a particular resource", nickname = "UpdateResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class) })
	public ResponseEntity<Object> updateResources(@RequestBody List<Resource> resources) {
		repository.save(resources);
		return new ResponseEntity<>("Resources Updated Successfully.", HttpStatus.OK);

	}

	/**
	 * Update a specific based on id
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/resources/{id}")
	@ApiOperation(value = "Update a particular resource", nickname = "UpdateResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 404, message = "Not Found"), })
	public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody Resource resource) {
		Resource dbresponse = repository.findOne(id);
		if (dbresponse == null) {
			return new ResponseEntity<>("Data Not Found for ID = " + id, HttpStatus.NOT_FOUND);
		}
		resource.setId(id);
		repository.save(resource);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	/**
	 * Delete specific Resource based on id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/resources/{id}")
	@ApiOperation(value = "Delete a particular resource", nickname = "DeleteResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<String> deleteResource(@PathVariable Long id) {

		Resource resource = repository.findOne(id);

		if (ObjectUtils.isEmpty(resource))
			return new ResponseEntity<String>("Data Not Found", HttpStatus.NOT_FOUND);

		repository.delete(id);
		return new ResponseEntity<String>("Resource Deleted", HttpStatus.OK);
	}
}
