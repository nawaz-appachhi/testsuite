package com.myntra.apiTests.portalservices.lgpservices;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class CassandraHelper {
	public static CassandraHelper cassandraHelper;
	public static CassandraHelper getInstance(){
		if(cassandraHelper == null)
			cassandraHelper = new CassandraHelper();
		return cassandraHelper;
	}
	
	Session session;
	Cluster cluster;
	ResultSet result;
	
	public Session connect(String hostName,int portNumber){
	
		try {
		cluster = Cluster.builder().addContactPoint(hostName).withPort(portNumber).build();
		Metadata metadata = cluster.getMetadata();
		System.out.println(metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.println(host.getDatacenter());
			System.out.println(host.getAddress()); 
			System.out.println(host.getRack());
		}
		session = cluster.connect();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return session;
}
public void disconnect(){
	try{
		cluster.close();
	} catch (Exception e){
		e.printStackTrace();
	}
}
public ResultSet executeQuery(Session session,String query){
	try{
		result = session.execute(query);
	} catch(Exception e) {
		e.printStackTrace();
	}
	return result;
}
}
