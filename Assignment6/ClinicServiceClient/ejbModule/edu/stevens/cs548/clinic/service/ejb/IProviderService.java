package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.service.dto.util.PatientDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;

public interface IProviderService {
	
	public class ProviderServiceExn extends Exception {
		private static final long serialVersionUID = 1L;
		public ProviderServiceExn (String m) {
			super(m);
		}
	}
	public class ProviderNotFoundExn extends ProviderServiceExn {
		private static final long serialVersionUID = 1L;
		public ProviderNotFoundExn (String m) {
			super(m);
		}
	}
	public class TreatmentNotFoundExn extends ProviderServiceExn {
		private static final long serialVersionUID = 1L;
		public TreatmentNotFoundExn (String m) {
			super(m);
		}
	}

	public long addProvider(ProviderDto dto) throws ProviderServiceExn;

	public ProviderDto getProvider(long id) throws ProviderServiceExn;

	public ProviderDto getProviderByNPI(long pid) throws ProviderServiceExn;
	
	public TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;

	public String siteInfo();
	
	public void addDrugTraeatment(long id, String diagnosis, String Drug, float dosage) throws ProviderServiceExn;

	public void addRadiology(long id, List<Date> radiologyDates,String diagnosis) throws ProviderServiceExn;
	
	public void addSurgery(long id, Date surgeryDate, String diagnosis) throws  ProviderServiceExn;
	
	public void deleteTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	public long addTreatment(TreatmentDto tdto)  throws ProviderServiceExn;

}
