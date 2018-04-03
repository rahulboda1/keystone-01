package com.hcsc.claim.simple.Exception;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hcsc.claim.simple.model.entity.Resource;

/**
 * @author Rahul
 *
 */
@Component
public class ResourceValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Resource.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {

		Resource resource = (Resource) obj;
		
		if (StringUtils.isEmpty(resource.getName())) {
			errors.rejectValue("name", " Name is Empty");
		}
		if (StringUtils.isEmpty(resource.getDescription())) {
			errors.rejectValue("description", "Description is empty");
		}
		if (StringUtils.isEmpty(resource.getSubName())) {
			errors.rejectValue("SubName", "SubName is empty");
		}
		if (resource.getSubId() == 0) {
			errors.rejectValue("SubId", "SubId is 0");
		}
		
	}

}
