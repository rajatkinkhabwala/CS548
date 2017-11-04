package edu.stevens.cs548.clinic.service.ejb;

import javax.ejb.Local;

import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;

@Local
public interface IProviderServiceLocal extends IProviderService {

	TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;

	
}
