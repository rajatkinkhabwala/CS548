package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Calendar;

import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@Table(name = "Radiology")
@DiscriminatorValue("RA")
public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue private long rid;
	
	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	@ElementCollection
	@Temporal(TemporalType.DATE)
	@CollectionTable(name="Radiology_date") 
//					 joinColumns= {
//							 //@JoinColumn(name="rid"),
//							 @JoinColumn(name="Treatmentid",referencedColumnName="Treatmentid"),
//							 @JoinColumn(name="patient_id",referencedColumnName="patient_id"),
//							 @JoinColumn(name="providers_id",referencedColumnName="providers_id")
//							 		}
//					)
	private List<Calendar> date;

	public List<Calendar> getDate() {
		return date;
	}

	public void setDate(List<Calendar> date) {
		this.date = date;
	}

	public Radiology() {
		super();
	}
   
}

