package com.myntra.apiTests.inbound.helper.planningandbuying;

import com.google.gson.Gson;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.team.entry.*;
import com.myntra.team.response.ApplicationResponse;
import com.myntra.team.response.TeamResponse;
import com.myntra.team.response.TeamTypeResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamServiceHelper {

	Svc svc;
	static Logger log = Logger.getLogger(TeamServiceHelper.class);

	public String createApplication(String payload, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.APPLICATION, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.POST, payload, headers);
		log.debug("\nCreate team-service application response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();

	}

	public String getApplicationById(String id, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.APPLICATION + id, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nGet team-service application by id response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String searchApplication(String query, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.APPLICATION + "search?q=" + query, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nSearch team-service search application by query response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();		
		}

	public String createTeamType(String payload, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM_TYPE, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.POST, payload, headers);
		log.debug("\nCreate team-service team-type response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String getTeamTypeById(Long id, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM_TYPE + id, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nGet team-service team-type by id response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String searchTeamType(String query, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM_TYPE + "search?q=" + query, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nSearch team-service team-type response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String createTeam(String payload, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM, null, SERVICE_TYPE.TEAM_SERVICE.toString(),
				HTTPMethods.POST, payload, headers);
		log.debug("\nCreate team-service team response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String getTeamById(Long id, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM + id, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nGet team-service team by id response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public String searchTeam(String query, HashMap<String, String> headers) throws Exception {
		svc = HttpExecutorService.executeHttpService(TeamServiceConstants.TEAMSERVICE_ENDPOINTS.TEAM + "search?q=" + query, null,
				SERVICE_TYPE.TEAM_SERVICE.toString(), HTTPMethods.GET, null, headers);
		log.debug("\nsearch team-service team response:\n" + svc.getResponseBody() + "\n");
		return svc.getResponseBody();
	}

	public ApplicationEntry applicationEntry() {
		ApplicationEntry applicationEntry = new ApplicationEntry();
		applicationEntry.setName("Application_Test");
		applicationEntry.setDescription("Application_Description");
		applicationEntry.setCode("MMB" + RandomStringUtils.randomAlphanumeric(10));
		return applicationEntry;

	}

	public TeamTypeEntry teamTypeEntry(List<String> applicationcode, int[] level, String[] attributeTypeCode) {
		TeamTypeEntry teamTypeEntry = new TeamTypeEntry();
		teamTypeEntry.setName("Team_type_Test");
		teamTypeEntry.setDescription("Team_type_Description");
		teamTypeEntry.setCode("MMB" + RandomStringUtils.randomAlphanumeric(10));
		teamTypeEntry.setApplicationCodes(applicationcode);
		teamTypeEntry.setTeamHierarchy(teamHierarchy(level, attributeTypeCode));
		return teamTypeEntry;

	}

	public TeamEntry teamEntry(Long teamTypeId, String[] attributeTypeCode, String[] attributeCode, int[] level) {
		TeamEntry teamEntry = new TeamEntry();
		teamEntry.setTeamTypeId(teamTypeId);
		teamEntry.setName("ROADSTER");
		teamEntry.setDescription("Roadster_Products");
		teamEntry.setActive(true);
		teamEntry.setTeamDefinitionAttributeValues(teamDeninitionEntry(attributeTypeCode, attributeCode, level));
		return teamEntry;
	}

	public List<TeamDefinitionAttributeValuesEntry> teamDeninitionEntry(String[] attributeTypeCode,
			String[] attributeCode, int[] level) {
		List<TeamDefinitionAttributeValuesEntry> teamDefinitionlist = new ArrayList<TeamDefinitionAttributeValuesEntry>();
		for (int i = 0; i < attributeTypeCode.length; i++) {
			TeamDefinitionAttributeValuesEntry teamDefinitionEntry = new TeamDefinitionAttributeValuesEntry();
			teamDefinitionEntry.setAttributeCode(attributeCode[i]);
			log.debug("AttributeCode: " + teamDefinitionEntry.getAttributeCode());
			teamDefinitionEntry.setAttributeTypeCode(attributeTypeCode[i]);
			log.debug("AttributeTypeCode: " + teamDefinitionEntry.getAttributeTypeCode());
			teamDefinitionEntry.setLevel(level[i]);
			teamDefinitionlist.add(teamDefinitionEntry);
			log.debug("LIST:" + teamDefinitionlist.get(i));
		}
		log.debug("*************************" + teamDefinitionlist + "****************************");
		return teamDefinitionlist;
	}

	public List<TeamHierarchyEntry> teamHierarchy(int[] level, String[] attributeTypeCode) {
		List<TeamHierarchyEntry> teamHierarchylist = new ArrayList<TeamHierarchyEntry>();

		for (int i = 0; i < attributeTypeCode.length; i++) {
			TeamHierarchyEntry teamHierarchyEntry = new TeamHierarchyEntry();
			teamHierarchyEntry.setLevel(level[i]);
			log.debug("Level: " + teamHierarchyEntry.getLevel());
			teamHierarchyEntry.setMandatory(true);;
			teamHierarchyEntry.setAttributeTypeCode(attributeTypeCode[i]);
			log.debug("BU:" + teamHierarchyEntry.getAttributeTypeCode());
			teamHierarchylist.add(teamHierarchyEntry);
			log.debug("LIST:" + teamHierarchylist.get(i));
		}
		log.debug("*************************" + teamHierarchylist + "****************************");
		return teamHierarchylist;

	}

	public String generate_payload(Object obj) {
		return new Gson().toJson(obj).toString();
	}

	public List<String> getApplicationCode(List<ApplicationEntry> entries) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < entries.size(); i++) {
			list.add(entries.get(i).getCode());
		}
		return list;
	}

	public String getApplicationId(ApplicationResponse applicationResponse) {
		return applicationResponse.getData().get(0).getId().toString();
	}

	public Long getTeamTypeId(TeamTypeResponse teamTypeResponse) {
		return teamTypeResponse.getData().get(0).getId();
	}

	public Long getTeamId(TeamResponse teamResponse) {
		return teamResponse.getData().get(0).getId();
	}

}
