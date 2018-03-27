package com.hcsc.claim.module1.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcsc.claim.module1.model.Resource;


public interface ClaimRepository extends CrudRepository<Resource, Long>{}
