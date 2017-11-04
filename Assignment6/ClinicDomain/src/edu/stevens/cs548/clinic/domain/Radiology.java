package edu.stevens.cs548.clinic.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "Radiology")
@DiscriminatorValue("R")
public class Radiology  extends Treatment  implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	@ElementCollection
	@Temporal(TemporalType.DATE)
	@CollectionTable(name="Radiology_date", joinColumns = @JoinColumn(name = "RadiologyTreatmentid_fk")) 
	private List<RadiologyDate> date;

	public List<RadiologyDate> getDate() {
		return date;
	}


	public void setDate(List<RadiologyDate> date) {
		this.date = date;
	}


	public Radiology() {
		super();
		this.setTreatmentType("R");
		date = new ArrayList<RadiologyDate>();
	}

	
	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		List<Date> rdList = new ArrayList<Date>();
		for(RadiologyDate rd : this.date){
			rdList.add(rd.getDate());
		}
		return visitor.exportRadiology(this.getTreatmentid(), this.getDiagnosis(), rdList); 
		   		  
	}

}
