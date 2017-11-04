package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.util.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	
	@EJB(beanName="ProviderServiceBean")
	IProviderServiceLocal service;
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByNPI(long pid) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getProviderByNPI(pid);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getTreatment(id, tid);
	}

	@Override
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		this.service.addDrugTraeatment(id, diagnosis, Drug, dosage);
	}

	@Override
	public void addRadiology(long id, List<Date> radiologyDates, String diagnosis) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		this.service.addRadiology(id, radiologyDates, diagnosis);
	}

	@Override
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		this.service.addSurgery(id, surgeryDate, diagnosis);
	}

	@Override
	public String siteInfo() {
		// TODO Auto-generated method stub
		return service.siteInfo();
	}


}
