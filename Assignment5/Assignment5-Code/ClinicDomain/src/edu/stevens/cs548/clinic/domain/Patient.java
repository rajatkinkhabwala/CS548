package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;

/**
 * Entity implementation class for Entity: Patient
 *
 */
/*
 * TODO
 */



@NamedQueries({
	@NamedQuery(
		name="SearchPatientByPatientID",
		query="select p from Patient p where p.patientId = :pid"),
	@NamedQuery(
		name="CountPatientByPatientID",
		query="select count(p) from Patient p where p.patientId = :pid"),
	@NamedQuery(
			name="CountPatientByID",
			query="select count(p) from Patient p where p.id = :pid"),
	@NamedQuery(
		name = "RemoveAllPatients", 
		query = "delete from Patient p")
})

/*
 * TODO
 */
@Entity
@Table(name = "Patient")
public class Patient implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO JPA annotations
	@Id @GeneratedValue private long id;
	
	private long patientId;
	
	private String name;
	
	// TODO JPA annotation
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private int age;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	// TODO JPA annotations (propagate deletion of patient to treatments)
	@OneToMany(cascade = REMOVE, orphanRemoval = true, mappedBy = "patient")
	@OrderBy
	private List<Treatment> treatments;

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	
	public long addTreatment (Treatment t) {
		// Persist treatment and set forward and backward links
		
			this.treatmentDAO.addTreatment(t);
			this.getTreatments().add(t);
			if (t.getPatient() != this) {
				t.setPatient(this);
			}
		
		return t.getTreatmentid();
	}
	
	public void getTreatmentIds(List<Long> treatmentIds) {
		for (Treatment t : this.getTreatments()) {
			treatmentIds.add(t.getTreatmentid());
		}
	}
	
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		// Export a treatment without violated Aggregate pattern
		// Check that the exported treatment is a treatment for this patient.
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getPatient() != this) {
			throw new TreatmentExn("Inappropriate treatment access: patient = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
	
	public void addDrugTreatment(String dName, String diagnosis, float dosage, Provider provider){
		DrugTreatment drugTreatment = new DrugTreatment();
		drugTreatment.setDiagnosis(diagnosis);
		drugTreatment.setDrug(dName);
		drugTreatment.setDosage(dosage);
		drugTreatment.setProviders(provider);
		this.addTreatment(drugTreatment);
	}
	
	public void addRadiologyTreatment(List<Date> radiologyDates,String diagnosis,Provider provider){
		Radiology radiology = new Radiology();
		for(Date date : radiologyDates){
		RadiologyDate dates = new RadiologyDate();
		dates.setDate(date);
		radiology.getDate().add(dates);
		}
		radiology.setDiagnosis(diagnosis);
		radiology.setProviders(provider);
		this.addTreatment(radiology);
	}
	
	public void addSurgeryTreatment(Date surgeryDate, String diagnosis, Provider provider){
		Surgery surgeryTreatment = new Surgery();
		surgeryTreatment.setDate(surgeryDate);
		surgeryTreatment.setDiagnosis(diagnosis);
		surgeryTreatment.setProviders(provider);
		this.addTreatment(surgeryTreatment);
	}
	
	public Patient() {
		super();
		/*
		 * TODO initialize lists
		 */
		treatments = new ArrayList<Treatment>();
	}
   
}
