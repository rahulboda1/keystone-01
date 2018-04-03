
package com.hcsc.claim.simple.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1")
public class SimpleResource {

  private final Logger LOG = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ClaimRepository repository;

  /**
   * Returns all Resources
   * 
   * @return
   */
  @GetMapping("/resources")
  @ApiOperation(value = "Get all resources", nickname = "getResources")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Showing All Resources", response = Resource.class) })
  public ResponseEntity<?> getResources() {
    LOG.info("/resources, GET start");
    List<Resource> res = (List<Resource>) repository.findAll();
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  /**
   * Create New Resource
   * 
   * @param resource
   * @return
   */
  @PostMapping("/resources")
  @ApiOperation(value = "Create new resource", nickname = "CreateResources")
  @ApiResponses(value = { @ApiResponse(code = 201, message = "Resource succesfully created", response = Resource.class) })
  public ResponseEntity<?> createResources(@RequestBody Resource resource) {
    LOG.info("Create Resource POST");
    repository.save(resource);
    return new ResponseEntity<>("Resource Created", HttpStatus.CREATED);
  }

  /**
   * Update Resources
   * 
   * @param resources
   * @return
   */
  @PutMapping("/resources")
  @ApiOperation(value = "Update Resources", nickname = "UpdateResources")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class) })
  public ResponseEntity<Object> updateResources(@RequestBody List<Resource> resources) {
    LOG.info("PUT Resources");
    repository.save(resources);
    return new ResponseEntity<>("Resources Updated Successfully.", HttpStatus.OK);

  }
  
  /**
   * Deletes all Resources
   * 
   * @return
   */
  @DeleteMapping("/resources")
  @ApiOperation(value = "Delete all resources", nickname = "DeleteResources")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Deleted", response = Resource.class) })
  public ResponseEntity<String> deleteResources() {
    LOG.info("DELETE all Resources");
    repository.deleteAll();
    return new ResponseEntity<String>("All Resources Deleted", HttpStatus.OK);
  }

  /**
   * Returns a specific Resource based on id
   * 
   * @param id
   * @return
   */
  @GetMapping("/resources/{id}")
  @ApiOperation(value = "Get a particular resource", nickname = "getResource")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class), @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<?> getResource(@PathVariable Long id) {
    LOG.info("/resources/{id}, GET by Id");
    Resource res = null;
    res = repository.findOne(id);
    if (ObjectUtils.isEmpty(res)) {
      LOG.error("Data Not Found");
      return new ResponseEntity<>("Data Not Found for Id = " + id, HttpStatus.NOT_FOUND);
    }
    LOG.info("Data Found");
    return new ResponseEntity<Resource>(res, HttpStatus.OK);
  }

  /**
   * Updates a specific Resource based on id
   * 
   * @param id
   * @param resource
   * @return
   */
  @PutMapping("/resources/{id}")
  @ApiOperation(value = "Update a particular resource", nickname = "UpdateResource")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class),
      @ApiResponse(code = 404, message = "Not Found"), })
  public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody Resource resource) {
    LOG.info("PUT Update by Id");
    Resource dbresponse = repository.findOne(id);
    if (dbresponse == null) {
      return new ResponseEntity<>("Data Not Found for ID = " + id, HttpStatus.NOT_FOUND);
    }
    resource.setId(id);
    repository.save(resource);
    return new ResponseEntity<>(resource, HttpStatus.OK);
  }

  /**
   * Deletes a specific Resource based on id
   * 
   * @param id
   * @return
   */
  @DeleteMapping("/resources/{id}")
  @ApiOperation(value = "Delete a particular resource", nickname = "DeleteResource")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Resource.class), @ApiResponse(code = 404, message = "Not Found") })
  public ResponseEntity<String> deleteResource(@PathVariable Long id) {
    LOG.info("DELETE by Id");

    Resource resource = repository.findOne(id);

    if (ObjectUtils.isEmpty(resource)) {
      LOG.error("Data Not Found");
      return new ResponseEntity<String>("Data Not Found", HttpStatus.NOT_FOUND);
    }
    LOG.info("Data Found");
    repository.delete(id);
    return new ResponseEntity<String>("Resource Deleted", HttpStatus.OK);
  }
  
  /**
   * Method Not Allowed
   * 
   * @param resource
   * @param id
   * @return
   */
  @PostMapping("/resources/{id}")
  @ApiOperation(value = "Method Not Allowed", nickname = "Create without Id")
  @ApiResponses(value = { @ApiResponse(code = 405, message = "Method Not Allowed") })
  public ResponseEntity<String> createResource(@RequestBody Resource resource, @PathVariable Long id) {
    LOG.info("POST BY ID - METHOD NOT ALLOWED");
    String message = "Method Not Allowed";
    return new ResponseEntity<String>(message, HttpStatus.METHOD_NOT_ALLOWED);
  }

}
