package edu.stevens.cs548.clinic.service.representations;

import edu.stevens.cs548.clinic.service.web.rest.data.DrugTreatmentType;

public class DrugTreatmentTypeRepresentation extends DrugTreatmentType {

	public DrugTreatmentTypeRepresentation(edu.stevens.cs548.clinic.service.dto.util.DrugTreatmentType drugTreatment) {
		this.dosage = drugTreatment.getDosage();
		this.drugname = drugTreatment.getName();
	}

	public DrugTreatmentTypeRepresentation() {
		// TODO Auto-generated constructor stub
	}
}
