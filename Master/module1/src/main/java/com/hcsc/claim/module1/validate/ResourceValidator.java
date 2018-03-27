package com.hcsc.claim.module1.validate;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hcsc.claim.module1.model.Resource;


@Component
public class ResourceValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Resource.class.equals(clazz);
	}

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
