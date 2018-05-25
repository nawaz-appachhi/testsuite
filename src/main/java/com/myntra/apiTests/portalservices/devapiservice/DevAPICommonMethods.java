package com.myntra.apiTests.portalservices.devapiservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.ListIterator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


public class DevAPICommonMethods {
	//SET IDP TOKENS
	public static String XID;
	public static String UIDX;
	public static String PPID;
	public static String SXID;
	IDPTestsHelper idpHelper;
	
	public void getAndSetTokens(String email, String password)
	{
		RequestGenerator SignInRequest = idpHelper.signIn(email, password);
		String SignInResponse = SignInRequest.returnresponseasstring();
		if(SignInRequest.response.getStatus()==200)
		{
			System.out.println("SignIn Service Response :\n "+SignInResponse);
			DevAPICommonMethods.SXID = SignInRequest.response.getHeaderString("sxid");
			DevAPICommonMethods.XID = JsonPath.read(SignInResponse, "$.meta.token").toString();
			DevAPICommonMethods.UIDX = JsonPath.read(SignInResponse, "$.data.login").toString();
			System.out.println("XID : "+XID);
			System.out.println("UIDX : "+UIDX);	
			try
			{
				DevAPICommonMethods.PPID = JsonPath.read(SignInResponse, "$.data.profile.publicProfileId").toString();
				System.out.println("PPID : "+PPID);
			}
			catch(PathNotFoundException E)
			{
				System.out.println("Unable to Set PPID - Verify Sign In Response");
			}
		}
		else
		{
			System.out.println("Unable to Sign in and get IDP tokens!");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Method to Parse JSON and check for null / empty keys
	String Current="$";
	String Node_="";
	List<String> emptyNodes=new ArrayList<String>();
	HashSet<String> emptyNodeSet = new HashSet<String>();
	public void PrintNodes(JsonNode Node)
	{
		if(!Current.equals(Node.asText()))
		{
			Current=Current+"."+Node.asText();
		}
		Iterator<String> NodeIterator = Node.getFieldNames();
		while(NodeIterator.hasNext())
		{
			String node = NodeIterator.next();
			JsonNode value = Node.get(node);
			if(value.isObject())
			{
				PrintNodes(value);
			}
			else
			{
				String NodeValue = value.asText();
				System.out.println(Current+"."+node+" : "+NodeValue);
				
			}
		}
		
		
	}
	
	public List<String> getEmptyNodes(JsonNode Node)
	{
		
		Iterator<String> NodeIterator = Node.getFieldNames();
		
		String NodeNew="";
		while(NodeIterator.hasNext())
		{
			String node = NodeIterator.next();
			JsonNode value = Node.get(node);
			if(value.isObject())
			{
				
				getEmptyNodes(value);
			}
			else if(value.isArray())
			{
				continue;
			}
			else
			{
				String NodeValue = value.asText();
				if(NodeValue.isEmpty() || NodeValue.equals(null) ||NodeValue.equals(""))
				{
					emptyNodes.add(node);
					emptyNodeSet.add(node);
					System.out.println("Found Empty Node : "+node);
					
				}
				else
				{
					//System.out.println(node+" : "+NodeValue);
				}
			}
		}
	
		return emptyNodes;
		
		
	}
	
	public boolean doesContainEmptyNodes(String response) throws JsonProcessingException, IOException
	{
		boolean hasEmptyNodes = false;
		JsonNode json = new ObjectMapper().readTree(response);
		List<String> emptyNodes = getEmptyNodes(json);
		
		System.out.println("Printing LIST -->"+emptyNodeSet);
		if(!emptyNodes.isEmpty())
		{
			hasEmptyNodes=true;
			System.out.println("FAULTY NODES:\n ");
			ListIterator<String> ITR = emptyNodes.listIterator();
			while(ITR.hasNext())
			{
				System.out.println(ITR.next());
			}
		}
		return hasEmptyNodes;
	}
	
	
	

}
