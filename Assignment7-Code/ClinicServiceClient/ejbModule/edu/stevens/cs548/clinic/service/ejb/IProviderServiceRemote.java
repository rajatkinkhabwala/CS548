package edu.stevens.cs548.clinic.service.ejb;

import javax.ejb.Remote;

import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;

@Remote
public interface IProviderServiceRemote extends IProviderService {

	TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
}
