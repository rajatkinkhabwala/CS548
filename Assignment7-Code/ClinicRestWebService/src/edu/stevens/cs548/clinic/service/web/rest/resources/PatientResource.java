package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import edu.stevens.cs548.clinic.service.dto.util.PatientDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.representations.PatientRepresentation;
import edu.stevens.cs548.clinic.service.representations.Representation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/patient")
@RequestScoped
public class PatientResource {
	
	final static Logger logger = Logger.getLogger(PatientResource.class.getCanonicalName());
	
	/*
	 * TODO inject using HK2 (not CDI)
	 */
	@Context
    private UriInfo uriInfo;
    
	public class RecordNotFound extends WebApplicationException {
		private static final long serialVersionUID = 1L;
		
		public RecordNotFound(String message) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity(message).type(Representation.MEDIA_TYPE).build());
	     }

	}
	
	public class RecordNotCreated extends WebApplicationException{
		private static final long serialVersionUID = 1L;
	
		public RecordNotCreated(String message) {
	         super(Response.status(Response.Status.BAD_REQUEST)
	             .entity(message).type(Representation.MEDIA_TYPE).build());
		}
	
	}
	
    private PatientDtoFactory patientDtoFactory;

    /**
     * Default constructor. 
     */
    public PatientResource() {
    	patientDtoFactory = new PatientDtoFactory();
    }
    
	/*
	 * TODO inject using CDI
	 */
    
   // @EJB(beanName="PatientServiceBean")
    @Inject
    private IPatientServiceLocal patientService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return patientService.siteInfo();
    }

	/*
	 * TODO input should be application/xml
	 */
    @POST
	@Consumes("application/xml")
    public Response addPatient(PatientRepresentation patientRep) {
    	try {
    		logger.info("PATIENT RESOURCES: addPATIENT");
    		PatientDto dto = patientDtoFactory.createPatientDto();
    		logger.info("PATIENT RESOURCES: addPATIENT:: Created Dto");
    		dto.setPatientId(patientRep.getPatientId());
    		dto.setName(patientRep.getName());
    		dto.setDob(patientRep.getDob());
    		dto.setAge(patientRep.getAge());
    		logger.info("PATIENT RESOURCES: addPATIENT:: set values");
    		long id = patientService.addPatient(dto);
    		logger.info("PATIENT RESOURCES: addPATIENT:: id return: " +id);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (PatientServiceExn e) {
    		throw new RecordNotCreated("Unable to add patient");
    	}
    }
    
	/**
	 * Query methods for patient resources.
	 */
	/*
	 * TODO output should be application/xml
	 */
    @GET
	@Path("{id}")
	@Produces("application/xml")
	public PatientRepresentation getPatient(@PathParam("id") String id) {
		try {
			logger.info("PATIENT RESOURCES: GETPATIENT");
			long key = Long.parseLong(id);
			PatientDto patientDTO = patientService.getPatient(key);
			logger.info("PATIENT RESOURCES: PATIENTDTO OBJECT GENERATED");
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
			logger.info("PATIENT RESOURCES: PATIENT REPRESENTATION OBJECT GENERATED");
			return patientRep;
		} catch (PatientServiceExn e) {
			throw new RecordNotFound("No Patient Found");
		}
	}
    
	/*
	 * TODO output should be application/xml
	 */
    @GET
	@Path("byPatientId")
	@Produces("application/xml")
	public PatientRepresentation getPatientByPatientId(@QueryParam("id") String patientId) {
    	try {
    		logger.info("PATIENT RESOURCES: byPatientId id: "+patientId);
			long pid = Long.parseLong(patientId);
			PatientDto patientDTO = patientService.getPatientByPatId(pid);
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
			return patientRep;
		} catch (PatientServiceExn e) {
			throw new RecordNotFound("No Patient Found");
		}
	}
    
	/*
	 * TODO output should be application/xml
	 */
	@GET
	@Path("{id}/treatment/{tid}")
	@Produces("application/xml")
    public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = patientService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
			TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
			return treatmentRep;
			
    	} catch (PatientServiceExn e) {
    		throw new RecordNotFound("No Treatment Found For this patient");
    	}
    }

}