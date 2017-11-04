package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;

public class ProviderFactory implements IProviderFactory {

	

	@Override
	public Provider createProvider(long npi, String name, String specialization) throws ProviderExn {
		Provider p = new Provider();
		p.setNpi(npi);
		p.setName(name);
		p.setSpecialization(specialization);
		return p;
	}

}
