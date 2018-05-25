package com.myntra.apiTests.inbound.helper.planningandbuying;

import java.util.List;

import org.testng.Assert;

import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceConstants.STATUS_CODES;
import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceConstants.STATUS_MESSAGES;
import com.myntra.apiTests.inbound.helper.planningandbuying.TeamServiceConstants.STATUS_TYPES;
import com.myntra.team.entry.ApplicationEntry;
import com.myntra.team.entry.TeamEntry;
import com.myntra.team.entry.TeamTypeEntry;
import com.myntra.team.response.ApplicationResponse;
import com.myntra.team.response.TeamResponse;
import com.myntra.team.response.TeamTypeResponse;

public class TeamServiceValidator {

	private JeevesHelper jeeveshelper = new JeevesHelper();

	/**
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	public void validateTheResponseStatus(String response, String type) throws Exception {
		String expectedStatusCode;
		String expectedStatusMessage;
		String expectedStatusType ;
		String failureMessage;

		switch (type) {
		case "CREATE_APPLICATION":
			expectedStatusCode = STATUS_CODES.CREATE_APPLICATION;
			expectedStatusMessage = STATUS_MESSAGES.CREATE_APPLICATION;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "Create team-service application failed. The expected ";
			break;

		case "CREATE_TEAMTYPE":
			expectedStatusCode = STATUS_CODES.CREATE_TEAMTYPE;
			expectedStatusMessage = STATUS_MESSAGES.CREATE_TEAMTYPE;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "Create team-service teamtype failed. The expected ";
			break;

		case "CREATE_TEAM":
			expectedStatusCode = STATUS_CODES.CREATE_TEAM;
			expectedStatusMessage = STATUS_MESSAGES.CREATE_TEAM;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "Create team-service team failed. The expected ";
			break;

		case "GET_APPLICATION_BY_ID":
			expectedStatusCode = STATUS_CODES.GET_APPLICATION_BY_ID;
			expectedStatusMessage = STATUS_MESSAGES.GET_APPLICATION_BY_ID;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "get team-service application by id failed. The expected ";
			break;

		case "GET_TEAMTYPE_BY_ID":
			expectedStatusCode = STATUS_CODES.GET_TEAMTYPE_BY_ID;
			expectedStatusMessage = STATUS_MESSAGES.GET_TEAMTYPE_BY_ID;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "get team-service teamtype by id failed. The expected ";
			break;

		case "GET_TEAM_BY_ID":
			expectedStatusCode = STATUS_CODES.GET_TEAM_BY_ID;
			expectedStatusMessage = STATUS_MESSAGES.GET_TEAM_BY_ID;
			expectedStatusType = STATUS_TYPES.SUCCESS;
			failureMessage = "get team-service team by id failed. The expected ";
			break;
			
		case "CREATE_DUPLICATE_APPLICATION":
			expectedStatusCode = STATUS_CODES.CREATE_DUPLICATE_APPLICATION;
			expectedStatusMessage = STATUS_MESSAGES.CREATE_DUPLICATE_APPLICATION;
			expectedStatusType = STATUS_TYPES.FAILURE;
			failureMessage = "create duplicate application passed. The expected ";
			break;		

		default:
			return;
		}

		String actualStatusCode = jeeveshelper.getStatusType(response, "statusCode");
		String actualStatusMessage = jeeveshelper.getStatusType(response, "statusMessage");
		String actualStatusType = jeeveshelper.getStatusType(response, "statusType");

		Assert.assertEquals(actualStatusCode, expectedStatusCode, failureMessage + "status code is" + expectedStatusCode
				+ " ,but actual status code is " + actualStatusCode);
		Assert.assertEquals(actualStatusMessage, expectedStatusMessage, failureMessage + "status message is"
				+ expectedStatusMessage + " ,but actual status message is " + actualStatusMessage);
		Assert.assertEquals(actualStatusType, expectedStatusType, failureMessage + "status type is"
				+ expectedStatusType + " ,but actual status type is " + actualStatusType);
	}

	/**
	 * @param applicationEntry
	 * @param applicationResponse
	 */
	public void validateApplicationResponse(ApplicationEntry applicationEntry,
			ApplicationResponse applicationResponse) {
		List<ApplicationEntry> appEntry = applicationResponse.getData();
		for (int i = 0; i < appEntry.size(); i++) {
			Assert.assertEquals(appEntry.get(i).getName(), applicationEntry.getName());
			Assert.assertEquals(appEntry.get(i).getDescription(), applicationEntry.getDescription());
			Assert.assertEquals(appEntry.get(i).getCode(), applicationEntry.getCode());
		}
	}

	/**
	 * @param teamTypeEntry
	 * @param teamTypeResponse
	 */
	public void validateTeamTypeResponse(TeamTypeEntry teamTypeEntry, TeamTypeResponse teamTypeResponse) {
		List<TeamTypeEntry> ttEntry = teamTypeResponse.getData();

		for (int i = 0; i < ttEntry.size(); i++) {
			Assert.assertEquals(ttEntry.get(i).getName(), teamTypeEntry.getName());
			Assert.assertEquals(ttEntry.get(i).getDescription(), teamTypeEntry.getDescription());
			Assert.assertEquals(ttEntry.get(i).getCode(), teamTypeEntry.getCode());
			for (int j = 0; j < ttEntry.get(i).getApplicationCodes().size(); j++) {
				Assert.assertEquals(ttEntry.get(i).getApplicationCodes().get(j), teamTypeEntry.getApplicationCodes().get(j));
			}
			for (int k = 0; k < teamTypeEntry.getTeamHierarchy().size(); k++) {
				Assert.assertEquals(teamTypeResponse.getData().get(i).getTeamHierarchy().get(k).getLevel(),
						teamTypeEntry.getTeamHierarchy().get(k).getLevel());
				Assert.assertEquals(teamTypeResponse.getData().get(i).getTeamHierarchy().get(k).isMandatory(),
						teamTypeEntry.getTeamHierarchy().get(k).isMandatory());
				Assert.assertEquals(teamTypeResponse.getData().get(i).getTeamHierarchy().get(k).getAttributeTypeCode(),
						teamTypeEntry.getTeamHierarchy().get(k).getAttributeTypeCode());
			}
		}
	}

	/**
	 * @param teamEntry
	 * @param teamResponse
	 */
	public void validateTeamResponse(TeamEntry teamEntry, TeamResponse teamResponse) {
		List<TeamEntry> team = teamResponse.getData();
		for (int i = 0; i < team.size(); i++) {
			Assert.assertEquals(team.get(i).getTeamTypeId(), teamEntry.getTeamTypeId());
			Assert.assertEquals(team.get(i).getName(), teamEntry.getName());
			Assert.assertEquals(team.get(i).getDescription(), teamEntry.getDescription());
			Assert.assertEquals(team.get(i).isActive(), teamEntry.isActive());
			for (int j = 0; j < team.get(i).getTeamDefinitionAttributeValues().size(); j++) {
				Assert.assertEquals(team.get(i).getTeamDefinitionAttributeValues().get(j).getAttributeCode(),
						teamEntry.getTeamDefinitionAttributeValues().get(j).getAttributeCode());
				Assert.assertEquals(team.get(i).getTeamDefinitionAttributeValues().get(j).getAttributeTypeCode(),
						teamEntry.getTeamDefinitionAttributeValues().get(j).getAttributeTypeCode());
			}
		}
	}
}
