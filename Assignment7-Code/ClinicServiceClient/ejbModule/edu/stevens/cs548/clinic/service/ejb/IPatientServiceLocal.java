package edu.stevens.cs548.clinic.service.ejb;

import javax.ejb.Local;

import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;


@Local
public interface IPatientServiceLocal extends IPatientService {

	TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

}
