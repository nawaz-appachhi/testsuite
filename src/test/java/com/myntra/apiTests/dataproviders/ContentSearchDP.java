package com.myntra.apiTests.dataproviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.DataOrc;
import com.myntra.lordoftherings.Toolbox;

public class ContentSearchDP
{
	static Map<Integer, List<LinkedList<Integer>>> powerset = new HashMap<>();
	static int count = 0;

	@DataProvider
	public Object[][] addContentDP(ITestContext testContext)
	{		
		Set<String> optional = new HashSet<String>();
		optional.add("description");
		optional.add("image");
		optional.add("content");
		optional.add("content_source");
		optional.add("creator");
		optional.add("category");
		optional.add("tags");
		optional.add("persona_tags");

		ArrayList<String> combinations = getAllCombinations(optional);
		Object[][] dataSet = new Object[combinations.size()][combinations.size()];
		int j = 0;
		for(String str : combinations){
			System.out.println(str);
			String[] payloadKeys = str.split(",");
			String payload = "";
			for(String key : payloadKeys){
				boolean multivalued = isMultivalued(key);
				payload = payload+key+"|"+getValue(key, multivalued)+";";
			}
		
			System.out.println(payload.substring(0, payload.length()-1));
			dataSet[j] = new String[]{payload.substring(0, payload.length()-1)};
			j++;
		}

		return dataSet;
	}

	public static String getCurrentSystemDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());

	}

	private String getValue(String key, boolean isMul){
		String returnString = "";
		String keyPrefix = keyPrefix(key);
		int max = randomNumberUptoLimit(5);
		if(isMul){
			String values = "";
			if(max==0)
				max=2;
			for(int i = 0; i < max; i++){
				if(key.equalsIgnoreCase("tags"))
					values = values + "\""+getTags()+"\":";
				else
					values = values + "\""+getPersonaTags()+"\":";
			}
			returnString = values.substring(0, values.length() - 1);
			return returnString;
		}
		else{
			String value = "";
			if(key.equalsIgnoreCase("id"))
				value = ""+getId(count);
			else if(key.equalsIgnoreCase("source_db_id"))
				value = ""+getSourceDbId();
			else if(key.equalsIgnoreCase("title"))
				value = ""+getTitle();
			else if(key.equalsIgnoreCase("description"))
				value = ""+getDesc();
			else if(key.equalsIgnoreCase("image"))
				value = ""+getImage();
			else if(key.equalsIgnoreCase("url"))
				value = keyPrefix("url")+geturl();
			else if(key.equalsIgnoreCase("content"))
				value = ""+getContent();
			else if(key.equalsIgnoreCase("content_source"))
				value = ""+getContSource();
			else if(key.equalsIgnoreCase("creator"))
				value = ""+getCreator();
			else if(key.equalsIgnoreCase("category"))
				value = ""+getCategory();
			else if(key.equalsIgnoreCase("tags"))
				value = ""+getTags();
			else if(key.equalsIgnoreCase("persona_tags"))
				value = ""+getPersonaTags();
			else if(key.equalsIgnoreCase("published"))
				value = ""+getPublished();
			else if(key.equalsIgnoreCase("is_published"))
				value = ""+getIsPublished();
			return value;
		}
	}
	
	public static int randomNumberUptoLimit(int limit){
		Random random = new Random();
		int index = random.nextInt(limit);
		return index;
	}
	
	private int getId(int currIndex){
		return ++currIndex;
	}
	
	private String getSourceDbId(){
		return "MyntraAutomation";
	}
	
	private String getTitle(){
		return "Myntra Automation_"+count;
	}
	
	private String getDesc(){
		return "Myntra Automation Add Content Description "+count;
	}
	
	private String getImage(){
		return "Myntra Automation Image Text!!!";
	}
	
	private int geturl(){
		return count;
	}
	
	private String getContent(){
		return "Myntra Automation Content "+count;
	}
	
	private String getContSource(){
		return "Myntra Automation Content Source "+count;
	}
	
	private String getCreator(){
		return "MyntraAutomationCreator_"+count;
	}
	
	private String getCategory(){
		return "MyntraAutomationCategory"+count;
	}
	
	private String getTags(){
		return "MyntraAutomationTags"+(++count);
	}
	
	private String getPersonaTags(){
		return "MyntraAutomationPersonaTags"+(++count);
	}
	
	private boolean getIsPublished(){
		if(count/2==0)
			return true;
		else
			return false;
	}
	
	private String getPublished(){
		return getCurrentSystemDateTime();
	}

	private String keyPrefix(String key){
		String toReturn = "";
		switch(key){
		case "id" : 
			toReturn = "ID_";
			break;
		case "source_db_id" : 
			toReturn = "SOURCE_DB_ID_";
			break;	
		case "title" : 
			toReturn = "TITLE_";
			break;
		case "url" : 
			toReturn = "http://localhost:8983/solr/#/Automation/";
			break;
		case "description" : 
			toReturn = "DESCRIPTION_";
			break;
		case "image" : 
			toReturn = "IMAGE_";
			break;
		case "content" : 
			toReturn = "CONTENT_";
			break;
		case "content_source" : 
			toReturn = "CONTENT_SOURCE_";
			break;
		case "creator" : 
			toReturn = "CREATOR_";
			break;
		case "category" : 
			toReturn = "CATEGORY_";
			break;
		case "tags" : 
			toReturn = "TAGS_";
			break;
		case "persona_tags" : 
			toReturn = "PERSONA_TAGS_";
			break;
		default :
			toReturn = "";
			break;
		}
		return toReturn;
	}
	private String getRequiredHeaders(){
		return "id,source_db_id,title,url,published,is_published";
	}

	
	private boolean isIndexed(String tagName){
		if(tagName.equals("id") || tagName.equals("title") || tagName.equals("published") || tagName.equals("description") 
				|| tagName.equals("content") || tagName.equals("content_source") || tagName.equals("creator") || tagName.equals("category") || tagName.equals("tags"))
			return true;
		else 
			return false;
	}

	private boolean isMultivalued(String tagName){
		if(tagName.equals("tags") || tagName.equals("persona_tags"))
			return true;
		else 
			return false;
	}


	private ArrayList getAllCombinations(Set<String> allHeaders){
		ArrayList<String> toReturn = new ArrayList<String>();
		String req = getRequiredHeaders();
		for (Set<String> s : powerSet(allHeaders)) {
			String temp = "";
			if(s.isEmpty())
				continue;
			for (String sec : s) {
				temp = temp+sec+",";
			}
			toReturn.add(req+","+temp.substring(0,temp.length()-1));
		}
		return toReturn;
	}

	private <T> Set<Set<T>> powerSet(Set<T> originalSet) {
		Set<Set<T>> sets = new HashSet<Set<T>>();
		if (originalSet.isEmpty()) {
			sets.add(new HashSet<T>());
			return sets;
		}
		List<T> list = new ArrayList<T>(originalSet);
		T head = list.get(0);
		Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
		for (Set<T> set : powerSet(rest)) {
			Set<T> newSet = new HashSet<T>();
			newSet.add(head);
			newSet.addAll(set);
			sets.add(newSet);
			sets.add(set);
		}		
		return sets;
	}

}
