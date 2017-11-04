
package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {


	private EntityManager em;
	@Transient
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientDAO.class.getCanonicalName());

	@Override
	public long addProvider(Provider provider) throws ProviderExn {
		long npi = provider.getNpi();
		Query query = em.createNamedQuery("CountProviderBynpi").setParameter("pid", npi);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			try {
				
				em.persist(provider);
				em.flush();
				provider.setTreatmentDAO(this.treatmentDAO);
				
			} catch (Exception e) {
				throw new IllegalStateException("Unimplemented");				
			}
			// TODO add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			
			return provider.getId();
			
		} else {
			throw new ProviderExn("Insertion: Provider with provider id (" + npi + ") already exists.");
		}
	}

	
	@Override
	public Provider getProviderByNPI(long npi) throws ProviderExn {
		
		TypedQuery<Provider> query =  em.createNamedQuery("SearchProviderBynpi",Provider.class).setParameter("pid", npi);
		List<Provider> providerL = query.getResultList();
//		
		if (providerL.isEmpty() || providerL.size() > 1) {
			throw new ProviderExn("Ambiguious result : "+providerL.size());
		} else {
			Provider provider = providerL.get(0);
			provider.setTreatmentDAO(this.treatmentDAO);
			return provider;
		}
	}

	
	@Override
	public Provider getProviderById(long id) throws ProviderExn {

		
		Provider provider = em.find(Provider.class, id);
		if (provider == null) {
			throw new ProviderExn("No provider Found with the provided Id");
		} else {
			provider.setTreatmentDAO(this.treatmentDAO);
			return provider;
		}
		
	}

	
	@Override
	public void deleteProviders() {
		// TODO Auto-generated method stub
		em.createQuery("delete from Treatment t").executeUpdate();
		Query update = em.createNamedQuery("RemoveAllProvider");
		update.executeUpdate();
	}




}
