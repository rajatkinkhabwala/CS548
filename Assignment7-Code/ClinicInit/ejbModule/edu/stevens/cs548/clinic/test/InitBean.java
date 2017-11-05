package edu.stevens.cs548.clinic.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.ejb.ClinicDomain;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());
	// TODO inject an EM
	@PersistenceContext(unitName="ClinicDomain")
	//@ClinicDomain
	EntityManager em;
	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
    
	@Inject
	IPatientServiceLocal patientService;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Rajat Kinkhabwala");

			try {

			
			IPatientDAO patientDAO = new PatientDAO(em);
			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);
			IProviderDAO providerDAO = new ProviderDAO(em);
			
			PatientFactory patientFactory = new PatientFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			
			
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */
			
			patientDAO.deletePatients();
			providerDAO.deleteProviders();
			
			
			Patient john = patientFactory.createPatient(12345678L, "John Doe",setDate("11/26/1990"), computeAge(setDate("11/26/1990")));
			patientDAO.addPatient(john);
			logger.info("Added "+john.getName()+" with id "+john.getId());
			
			Patient p = patientDAO.getPatient(john.getId());
			
			logger.info("Added Patient "+p.getName()+" with id "+p.getId() +" & Patient Id: "+p.getPatientId() +"Patient DOB: "+ p.getBirthDate()+" & patient age: "+ p.getAge());;
			
			Patient p1 = patientDAO.getPatientByPatientId(p.getPatientId());
		
			logger.info("Added Patientby ID: "+p1.getName()+" with id "+p1.getId());
		
			Provider Rajat = providerFactory.createProvider(989898, "Rajat Kinkhabwala", "radiology");
			providerDAO.addProvider(Rajat);
			
			logger.info("Added Provider: "+Rajat.getName()+" with npi "+Rajat.getNpi());
		
			Provider pro2 = providerDAO.getProviderById(Rajat.getId());
			logger.info("By ID : Added Provider: "+pro2.getName()+" with npi "+pro2.getNpi()+ " with id: "+ pro2.getId());
			
			Provider pro3 = providerDAO.getProviderByNPI(Rajat.getNpi());
			logger.info("BY NPI : Added Provider: "+pro3.getName()+" with npi "+pro3.getNpi()+ " with id: "+ pro3.getId());			
			
			Patient Sunny = patientFactory.createPatient(87654321L, "Sunny Patel",setDate("10/15/1980"), computeAge(setDate("10/15/1980")));
			patientDAO.addPatient(Sunny);
			
			logger.info("Added "+Sunny.getName()+" with id "+Sunny.getId());
			
			Patient RK = patientFactory.createPatient(15935785L, "RK ",setDate("03/07/1993"), computeAge(setDate("03/07/1993")));
			patientDAO.addPatient(RK);
			
			logger.info("Added "+RK.getName()+" with id "+RK.getId());

			
			Provider Rooney = providerFactory.createProvider(65983241, "Wayne Rooney", "Surgery");
			providerDAO.addProvider(Rooney);
			
			//Adding Surgery 
			long surgeryid = Rooney.addSurgeryTreatment(setDate("05/05/2010"), "appendics", john);
			
			//Adding Radiology Treatment 
			List<Date> radiologyDates = new ArrayList<Date>();
			for(int i =1;i<4;i++){
				radiologyDates.add(setDate("10/"+i+"/2014"));
			}
			long rid = Rajat.addRadiologyTreatment(radiologyDates, "Cancer", RK);
			
			logger.info("Provider : " + Rajat.getName() + " supervising treatment with id " + rid + " for patient "
					+ RK.getName());
			
			//Adding Drug Treatment 
			long drugTreatmentid = Rooney.addDrugTreatment("Asthma", "Epitomic", 6, RK);
			
			logger.info("Provider : " + Rooney.getName() + " supervising treatment with id " + drugTreatmentid + " for patient "
					+ RK.getName());
			
		} catch (PatientExn  e) {

			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
			IllegalStateException ex = new IllegalStateException("patient Exception" + e.getMessage());
			ex.initCause(e);
			throw ex;
			
		} 
		catch( ProviderExn e2){
			IllegalStateException ex = new IllegalStateException("provider Exception :"  + e2.getMessage());
			ex.initCause(e2);
			throw ex;
		} 

			
	}
	private Date setDate(String stringDate) {
		Date date = null;
		try {
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			 date = format.parse(stringDate);
		} catch (Exception e) {
			IllegalStateException ex = new IllegalStateException("Error while setting state");
			ex.initCause(e);
			throw ex;
		}
		return date;
	}
	private static int computeAge(Date dateOfBirth) {
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dateOfBirth);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		return age;
	}
}
