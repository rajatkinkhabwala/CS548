package edu.stevens.cs548.clinic.service.representations;

import edu.stevens.cs548.clinic.service.web.rest.data.RadiologyType;

public class RadiologyTypeRepresentation extends RadiologyType {

	public RadiologyTypeRepresentation(edu.stevens.cs548.clinic.service.dto.RadiologyType radiology) {
      this.date = radiology.getDate();
	}

	public RadiologyTypeRepresentation() {
		// TODO Auto-generated constructor stub
	}

}
