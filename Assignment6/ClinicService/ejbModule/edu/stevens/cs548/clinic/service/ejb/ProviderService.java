package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.service.dto.util.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.util.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.util.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;


@Stateless(name="ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {


	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());

	private IProviderFactory providerFactory;
	
	private ProviderDtoFactory providerDtoFactory;

	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;
	
	public ProviderService() {
		// TODO initialize factories
		providerFactory = new ProviderFactory();
		providerDtoFactory = new ProviderDtoFactory();
	}
	
	@Inject
	//@PersistenceContext(unitName="ClinicDomain")
	@ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void initialize(){
		providerDAO = new ProviderDAO(em);
		patientDAO = new PatientDAO(em);
	}
	
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try {
			Provider provider = providerFactory.createProvider(dto.getNpi(), dto.getName(), dto.getSpecialization());
			providerDAO.addProvider(provider);
			return provider.getId();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try{
			Provider provider = providerDAO.getProviderById(id);
			//PatientDto patientDTO = patientToDTO(patient);
			
			return providerDtoFactory.createProviderDto(provider);
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	@Override
	public ProviderDto getProviderByNPI(long pid) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try{
			Provider provider = providerDAO.getProviderByNPI(pid);
			//PatientDto patientDTO = patientToDTO(patient);
			
			return providerDtoFactory.createProviderDto(provider); 
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	@Resource(name="SiteInfo")
	private String siteInformation;
	
	@Override
	public String siteInfo() {
		// TODO Auto-generated method stub
		return siteInformation;
	}

	@Override
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = new Patient();
			Provider provider = providerDAO.getProviderById(id);
			provider.addDrugTreatment(Drug, diagnosis, dosage, patient);
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	@Override
	public void addRadiology(long id, List<Date> radiologyDates, String diagnosis) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = new Patient();
			Provider provider = providerDAO.getProviderById(id);
			provider.addRadiologyTreatment(radiologyDates, diagnosis, patient);
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	@Override
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = new Patient();
			Provider provider = providerDAO.getProviderById(id);
			provider.addSurgeryTreatment(surgeryDate, diagnosis, patient);
		} catch(ProviderExn e){
			throw new ProviderServiceExn(e.getMessage());
		}
	}

	@Override
	public void deleteTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Auto-generated method stub
		try {
//			Provider provider =  providerDAO.getProviderById(id);
//			TreatmentDto[] treatments = new TreatmentDto[tid.length];
//			
//			for(int i=0; i<tid.length; i++){
//				TreatmentExporter visitor = new TreatmentExporter();
//				provider.exportTreatment(tid[i], visitor);
//				treatments[i]=visitor.getDto();
//			}
			Provider provider = providerDAO.getProviderById(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
			//return null;
			//return treatments;
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
		
		private ObjectFactory factory = new ObjectFactory();
		
		private TreatmentDto dto;
		public TreatmentDto getDto(){
			return dto;
		}
		
		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug,
				float dosage) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates) {
			 
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			RadiologyType radioInfo = factory.createRadiologyType();
			radioInfo.setDate(dates);
			dto.setRadiology(radioInfo);
			return dto;
			
		}

		@Override
		public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
			 
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			SurgeryType surgeryInfo = factory.createSurgeryType();
			surgeryInfo.setDate(date);
			dto.setSurgery(surgeryInfo);
			return dto;
			
		}
		
	}


	@Override
	public long addTreatment(TreatmentDto tdto) throws ProviderServiceExn {
		long tid = 0;
		try {
			Provider provider = providerDAO.getProviderById(tdto.getProvider());
			Patient patient = patientDAO.getPatient(tdto.getPatient());
			if (tdto.getDrugTreatment() != null) {
				tid = provider.addDrugTreatment(tdto.getDiagnosis(), tdto.getDrugTreatment().getName(), tdto.getDrugTreatment().getDosage(), patient);
			} else if (tdto.getRadiology() != null) {
				tid = provider.addRadiologyTreatment(tdto.getRadiology().getDate(), tdto.getDiagnosis(), patient);
			} else if (tdto.getSurgery() != null) {
				tid = provider.addSurgeryTreatment(tdto.getSurgery().getDate(), tdto.getDiagnosis(), patient);
			}
		
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		} catch (PatientExn e) {
			throw new ProviderServiceExn(e.toString());
		}
		return tid;
	}



}
