package edu.stevens.cs548.clinic.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author 
 *
 */
public class ClinicGateway implements IClinicGateway {

	private EntityManagerFactory emf;
	private EntityManager em;

	@Override
	public IPatientFactory getPatientFactory() {
		return new PatientFactory();
	}

	@Override
	public IPatientDAO getPatientDAO() {
		return new PatientDAO(em);
	}

	@Override
	public IProviderFactory getProviderFactory() {
		return new ProviderFactory();
	}

	@Override
	public IProviderDAO getProviderDAO() {
		
		return new ProviderDAO(em);
	}

	public ClinicGateway() {
		emf = Persistence.createEntityManagerFactory("ClinicDomain");
		em = emf.createEntityManager();
	}

//	@PreDestroy
//	public void destruct()
//	{
//	    emf.close();
//	}
}
