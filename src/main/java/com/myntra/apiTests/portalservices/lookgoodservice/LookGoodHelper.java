package com.myntra.apiTests.portalservices.lookgoodservice;

import java.util.Arrays;
import java.util.List;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.legolas.Commons;

/**
 * @author shankara.c
 *
 */
public class LookGoodHelper extends CommonUtils
{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(LookGoodHelper.class);
	static String dbConnection_QA = "jdbc:mysql://"+"ec2-54-251-97-209.ap-southeast-1.compute.amazonaws.com"+"/myntra?"+"user=myntraAppDBUser&password=9eguCrustuBR";
	static String dbConnection_Fox = "jdbc:mysql://"+"54.179.37.12"+"/myntra?"+"user=myntraAppDBUser&password=9eguCrustuBR&port=3306";
	Commons con = new Commons();
	
	public List<String> lookGoodIdsFox7() throws ClassNotFoundException {
		
		con.createDbConnection(dbConnection_Fox);
		System.out.println("Fox db connected");
		String query = "select * from lg_look_book limit 5";
		StringBuffer sb= con.getDataFromDb(query, "id");
		String str = sb.toString();
		String[]  array = str.split("\n");
	    List<String> list = Arrays.asList(array);
	    return list; 
    
	}
	
	public List<String> lookGoodIdsFunctional() throws ClassNotFoundException {
		
		con.createDbConnection(dbConnection_QA);
		System.out.println("functional db connected");
		String query = "select * from lg_look_book limit 5";
		StringBuffer sb= con.getDataFromDb(query, "id");
		String str = sb.toString();
		String[]  array = str.split("\n");
	    List<String> list = Arrays.asList(array);
	    return list; 
    
	}
	
	public RequestGenerator invokeGetLookBookById(String lookId)
	{
		MyntraService getLookBookByIdService = Myntra.getService(ServiceType.PORTAL_LOOKGOOD, APINAME.GETLOOKBOOKBYID, init.Configurations, null, new String[]{ lookId });
	
		System.out.println("\nPrinting LookGoodService getLookBookById API URL :"+getLookBookByIdService.URL);
		log.info("\nPrinting LookGoodService getLookBookById API URL :"+getLookBookByIdService.URL);
		
		System.out.println("\nPrinting LookGoodService getLookBookById API Payload :\n\n"+getLookBookByIdService.Payload);
		log.info("\nPrinting LookGoodService getLookBookById API Payload :\n\n"+getLookBookByIdService.Payload);
		
		return new RequestGenerator(getLookBookByIdService);
	}
		
}
