package com.myntra.apiTests.portalservices.lgpservices;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraConnectHelper {
	
	
	public static Session getSession(String hostName, int port){
		
		Session session = null;
		
		try {
			Cluster cluster = Cluster.builder().addContactPoint(hostName).withPort(port).build();
			Metadata metadata = cluster.getMetadata();
			System.out.println(metadata.getClusterName());
			for (Host host : metadata.getAllHosts()) {
				System.out.println(host.getDatacenter());
				System.out.println(host.getAddress()); 
				System.out.println(host.getRack());
			}
			session = cluster.connect();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return session;
	}

}
