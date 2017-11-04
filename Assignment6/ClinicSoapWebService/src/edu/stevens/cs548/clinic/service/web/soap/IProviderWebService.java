package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.util.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.TreatmentNotFoundExn;

@WebService(
		name="IProviderWebPort",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap")
	        
	@SOAPBinding(
			style=SOAPBinding.Style.DOCUMENT,
			use=SOAPBinding.Use.LITERAL,
			parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public interface IProviderWebService {
	
	@WebMethod
	public long addProvider (
			@WebParam(name="provider-dto",
			          targetNamespace="http://cs548.stevens.edu/clinic/dto")
			ProviderDto dto) throws ProviderServiceExn;

	@WebMethod
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProvider(long id) throws ProviderServiceExn;
	
	@WebMethod
	@WebResult(name="provider-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProviderByNPI(long pid) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerGetTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;

	@WebMethod(operationName="provideAddDrugTreatment")
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerAddRadiology")
	public void addRadiology(long id, List<Date> radiologyDates, String diagnosis) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerAddSurgery")
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws ProviderServiceExn;
	
	@WebMethod
	public String siteInfo();
}
