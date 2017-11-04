package edu.stevens.cs548.clinic.service.dto.util;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs548.clinic.domain.Patient;

public class PatientDtoFactory {
	
	ObjectFactory factory;
	
	public PatientDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public PatientDto createPatientDto () {
		return factory.createPatientDto();
	}
	
	public PatientDto createPatientDto (Patient p) {
		PatientDto d = factory.createPatientDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		d.setId(p.getId());
		d.setDob(p.getBirthDate());
		d.setName(p.getName());
		d.setPatientId(p.getPatientId());
		
		List <Long> tids = p.getTreatmentIdsForService();
		d.treatments = new ArrayList<>();
		for (int i=0; i<tids.size(); i++){
			d.treatments.add(tids.get(i));
		}
		
		return d;
	}

}
