package com.hcsc.claim.simple.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcsc.claim.simple.exception.CustomError;
import com.hcsc.claim.simple.exception.ResourceValidator;
import com.hcsc.claim.simple.model.entity.Resource;
import com.hcsc.claim.simple.model.repository.ClaimRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Rahul
 *
 */
@RestController
@RequestMapping("/api/v2")
public class SimpleResourceV2 {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ClaimRepository repository;

	@Autowired
	private ResourceValidator validator;

	/**
	 * Return all Resources
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get all the resources", nickname = "getResources")
	@GetMapping("/resources")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<?> getResources() {
		logger.info(" getResources start.");
		List<Resource> res = (List<Resource>) repository.findAll();
		if (ObjectUtils.isEmpty(res) ) {
			logger.error("Data Not Found.");
			return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
		} else {
			logger.info("Data Found.");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}

	/**
	 * Create new Resource
	 * 
	 * @return
	 */
	@PostMapping("/resources")
	@ApiOperation(value = "Create Resources", nickname = "CreateResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Created", response = Resource.class),
			@ApiResponse(code = 400, message = "Bad Request")})
	public ResponseEntity<?> createResource(@Valid @RequestBody Resource resource, Errors errors) {
		logger.info("/resources, Create Resource");
		validator.validate(resource, errors);
		if (errors.hasErrors()) {
			logger.error("Bad Request");
			return new ResponseEntity<>(
					new CustomError(errors.getFieldError().getField(), errors.getFieldError().getDefaultMessage()),
					HttpStatus.BAD_REQUEST);
		}
		Resource resource2 = repository.save(resource);
		logger.info("Resource Created");
		return new ResponseEntity<>(resource2, HttpStatus.CREATED);
	}
	
	 /**
   * Bulk Update
   *
   * @param resources
   * @return
   */
  @PutMapping("/resources")
  @ApiOperation(value = "Update Resources", nickname = "UpdateResources")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class)})
  public ResponseEntity<Object> updateResources(@Valid @RequestBody List<Resource> resources) {
    logger.info("Update Bulk Resources");
    repository.save(resources);
    return new ResponseEntity<>("Resources Updated Successfully.", HttpStatus.OK);
  }


	/**
	 * delete all the resources
	 * 
	 * @return
	 */
	@DeleteMapping("/resources")
	@ApiOperation(value = "Deletes all resources", nickname = "DeleteResources")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted", response = Resource.class)})
	public ResponseEntity<String> deleteResources() {
		logger.info("Delete");
		repository.deleteAll();
		logger.info("Resources Deleted");
		return new ResponseEntity<String>("All Data Deleted", HttpStatus.OK);
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
              @ApiResponse(code = 404, message = "Not Found")
              })
  public ResponseEntity<?> getResource(@PathVariable Long id) {
    logger.info("/resources/{id}, GET by Id");
    Resource res = null;
    res = repository.findOne(id);
    if (ObjectUtils.isEmpty(res)) {
      logger.error("Data Not Found");
      return new ResponseEntity<>("Invalid ID", HttpStatus.NOT_FOUND);
    
    }
    logger.info("Data Found");
    return new ResponseEntity<>(res, HttpStatus.OK);

  }
	
	/**
	 * Update resource with id
	 * 
	 * @param id
	 * @param resource
	 * @return
	 */
	@PutMapping("/resources/{id}")
	@ApiOperation(value = "Update a particular resource", nickname = "UpdateResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 400, message = "Bad Request"),@ApiResponse(code = 404, message = "Not Found")
				})
	public ResponseEntity<Object> updateResource(@PathVariable Long id, @Valid @RequestBody Resource resource,
			Errors errors) {
		logger.info("Update by ID");
		validator.validate(resource, errors);
		if (errors.hasErrors()) {
			logger.error("Bad Request");
			return new ResponseEntity<>(
					new CustomError(errors.getFieldError().getField(), errors.getFieldError().getDefaultMessage()),
					HttpStatus.BAD_REQUEST);
		}

		Resource dbresponse = repository.findOne(id);

		if (dbresponse == null) {
			logger.info("Data Not Found");
			return new ResponseEntity<>("Data Not Found for ID = "+id, HttpStatus.NOT_FOUND);
		}
		resource.setId(id);
		Resource res = repository.save(resource);
		logger.info("Resource updated");
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	/**
	 * Delete particular resource based on id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/resources/{id}")
	@ApiOperation(value = "Delete a particular resource", nickname = "DeleteResource")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<?> deleteResource(@PathVariable Long id) {
		logger.info("Delete by Id");
		Resource dbresponse = repository.findOne(id);
		if (dbresponse == null) {
			logger.error("Data Not Found");
			return new ResponseEntity<>("Data Not Found for ID = "+id, HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		logger.info("Resource Deleted by Id");
		return new ResponseEntity<>("Id =" + id + " , Deleted ", HttpStatus.OK);
	}

}
 