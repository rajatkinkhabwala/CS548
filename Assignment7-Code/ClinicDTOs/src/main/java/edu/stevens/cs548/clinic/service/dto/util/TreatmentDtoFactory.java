package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.RadiologyDate;
import edu.stevens.cs548.clinic.domain.Surgery;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public DrugTreatmentType createDrugTreatmentDto () {
		return factory.createDrugTreatmentType();
	}

	public SurgeryType createsurgeryTreatmentDto () {
		return factory.createSurgeryType();
	}
	public RadiologyType createRadiologyTreatmentDto () {
		return factory.createRadiologyType();
	}
	
	public TreatmentDto createTreatmentDto(){
		return factory.createTreatmentDto();
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		DrugTreatmentType drugTreatment = this.createDrugTreatmentDto();
		drugTreatment.setDosage(t.getDosage());
		drugTreatment.setName(t.getDrug());
		treatment.setDrugTreatment(drugTreatment);
		return treatment;
		
	}
	
	public TreatmentDto createTreatmentDto(Surgery t){
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		SurgeryType surgeryTreatment = this.createsurgeryTreatmentDto();
		surgeryTreatment.setDate(t.getDate());
		treatment.setSurgery(surgeryTreatment);
		return treatment;
		
	}
	
	public TreatmentDto createTreatmentDto(Radiology t){
		TreatmentDto treatment = factory.createTreatmentDto();
		treatment.setDiagnosis(t.getDiagnosis());
		RadiologyType radiologyTreatment = this.createRadiologyTreatmentDto();
		for(RadiologyDate d :t.getDate()){
			radiologyTreatment.getDate().add(d.getDate());	
		}
		treatment.setRadiology(radiologyTreatment);
		return treatment;
		
	}
}
