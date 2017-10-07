package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Provider
 *
 */
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import static javax.persistence.CascadeType.REMOVE;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@Table(name = "Provider")
public class Provider implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private long id;
	private long npi; 
	
	private String name;
	
	
	
	public long getNpi() {
		return npi;
	}

	public void setNpi(long npi) {
		this.npi = npi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Provider() {
		super();
		treatment = Collections.emptyList();		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(cascade = REMOVE, orphanRemoval = true, mappedBy = "providers")
	private List<Treatment> treatment;

	public List<Treatment> getTreatment() {
		return treatment;
	}

	public void setTreatment(List<Treatment> treatment) {
		this.treatment = treatment;
	}


}
