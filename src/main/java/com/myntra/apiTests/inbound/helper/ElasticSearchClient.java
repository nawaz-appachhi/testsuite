package com.myntra.apiTests.inbound.helper;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;

public class ElasticSearchClient {

	public static Client client;
	static Logger log = Logger.getLogger(ElasticSearchClient.class);
	

	public Client getESClientConnection() {
		try {
			Svc executeSvc = SystemConfigProvider.getTopology().getService(SERVICE_TYPE.ES_CLIENT_SVC.toString());
			client = TransportClient.builder()
					.settings(Settings.builder().put(ESConstants.ESCLIENT.CLUSTER_NAME, ESConstants.ESCLIENT.CLUSTER_NAME_VAL).put(ESConstants.ESCLIENT.CLIENT_TRANSPORT_SNIFF, false))
					.build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(executeSvc.getIp()), executeSvc.getPort()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;

	}
	public SearchResponse ESExecutor(SearchRequestBuilder query){
		SearchResponse response=query.execute().actionGet();
//		System.out.println("Elastic Search Response:\n"+response);
		log.debug("\nPrinting the Elastic Search response for products :\n"
				+ response + "\n");
		return response;
	}

	public void closeESConnection() {
		client.close();
	}

}
