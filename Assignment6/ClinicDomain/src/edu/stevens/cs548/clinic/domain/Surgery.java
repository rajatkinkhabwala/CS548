package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Surgery")
//@MappedSuperclass
@DiscriminatorValue("S")
public class Surgery  extends Treatment  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	


	@Temporal(TemporalType.DATE)
	private Date date;
	
	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Surgery() {
		super();
		this.setTreatmentType("S");
	}
   
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportSurgery(this.getTreatmentid(), 
								   		   this.getDiagnosis(),
								   		   this.getDate());
	}
	
}
