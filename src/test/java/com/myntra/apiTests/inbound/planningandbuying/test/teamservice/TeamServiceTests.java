package com.myntra.apiTests.inbound.planningandbuying.test.teamservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceConstants;
import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceValidator;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myntra.apiTests.inbound.helper.planningandbuying.JeevesHelper;
import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceHelper;
import com.myntra.apiTests.inbound.planningandbuying.test.InseasonOrderIndent;
import com.myntra.team.entry.ApplicationEntry;
import com.myntra.team.entry.TeamEntry;
import com.myntra.team.entry.TeamTypeEntry;
import com.myntra.team.response.ApplicationResponse;
import com.myntra.team.response.TeamResponse;
import com.myntra.team.response.TeamTypeResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class TeamServiceTests extends BaseTest {

	private static Logger log;
	private TeamServiceHelper teamserviceHelper;
	private JeevesHelper jeeveshelper;
	private ApplicationEntry appEntry;
	private TeamTypeEntry teamTypeEntry;
	private TeamEntry teamEntry;
	private TeamServiceValidator teamServiceValidator;
	private ObjectMapper mapper;
	private HashMap<String, String> create_baseoi_headers;
	private List<String> applicationCode;
	private String appCode;
	private Long teamTypeId;
	private Long teamId;
	private String applicationId;
	String query;

	@BeforeClass(alwaysRun = true)
	public void init() {
		log = Logger.getLogger(InseasonOrderIndent.class);
		jeeveshelper = new JeevesHelper();
		teamserviceHelper = new TeamServiceHelper();
		teamServiceValidator = new TeamServiceValidator();
		mapper = new ObjectMapper();
		// headers
		create_baseoi_headers = jeeveshelper.Headers(Constants.HEADERS.AUTHORIZATION, Constants.HEADERS.ACCEPT,
				Constants.HEADERS.CONTENTTYPE);

	}

	/**
	 * Test for create application api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service create application test")
	public void team_service_create_application_test() throws Exception {
		List<ApplicationEntry> applicationEntries = new ArrayList<ApplicationEntry>();
		ApplicationResponse createapplicationResponse = new ApplicationResponse();
		appCode = "MMB" + RandomStringUtils.randomAlphanumeric(10);

		appEntry = teamserviceHelper.applicationEntry();
		applicationEntries.add(appEntry);

		String appEntry_payload = teamserviceHelper.generate_payload(appEntry);

		// Create team-service application
		String create_application_response = teamserviceHelper.createApplication(appEntry_payload,
				create_baseoi_headers);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		createapplicationResponse = mapper.readValue(create_application_response, ApplicationResponse.class);
		teamServiceValidator.validateTheResponseStatus(create_application_response, "CREATE_APPLICATION");
		teamServiceValidator.validateApplicationResponse(appEntry, createapplicationResponse);
		applicationId = teamserviceHelper.getApplicationId(createapplicationResponse);

		applicationCode = teamserviceHelper.getApplicationCode(applicationEntries);

	}

	/**
	 * Test for get application by id api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, dependsOnMethods = {
			"team_service_create_application_test" }, description = "Team-Service get application by id test")
	public void team_service_get_application_by_id_test() throws Exception {
		ApplicationResponse getapplicationResponse = new ApplicationResponse();

		// get team-service application by id
		String get_application_by_id_response = teamserviceHelper.getApplicationById(applicationId,
				create_baseoi_headers);
		getapplicationResponse = mapper.readValue(get_application_by_id_response, ApplicationResponse.class);
		teamServiceValidator.validateTheResponseStatus(get_application_by_id_response, "GET_APPLICATION_BY_ID");
		teamServiceValidator.validateApplicationResponse(appEntry, getapplicationResponse);
	}

	/**
	 * Test for create team type api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity",
			"team-service" }, description = "Team-Service create team type test", dependsOnMethods = {
					"team_service_create_application_test" })
	public void team_service_create_team_type_test() throws Exception {

		// create team-service teamtype
		teamTypeEntry = teamserviceHelper.teamTypeEntry(applicationCode, TeamServiceConstants.ATTRIBUTES.LEVEL,
				TeamServiceConstants.ATTRIBUTES.MMB_ATTRIBUTETYPE_CODE);
		String createteamType_payload = teamserviceHelper.generate_payload(teamTypeEntry);
		String createteamType_response = teamserviceHelper.createTeamType(createteamType_payload,
				create_baseoi_headers);
		TeamTypeResponse createteamTypeResponse = mapper.readValue(createteamType_response, TeamTypeResponse.class);
		teamServiceValidator.validateTheResponseStatus(createteamType_response, "CREATE_TEAMTYPE");
		teamServiceValidator.validateTeamTypeResponse(teamTypeEntry, createteamTypeResponse);
		teamTypeId = teamserviceHelper.getTeamTypeId(createteamTypeResponse);

	}

	/**
	 * Test for get team type by id api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity",
			"team-service" }, description = "Team-Service get team type by id test", dependsOnMethods = {
					"team_service_create_team_type_test" })
	public void team_service_get_team_type_by_id_test() throws Exception {

		// get team-service team type by id
		String get_teamType_by_id_response = teamserviceHelper.getTeamTypeById(teamTypeId, create_baseoi_headers);
		TeamTypeResponse getTeamTypeResponse = mapper.readValue(get_teamType_by_id_response, TeamTypeResponse.class);
		teamServiceValidator.validateTheResponseStatus(get_teamType_by_id_response, "GET_TEAMTYPE_BY_ID");
		teamServiceValidator.validateTeamTypeResponse(teamTypeEntry, getTeamTypeResponse);

	}

	/**
	 * Test for create team api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service create team test", dependsOnMethods = {
			"team_service_create_team_type_test" })
	public void team_service_create_team_test() throws Exception {
		// create team-service team
		teamEntry = teamserviceHelper.teamEntry(teamTypeId, TeamServiceConstants.ATTRIBUTES.MMB_ATTRIBUTETYPE_CODE,
				TeamServiceConstants.ATTRIBUTES.MMB_ATTRIBUTE_CODE, TeamServiceConstants.ATTRIBUTES.LEVEL);
		String team_payload = teamserviceHelper.generate_payload(teamEntry);
		String createTeam_response = teamserviceHelper.createTeam(team_payload, create_baseoi_headers);
		TeamResponse createTeamResponse = mapper.readValue(createTeam_response, TeamResponse.class);
		teamServiceValidator.validateTheResponseStatus(createTeam_response, "CREATE_TEAM");
		teamServiceValidator.validateTeamResponse(teamEntry, createTeamResponse);
		teamId = teamserviceHelper.getTeamId(createTeamResponse);

	}

	/**
	 * Test for get team by id api
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service get team by id test", dependsOnMethods = {
			"team_service_create_team_test" })
	public void team_service_get_team_by_id_test() throws Exception {
		// get team service team by id
		String get_team_by_id_response = teamserviceHelper.getTeamById(teamId, create_baseoi_headers);
		TeamResponse getTeamResponse = mapper.readValue(get_team_by_id_response, TeamResponse.class);
		teamServiceValidator.validateTheResponseStatus(get_team_by_id_response, "GET_TEAM_BY_ID");
		teamServiceValidator.validateTeamResponse(teamEntry, getTeamResponse);

	}

	/**
	 * Test to create application with existing applicationcode
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service get team by id test", dependsOnMethods = {
			"team_service_create_application_test" })
	public void team_service_create_duplicate_application_test() throws Exception {
		String appEntry_payload = teamserviceHelper.generate_payload(appEntry);

		// Create team-service application
		String create_application_response = teamserviceHelper.createApplication(appEntry_payload,
				create_baseoi_headers);
		teamServiceValidator.validateTheResponseStatus(create_application_response, "CREATE_DUPLICATE_APPLICATION");
	}

	/**
	 * Test to create a duplicate team type
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service get team by id test", dependsOnMethods = {
			"team_service_create_team_type_test" })
	public void team_service_create_duplicate_team_type_test() throws Exception {

		String createteamType_payload = teamserviceHelper.generate_payload(teamTypeEntry);
		String createteamType_response = teamserviceHelper.createTeamType(createteamType_payload,
				create_baseoi_headers);
		teamServiceValidator.validateTheResponseStatus(createteamType_response, "CREATE_DUPLICATE_APPLICATION");
	}

	/**
	 * Test to create a duplicate team
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Team-Service get team by id test", dependsOnMethods = {
			"team_service_create_team_test" })
	public void team_service_create_duplicate_team_test() throws Exception {

		String team_payload = teamserviceHelper.generate_payload(teamEntry);
		String createTeam_response = teamserviceHelper.createTeam(team_payload, create_baseoi_headers);
		teamServiceValidator.validateTheResponseStatus(createTeam_response, "CREATE_DUPLICATE_APPLICATION");
	}

	/**
	 * Test to search for application using search query
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Search Team-Service with search query", dependsOnMethods = {
			"team_service_create_application_test" })
	public void team_service_search_application_test() throws Exception {
		query = "name.eq:" + appEntry.getName() + "___" + "description.eq:" + appEntry.getDescription() + "___" + "code.eq:" + appEntry.getCode();
		String application_resp = teamserviceHelper.searchApplication(query, create_baseoi_headers);
		ApplicationResponse applicationResponse = mapper.readValue(application_resp, ApplicationResponse.class);
		
		teamServiceValidator.validateTheResponseStatus(application_resp, "GET_APPLICATION_BY_ID");
		teamServiceValidator.validateApplicationResponse(appEntry, applicationResponse);
	}	
	
	/**
	 * Test to search team-type using search query
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Search Team-type with search query", dependsOnMethods = {
			"team_service_create_team_type_test" })
	public void team_service_search_team_type_test() throws Exception {
		query = "name.eq:" + teamTypeEntry.getName() + "___" + "description.eq:" + teamTypeEntry.getDescription() + "___" + "code.eq:" + teamTypeEntry.getCode();
		String team_type_resp = teamserviceHelper.searchTeamType(query, create_baseoi_headers);
		TeamTypeResponse teamTypeResponse = mapper.readValue(team_type_resp, TeamTypeResponse.class);
		
		teamServiceValidator.validateTheResponseStatus(team_type_resp, "GET_TEAMTYPE_BY_ID");
		teamServiceValidator.validateTeamTypeResponse(teamTypeEntry, teamTypeResponse);
	}
	
	/**
	 * Test to search team using search query
	 * @throws Exception
	 */
	@Test(groups = { "sanity", "team-service" }, description = "Search Team with search query", dependsOnMethods = {
			"team_service_create_team_test" })
	public void team_service_search_team_test() throws Exception {
		query = "name.eq:" + teamEntry.getName() + "___" + "description.eq:" + teamEntry.getDescription() + "___" + "teamTypeId.eq:" + teamEntry.getTeamTypeId();
		String team_resp = teamserviceHelper.searchTeam(query, create_baseoi_headers);
		TeamResponse teamResponse = mapper.readValue(team_resp, TeamResponse.class);
		
		teamServiceValidator.validateTheResponseStatus(team_resp, "GET_TEAM_BY_ID");
		teamServiceValidator.validateTeamResponse(teamEntry, teamResponse);
	}	
	
}
