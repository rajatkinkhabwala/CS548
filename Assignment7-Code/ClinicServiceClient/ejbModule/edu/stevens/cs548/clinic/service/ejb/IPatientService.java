package edu.stevens.cs548.clinic.service.ejb;


import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.util.PatientDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;

public interface IPatientService {
	
	public class PatientServiceExn extends Exception {
		private static final long serialVersionUID = 1L;
		public PatientServiceExn (String m) {
			super(m);
		}
	}
	public class PatientNotFoundExn extends PatientServiceExn {
		private static final long serialVersionUID = 1L;
		public PatientNotFoundExn (String m) {
			super(m);
		}
	}
	public class TreatmentNotFoundExn extends PatientServiceExn {
		private static final long serialVersionUID = 1L;
		public TreatmentNotFoundExn (String m) {
			super(m);
		}
	}

	public long addPatient(PatientDto dto) throws PatientServiceExn;

	public PatientDto getPatient(long id) throws PatientServiceExn;

	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn;
	
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

	public String siteInfo();
	
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws PatientServiceExn;
	
	public void addRadiology(long id, List<Date> radiologyDates,String diagnosis) throws PatientServiceExn;
	
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws  PatientServiceExn;
	
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

}
