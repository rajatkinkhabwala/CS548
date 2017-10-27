package edu.stevens.cs548.clinic.domain;

import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: RadiologyDate
 *
 */
@Embeddable

public class RadiologyDate {

	@Temporal(TemporalType.DATE)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
//	public RadiologyDate() {
//		super();
//	}
   
}
