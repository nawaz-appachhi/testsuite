package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;

import com.myntra.apiTests.portalservices.devapiservice.DevApiServiceHelper;
import org.codehaus.jettison.json.JSONException;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;

public class DataBaseAssertion {
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static APIUtilities utilities = new APIUtilities();
	static DevApiServiceHelper devApiServiceHelper = new DevApiServiceHelper();
	String appId="2";
	String versionSpecification;
	int previouFeedMaxIterations=20;
	int previousStreamMaxIterations=20;
	int	nextStreamMaxIteration=20;
	String persona = null;
	EndToEndHelper helper = new EndToEndHelper();
	public DataBaseAssertion(){
		versionSpecification=System.getenv("API_VERSION");
		if(null==versionSpecification)
		{
		 versionSpecification = "v2.7";
		}
	}
	
	public void performActions(EndToEndRecorder record,ArrayList<String> failures,String pumpsPayload) throws JSONException{
		
	}

}
