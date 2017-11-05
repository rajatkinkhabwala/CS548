package edu.stevens.cs548.clinic.service.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class PatientProducer
 */
@Stateless
@LocalBean
public class ClinicDomainProducer {

    /**
     * Default constructor. 
     */
    public ClinicDomainProducer() {
    }
    
    // TODO inject the persistence context (do NOT use @Inject)
    @PersistenceContext
    EntityManager em;
    
    @Produces @ClinicDomain
    public EntityManager clinicDomainProducer() {
    	return em;
    }
    
    public void clinicDomainDispose(@Disposes @ClinicDomain EntityManager em) {
    }

}
