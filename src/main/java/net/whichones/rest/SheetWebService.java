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

import net.whichones.common.lines.LinesService;
import net.whichones.common.lines.Services;
import net.whichones.common.lines.data.Line;
import net.whichones.common.sheet.SheetsService;
import net.whichones.common.sheet.data.Sheet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Path("/sheet")
@WebService(name=Services.SHEET_WEB_SERVICE)
public class SheetWebService extends SpringBeanAutowiringSupport {

	
	private SheetsService sheetsService;
	private LinesService linesService;
	
	
	/**
	 * @param sheetsService the sheetsService to set
	 */
	@Inject
	public void setSheetsService(SheetsService sheetsService) {
		this.sheetsService = sheetsService;
	}

	/**
	 * @param linesService the linesService to set
	 */
	@Inject
	public void setLinesService(LinesService linesService) {
		this.linesService = linesService;
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
	@GET
	@Path("line/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Line getLine(@PathParam("id") Integer lineId){
		return linesService.getLine(lineId);
	}
	@POST
	@Path("line")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Line saveLine(Line line){
		return linesService.saveLine(line);
	}
	@POST
	@Path("add/line/{sheetId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Line createLine(Line line, @PathParam("sheetId") Integer sheetId){
		return sheetsService.addLine(line, sheetId);
	}
	@POST
	@Path("remove/line/{lineId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean removeLine(@PathParam("lineId") Integer lineId){
		return linesService.deleteLine(lineId);
	}
	
}
