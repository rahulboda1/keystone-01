package com.hcsc.claim.simple.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcsc.claim.simple.model.entity.Resource;

/**
 * @author Rahul
 *
 */
public interface ClaimRepository extends CrudRepository<Resource, Long> {
}
