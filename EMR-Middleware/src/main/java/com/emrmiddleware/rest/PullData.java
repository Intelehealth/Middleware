package com.emrmiddleware.rest;

import java.sql.Timestamp;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emrmiddleware.action.PullDataAction;
import com.emrmiddleware.dto.PullDataDTO;
import com.emrmiddleware.dto.ResponseDTO;
import com.emrmiddleware.exception.ActionException;
import com.emrmiddleware.exception.DAOException;
import com.emrmiddleware.resource.Resources;
import com.emrmiddleware.utils.EmrUtils;
import com.google.gson.Gson;

import io.swagger.annotations.Api;

@Api("PULL DATA")
@Path("pull")
public class PullData {
	private final Logger logger = LoggerFactory.getLogger(PullData.class);
	@Context
	ServletContext context;

	@Path("pulldata/{locationuuid}/{lastpulldate}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getData(@PathParam("locationuuid") String locationuuid,
			@PathParam("lastpulldate") String lastpulldatatime) {

		ResponseDTO responsedto = new ResponseDTO();
		PullDataDTO pulldatadto = new PullDataDTO();
		Gson gson = new Gson();
		try {
			PullDataAction pulldataaction = new PullDataAction();
			Timestamp lastdatapulltime = EmrUtils.getFormatDate(lastpulldatatime);
			pulldatadto = pulldataaction.getPullData(lastdatapulltime, locationuuid);
			responsedto.setStatus(Resources.OK);
			responsedto.setData(pulldatadto);
		} catch (DAOException e) {
			logger.error(Resources.DAOEXCEPTION, e);
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		} catch (Exception e) {
			logger.error(Resources.CONTROLLEREXCEPTION + e.getMessage());
			responsedto.setStatusMessage(Resources.ERROR, Resources.SERVER_ERROR, Resources.UNABLETOPROCESS);
			return Response.status(500).entity(gson.toJson(responsedto)).build();
		}

		return Response.status(200).entity(gson.toJson(responsedto)).build();

	}

}