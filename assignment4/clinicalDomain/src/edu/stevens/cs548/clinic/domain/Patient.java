package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(
		name="SearchPatientByPatientID",
		query="select p from Patient p where p.patientId = :pid"),
	@NamedQuery(
		name="CountPatientByPatientID",
		query="select count(p) from Patient p where p.patientId = :pid"),
	@NamedQuery(
		name = "RemoveAllPatients", 
		query = "delete from Patient p")
})

/**
 * Entity implementation class for Entity: Patient
 *
 */
@Entity
@Table(name="Patient")

public class Patient implements Serializable {

	
	@Id @ GeneratedValue
	private long id;
	private long patientId;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	private static final long serialVersionUID = 1L;

	public Patient() {
		super();
	}   
	public long getId() {
		return this.id;
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

   
}
