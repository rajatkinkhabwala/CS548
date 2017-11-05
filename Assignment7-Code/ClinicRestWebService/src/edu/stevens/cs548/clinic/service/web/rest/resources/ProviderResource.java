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
import edu.stevens.cs548.clinic.service.dto.util.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.util.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.Representation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
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

	@Context
	private UriInfo uriInfo;

	private ProviderDtoFactory providerDtoFactory;
	private TreatmentDtoFactory treatmentDtoFactory;

	/**
	 * Default constructor.
	 */
	public ProviderResource() {
		providerDtoFactory = new ProviderDtoFactory();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}

	@Inject
	private IProviderServiceLocal providerService;

	@GET
	@Path("site")
	@Produces("text/plain")
	public String getSiteInfo() {
		return providerService.siteInfo();
	}

	@POST
	@Consumes("application/xml")
	public Response addProvider(ProviderRepresentation providerRep) {
		try {
			ProviderDto dto = providerDtoFactory.createProviderDto();
			dto.setId(providerRep.getProviderId());
			dto.setName(providerRep.getName());
			dto.setSpecialization(providerRep.getSpecialization());
			long id = providerService.addProvider(dto);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
			URI url = ub.build(Long.toString(id));
			return Response.status(201).build();
		} catch (ProviderServiceExn e) {
			throw new RecordNotCreated("Unable to add Provider");
		}
	}

	@POST
	@Path("{id}/treatment")
	@Consumes("application/xml")
	public Response addTreatments(TreatmentRepresentation treatRep) {
		try {

			TreatmentDto treatDto = treatmentDtoFactory.createTreatmentDto();
			treatDto.setDiagnosis(treatRep.getDiagnosis());
			//treatDto.setId(Representation.getId(treatRep.getId()));
			treatDto.setPatient(Representation.getId(treatRep.getPatient()));
			treatDto.setProvider(Representation.getId(treatRep.getProvider()));
			if (treatRep.getDrugTreatment() != null) {
				DrugTreatmentType drugDto = treatmentDtoFactory.createDrugTreatmentDto();
				drugDto.setDosage(treatRep.getDrugTreatment().getDosage());
				drugDto.setName(treatDto.getDrugTreatment().getName());
				treatDto.setDrugTreatment(drugDto);
			}
			if (treatRep.getSurgery() != null) {
				SurgeryType surgeryDto = treatmentDtoFactory.createsurgeryTreatmentDto();
				surgeryDto.setDate(treatDto.getSurgery().getDate());
				treatDto.setSurgery(surgeryDto);
			}
			if (treatRep.getRadiology() != null) {
				RadiologyType radiologyDto = treatmentDtoFactory.createRadiologyTreatmentDto();
				radiologyDto.getDate().addAll(treatDto.getRadiology().getDate());
				treatDto.setRadiology(radiologyDto);
			}
			long tid = providerService.addTreatment(treatDto);
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{tid}");
			URI url = ub.build(Long.toString(tid));
			return Response.created(url).build();
		} catch (ProviderServiceExn e) {
			throw new RecordNotCreated("Unable to add Treatment");
		}
	}

	/**
	 * Query methods for provider resources.
	 */

	@GET
	@Path("{id}")
	@Produces("application/xml")
	public ProviderRepresentation getProvider(@PathParam("id") String id) {
		try {
			long key = Long.parseLong(id);
			ProviderDto providerDTO = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new RecordNotFound("No Provider Found");
		}
	}

	@GET
	@Path("byNPI")
	@Produces("application/xml")
	public ProviderRepresentation getProviderByProviderId(@QueryParam("id") String providerId) {
		try {
			long pid = Long.parseLong(providerId);
			ProviderDto providerDTO = providerService.getProviderByNPI(pid);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new RecordNotFound("No Provider Found");
		}
	}

	@GET
	@Path("{id}/treatment/{tid}")
	@Produces("application/xml")
	public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
		try {
			logger.info("Provider RESOURCES: getProvideTreatment: "+tid);
			TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
			logger.info("Provider RESOURCES: Treatment object generated: "+tid);
			TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
			logger.info("Provider RESOURCES: treatment REpresentation generated: "+tid);
			return treatmentRep;
		} catch (ProviderServiceExn e) {
			throw new RecordNotFound("No Treatment record Found");
		}
	}

}
