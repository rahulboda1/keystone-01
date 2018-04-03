package com.hcsc.claim.simple.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Rahul
 *
 */
@Entity
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotEmpty
	private String subName;
	@NotNull
	private Long subId;

	public Resource() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param subName
	 * @param subId
	 */
	public Resource(Long id, String name, String description, String subName, Long subId) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.subName = subName;
		this.subId = subId;
	}

	/**
	 * @param name
	 * @param description
	 * @param subName
	 * @param subId
	 */
	public Resource(String name, String description, String subName, Long subId) {
		this();
		this.name = name;
		this.description = description;
		this.subName = subName;
		this.subId = subId;
	}

	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getSubName() {
		return subName;
	}

	/**
	 * @param subName
	 */
	public void setSubName(String subName) {
		this.subName = subName;
	}

	/**
	 * @return
	 */
	public Long getSubId() {
		return subId;
	}

	/**
	 * @param subId
	 */
	public void setSubId(Long subId) {
		this.subId = subId;
	}

}
