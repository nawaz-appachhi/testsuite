package com.myntra.apiTests.portalservices.lgpservices;

public interface DataStoreHelper {
public void connect(String hostName,int portNumber);
public void disconnect();
public void executeQuery();
}
