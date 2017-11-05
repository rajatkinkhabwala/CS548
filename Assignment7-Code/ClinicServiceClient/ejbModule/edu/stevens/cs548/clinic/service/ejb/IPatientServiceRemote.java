package edu.stevens.cs548.clinic.service.ejb;

import javax.ejb.Remote;

import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;


@Remote
public interface IPatientServiceRemote extends IPatientService {

	TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

}
