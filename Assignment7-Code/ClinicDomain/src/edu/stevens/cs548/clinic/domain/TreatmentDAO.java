package edu.stevens.cs548.clinic.domain;


import javax.persistence.EntityManager;

public class TreatmentDAO implements ITreatmentDAO {
	
	public TreatmentDAO (EntityManager em) {
		this.em = em;
	}
	
	private EntityManager em;
	
	@Override
	public Treatment getTreatment(long id) throws TreatmentExn {
		Treatment t = em.find(Treatment.class, id);
		if (t == null) {
			throw new TreatmentExn("Missing treatment: id = " + id);
		} else {
			return t;
		}
	}

	@Override
	public long addTreatment(Treatment t) {
//		Patient pat = em.getReference(Patient.class, patientid);
//		Provider pro = em.getReference(Provider.class, providerid);
//		t.setPatient(pat);
//		t.setProviders(pro);
		em.persist(t);
		em.flush();
	
		return t.getTreatmentid();
		
	}
//
//	@Override
//	public long addDrugTreatment(Treatment t) {
//	
//		em.persist(t);
//		em.flush();
//		
//		return t.getTreatmentid();
//	}
//
//	@Override
//	public long addRadiology(Treatment t) {
//		em.persist(t);
//		em.flush();
//		return t.getTreatmentid();
//	}
//
//	@Override
//	public long addSurgery(Treatment t) {
//		em.persist(t);
//		em.flush();
//		return t.getTreatmentid();
//	}

	@Override
	public void deleteTreatment(Treatment t) {
		// TODO Auto-generated method stub
		em.remove(t);
		em.flush();
	}
	
}
