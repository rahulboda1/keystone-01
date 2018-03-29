package com.hcsc.claim.productservice.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcsc.claim.productservice.model.entity.Resource;

public interface ClaimRepository extends CrudRepository<Resource, Long> {
}
