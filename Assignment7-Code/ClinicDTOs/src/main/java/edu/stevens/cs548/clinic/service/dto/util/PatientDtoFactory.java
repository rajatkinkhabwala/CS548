package edu.stevens.cs548.clinic.service.dto.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.stevens.cs548.clinic.domain.Patient;


public class PatientDtoFactory {
	private static Logger logger = Logger.getLogger(PatientDtoFactory.class.getCanonicalName());
	ObjectFactory factory;
	
	public PatientDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public PatientDto createPatientDto () {
		return factory.createPatientDto();
	}
	
	public PatientDto createPatientDto (Patient p) {
		
		logger.info("INSIDE PATIENTDTOFACTORY FOR CREATING OBJECT");
		PatientDto d = factory.createPatientDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		d.setId(p.getId());
		d.setDob(p.getBirthDate());
		d.setName(p.getName());
		d.setPatientId(p.getPatientId());
		
		logger.info("INSIDE PATIENTDTOFACTORY == gettign treatment");
		List <Long> tids = p.getTreatmentIdsForService();
		d.treatments = new ArrayList<>();
		for (int i=0; i<tids.size(); i++){
			d.treatments.add(tids.get(i));
		}
		logger.info("INSIDE PATIENTDTOFACTORY == AFTER TREATMENT SET");
		return d;
	}

}
