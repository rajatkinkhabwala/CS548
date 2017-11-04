package edu.stevens.cs548.clinic.service.dto.util;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs548.clinic.domain.Provider;

public class ProviderDtoFactory {
	
	ObjectFactory factory;
	
	public ProviderDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public ProviderDto createProviderDto () {
		return factory.createProviderDto();
	}
	
	public ProviderDto createProviderDto (Provider p) {
		ProviderDto d = factory.createProviderDto();
		/*
		 * TODO: Initialize the fields of the DTO.
		 */
		d.setId(p.getId());
		d.setNpi(p.getNpi());
		d.setSpecialization(p.getSpecialization());
		
		List <Long> tids = p.getTreatmentIdsForService();
		d.treatments = new ArrayList<>();
		for (int i=0; i<tids.size(); i++){
			d.treatments.add(tids.get(i));
		}
		
		return d;
	}

}
