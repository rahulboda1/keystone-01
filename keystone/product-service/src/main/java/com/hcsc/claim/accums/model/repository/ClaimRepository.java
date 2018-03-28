package com.hcsc.claim.accums.model.repository;

import org.springframework.data.repository.CrudRepository;
import com.hcsc.claim.accums.model.entity.Resource;

public interface ClaimRepository extends CrudRepository<Resource, Long> {
}
