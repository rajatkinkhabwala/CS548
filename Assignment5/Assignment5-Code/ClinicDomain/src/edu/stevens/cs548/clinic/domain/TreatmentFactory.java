package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;


public class TreatmentFactory implements ITreatmentFactory {

	@Override
	public Treatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		DrugTreatment treatment = new DrugTreatment();		
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		treatment.setTreatmentType(TreatmentType.DRUG_TREATMENT.getTag());
		return treatment;
	}


	@Override
	public Treatment createRadiologyTreatment(String diagnosis, List<Date> date) {
		Radiology radiology = new Radiology();
		radiology.setDiagnosis(diagnosis);
		for (Date radiologyDate : date) {
			RadiologyDate dates = new RadiologyDate();
			dates.setDate(radiologyDate);
			radiology.getDate().add(dates);
		}
		radiology.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
		return radiology;
	}


	@Override
	public Treatment createSurgeryTreatment(String diagnosis, Date date) {
		// TODO Auto-generated method stub
		Surgery surgery = new Surgery();
		surgery.setDiagnosis(diagnosis);
		surgery.setDate(date);
		surgery.setTreatmentType(TreatmentType.SURGERY.getTag());
		return surgery;
	}

}
