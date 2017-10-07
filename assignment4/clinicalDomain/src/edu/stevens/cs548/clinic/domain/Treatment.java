package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;

import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Table;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
@Entity
@Table(name = "Treatment")
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name="TREATMENT_TYPE")

public class Treatment implements Serializable {

	
	
	@Id @GeneratedValue private long Treatmentid;
	public long getTreatmentid() {
		return Treatmentid;
	}

	public void setTreatmentid(long treatmentid) {
		Treatmentid = treatmentid;
	}
	
private String diagnosis;
	
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	@ManyToOne(cascade = { PERSIST, REMOVE })
	
	
	@GeneratedValue
	private Patient patient;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		// More logic in the domain model.
	}
	
	@ManyToOne(cascade = { PERSIST, REMOVE })
	private Provider providers;
	
	
	public Provider getProvider() {
		return providers;
	}

	public void setProvider(Provider provider) {
		this.providers = provider;
	}
	public Treatment() {
		super();
	}
	
   
}
