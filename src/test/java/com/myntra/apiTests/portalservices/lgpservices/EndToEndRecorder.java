package com.myntra.apiTests.portalservices.lgpservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class EndToEndRecorder {
	public Map<String,Map<UserProperty,List<String>>> users;
	public Map<String,Map<ObjectProperty,List<String>>> lgpObjects;
	
	public EndToEndRecorder(){
		users=new HashMap<>();
		lgpObjects=new HashMap<>();
	}
	
	public void updateUserProperty(String uidx, UserProperty property,RecordKeeperAction action, String... value){
		switch(action){
		case ADD:
			System.out.println("it came here with uidx"+uidx);
			if(users.containsKey(uidx)){
				if(users.get(uidx).containsKey(property)){
					users.get(uidx).get(property).addAll(getValuesAsList(value));
				}else{
					users.get(uidx).put(property, getValuesAsList(value));
				}
			}else{
				ArrayList<String> values=new ArrayList<>();
				values.addAll(getValuesAsList(value));
				Map<UserProperty, List<String>> propertyMap = new HashMap<>();
				propertyMap.put(property, values);
				users.put(uidx, propertyMap);
			}
			break;
		case REMOVE:
			if(users.containsKey(uidx)){
				if(users.get(uidx).containsKey(property)){
					List<String> properties = users.get(uidx).get(property);
					for(String val:value){
						properties.remove(val);
					}
					if(properties.size()==0){
						users.get(uidx).remove(property);
					}
				}
			}
			break;
		case REPLACE:
			if(users.containsKey(uidx)){
				users.get(uidx).put(property, getValuesAsList(value));
			}else{
				users.put(uidx, (Map<UserProperty, List<String>>) new HashMap<UserProperty, List<String>>().put(property, getValuesAsList(value)));
			}
			break;
			
			
		}
		
	}
	
	public void updateObjectProperty(String objectId, ObjectProperty property,RecordKeeperAction action, String... value){
		switch(action){
		case ADD:
			if(lgpObjects.containsKey(objectId)){
				if(lgpObjects.get(objectId).containsKey(property)){
					lgpObjects.get(objectId).get(property).addAll(getValuesAsList(value));
				}else{
					lgpObjects.get(objectId).put(property, getValuesAsList(value));
				}
			}else{
				Map<ObjectProperty,List<String>> temp=new HashMap<ObjectProperty,List<String>>();
				temp.put(property, getValuesAsList(value));
				lgpObjects.put(objectId, (Map<ObjectProperty, List<String>>)temp);
			}
			break;
		case REMOVE:
			if(lgpObjects.containsKey(objectId)){
				if(lgpObjects.get(objectId).containsKey(property)){
					List<String> properties = lgpObjects.get(objectId).get(property);
					for(String val:value){
						properties.remove(properties.indexOf(val));
					}
					if(properties.size()==0){
						lgpObjects.get(objectId).remove(property);
					}
				}
			}
			break;
		case REPLACE:
			if(lgpObjects.containsKey(objectId)){
				lgpObjects.get(objectId).put(property, getValuesAsList(value));
			}else{
				lgpObjects.put(objectId, (Map<ObjectProperty, List<String>>) new HashMap<ObjectProperty, List<String>>().put(property, getValuesAsList(value)));
			}
			break;
			
		}
		
	}
	

	private List<String> getValuesAsList(String[] value){
		HashMap<ObjectProperty, List<String>> temp= new HashMap<ObjectProperty, List<String>>();
		List<String> valueList = new ArrayList<String>();
		for(String val:value){
			valueList.add(val);
		}
		return(valueList);
	}

	public List<String> getUserProperty(String uidx,UserProperty property){
		if((users.containsKey(uidx)) && (users.get(uidx).containsKey(property))){
			
				return(users.get(uidx).get(property));

		}
		return(null);
	}
	
	public List<String> getObjectProperty(String objectId,ObjectProperty property){
		if((lgpObjects.containsKey(objectId)) && (lgpObjects.get(objectId).containsKey(property))){
			
				return(lgpObjects.get(objectId).get(property));

		}
		return(null);
	}
	
	public Set<String> getUsers(){
		return(users.keySet());
	}
	
	public Set<String> getObjects(){
		return(lgpObjects.keySet());
	}

	
	public List<String> getObjectsWithAllProperies(ObjectProperty property,String... values){
		List<String> matchingObjects=new ArrayList<>();
		for(Entry<String, Map<ObjectProperty, List<String>>> entry:lgpObjects.entrySet()){
			if(entry.getValue().containsKey(property)){
				boolean flag=true;
				for(String value:values){
					if(!entry.getValue().get(property).contains(value)){
						flag=false;
						break;
					}
				}
				if(flag){
					matchingObjects.add(entry.getKey());
				}
			}
		}
		return(matchingObjects);
	}
	
	public List<String> getObjectsWithAnyPropery(ObjectProperty property,String... values){
		List<String> matchingObjects=new ArrayList<>();
		for(Entry<String, Map<ObjectProperty, List<String>>> entry:lgpObjects.entrySet()){
			if(entry.getValue().containsKey(property)){
				boolean flag=false;
				for(String value:values){
					if(entry.getValue().get(property).contains(value)){
						flag=true;
						break;
					}
				}
				if(values.length==0 && entry.getValue().get(property).isEmpty())
				{
					flag=true;
				}
				if(flag){
					matchingObjects.add(entry.getKey());
				}
				
			}
		}
		return(matchingObjects);
	}
	

}
