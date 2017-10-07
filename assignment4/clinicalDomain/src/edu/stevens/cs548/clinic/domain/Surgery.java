package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Surgery
 *
 */
@Entity
@Table(name = "Surgery")
//@MappedSuperclass
@DiscriminatorValue("SU")
public class Surgery extends Treatment implements Serializable {

	
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
	}
   
}
