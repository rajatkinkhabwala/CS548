package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Provider
 *
 */


@NamedQueries({
	@NamedQuery(
			name = "SearchProviderBynpi", 
			query = "select p from Provider p where p.npi = :pid"
			),
	@NamedQuery(
			name = "CountProviderBynpi", 
			query = "select count(p) from Provider p where p.npi = :pid"
			),
	@NamedQuery(
			name = "RemoveAllProvider", 
			query = "delete from Provider p"
			) 
})

@Entity
@Table(name = "Provider")
public class Provider implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue private long id;
	private long npi; 
	
	private String name;
	

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	private String specialization;
	
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
		treatments = new ArrayList<Treatment>();		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(cascade = REMOVE, mappedBy = "providers", orphanRemoval = true)
	@OrderBy
	private List<Treatment> treatments;

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatment) {
		this.treatments = treatment;
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
			if (t.getProviders() != this) {
				t.setProviders(this);
			}
			//patient.addTreatment(t);
			
		
		return t.getTreatmentid();
	}
	
	public long addDrugTreatment(String dName, String diagnosis, float dosage, Patient patient){
		DrugTreatment drugTreatment = new DrugTreatment();
		drugTreatment.setDiagnosis(diagnosis);
		drugTreatment.setDrug(dName);
		drugTreatment.setDosage(dosage);
		drugTreatment.setPatient(patient);
		this.addTreatment(drugTreatment);
		return drugTreatment.getTreatmentid();
	}
	
	public long addRadiologyTreatment(List<Date> radiologyDates, String diagnosis, Patient patient){
		Radiology radiology = new Radiology();
		for(Date date : radiologyDates){
		RadiologyDate dates = new RadiologyDate();
		dates.setDate(date);
		radiology.getDate().add(dates);
		}
		radiology.setDiagnosis(diagnosis);
		radiology.setPatient(patient);
		this.addTreatment(radiology);
		return radiology.getTreatmentid();
	}
	
	public long addSurgeryTreatment(Date surgeryDate, String diagnosis, Patient patient){
		Surgery surgeryTreatment = new Surgery();
		surgeryTreatment.setDate(surgeryDate);
		surgeryTreatment.setDiagnosis(diagnosis);
		surgeryTreatment.setPatient(patient);
		this.addTreatment(surgeryTreatment);
		return surgeryTreatment.getTreatmentid();
	}
	
	public List<Long> getTreatmentIds(List<Long> treatmentIds) {
		
		for (Treatment t : this.getTreatments()) {
			treatmentIds.add(t.getTreatmentid());
		}
		return treatmentIds;
	}
	
	public List<Long> getTreatmentIdsForService() {
		List<Long> treatmentIds = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()) {
			treatmentIds.add(t.getTreatmentid());
		}
		return treatmentIds;
	}
	
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		// Export a treatment without violated Aggregate pattern
		// Check that the exported treatment is a treatment for this patient.
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getProviders() != this) {
			throw new TreatmentExn("Inappropriate treatment access: patient = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
}
