package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentFactory {
	
	public Treatment createDrugTreatment (String diagnosis, String drug, float dosage);
	public Treatment createRadiologyTreatment(String diagnosis,List<Date> date);
	public Treatment createSurgeryTreatment(String diagnosis,Date date);
	/*
	 * TODO add methods for Radiology, Surgery
	 */

}
