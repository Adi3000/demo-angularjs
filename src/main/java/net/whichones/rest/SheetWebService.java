package net.whichones.rest;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.whichones.common.lines.Services;
import net.whichones.common.sheet.SheetsService;
import net.whichones.common.sheet.data.Sheet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Path("/sheet")
@WebService(name=Services.SHEET_WEB_SERVICE)
public class SheetWebService extends SpringBeanAutowiringSupport {

	
	private SheetsService sheetsService;
	
	
	/**
	 * @param sheetsService the sheetsService to set
	 */
	@Inject
	public void setSheetsService(SheetsService sheetsService) {
		this.sheetsService = sheetsService;
	}

	@POST
	@Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Sheet createSheet(Sheet sheet){
		return sheetsService.createSheet(sheet);
	}
	
	@GET
	@Path("{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Sheet getSheetByToken(@PathParam("token") String token){
		return sheetsService.getSheetByToken(token);
	}
}
