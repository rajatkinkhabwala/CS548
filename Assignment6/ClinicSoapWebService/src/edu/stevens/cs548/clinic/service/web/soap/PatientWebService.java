package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.util.PatientDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;

// Use JSR-181 annotations to specify Web service.
//Specify: endpoint interface (FQN), target namespace, service name, port name

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IPatientWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", 
		serviceName = "PatientWebService", portName = "PatientWebPort")

public class PatientWebService implements IPatientWebService {

	// Use CDI to inject the service
	@EJB(beanName="PatientServiceBean")
	IPatientServiceLocal service;

	@Override
	public long addPatient(PatientDto dto) throws PatientServiceExn {
		return service.addPatient(dto);
	}

	@Override
	public PatientDto getPatient(long id) throws PatientServiceExn {
		return service.getPatient(id);
	}

	@Override
	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn {
		return service.getPatientByPatId(pid);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn,
			PatientServiceExn {
		return service.getTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

	@Override
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws PatientServiceExn {
		
		this.service.addDrugTraeatment(id, diagnosis, Drug, dosage);
	}

	@Override
	public void addRadiology(long id, List<Date> radiologyDates, String diagnosis) throws PatientServiceExn {
		// TODO Auto-generated method stub
		this.service.addRadiology(id, radiologyDates, diagnosis);
	}

	@Override
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws PatientServiceExn {
		// TODO Auto-generated method stub
		this.service.addSurgery(id, surgeryDate, diagnosis);
	}

}
