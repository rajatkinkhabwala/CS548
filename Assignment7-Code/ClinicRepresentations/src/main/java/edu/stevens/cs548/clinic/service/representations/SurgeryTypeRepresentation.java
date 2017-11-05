package edu.stevens.cs548.clinic.service.representations;

import edu.stevens.cs548.clinic.service.web.rest.data.SurgeryType;

public class SurgeryTypeRepresentation extends SurgeryType {

	public SurgeryTypeRepresentation(edu.stevens.cs548.clinic.service.dto.SurgeryType surgery) {
		this.setDate(surgery.getDate());
	}

	public SurgeryTypeRepresentation() {
		// TODO Auto-generated constructor stub
	}

}
