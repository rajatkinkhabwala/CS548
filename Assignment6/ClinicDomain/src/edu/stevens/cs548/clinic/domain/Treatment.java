package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
/*
 * TODO
 */
@Entity
@Table(name = "Treatment")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TREATMENT_TYPE")
public abstract class Treatment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * TODO
	 */
	@Id @GeneratedValue private long Treatmentid;
	public long getTreatmentid() {
		return Treatmentid;
	}

	public void setTreatmentid(long treatmentid) {
		Treatmentid = treatmentid;
	}

	private String diagnosis;


	
	/*
	 * TODO
	 */
	@Column(name = "TREATMENT_TYPE", length = 2)
	private String treatmentType;
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/*
	 * TODO
	 */
	@ManyToOne(cascade = { PERSIST, REMOVE })	
	@JoinColumn(name = "patient_fk", referencedColumnName = "id")
	private Patient patient;
	 
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		//Patient pat = EntityManager.getReference(Patient.class, patientId);
		if (!patient.getTreatments().contains(this))
			patient.addTreatment(this);
	}
	
	@ManyToOne(cascade = { PERSIST, REMOVE })	
	@JoinColumn(name = "providers_fk", referencedColumnName = "id")
	private Provider providers;
	
	
	public Provider getProviders() {
		return providers;
	}

	public void setProviders(Provider provider) {
		this.providers = provider;
		if (!providers.getTreatments().contains(this))
			providers.addTreatment(this);
	}
	
	
	public abstract <T> T export(ITreatmentExporter<T> visitor);

	
	public Treatment() {
		super();
	}
   
}
