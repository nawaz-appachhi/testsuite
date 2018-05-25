package com.myntra.apiTests.common;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;

import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.topology.SystemConfigProvider;

/**
 * 
 * @author sandal.iqbal
 *
 */
public class ServiceHelper {

	Logger log = LoggerFactory.getLogger(ServiceHelper.class);

	ITestContext context;
	String environment;

	public ServiceHelper(ITestContext testContext) {
		context = testContext;
		environment = context.getSuite().getParameter("environment");
	}

	public String getVMSServiceURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("vmsURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("marketplacevms", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

	public String getVendorServiceURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("vendorServiceURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("vendoritemmaster", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

	public String getTermsServiceURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("termsURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("vendorterms", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

	public String getJobTrackerServiceURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("jobTrackerURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("jobtracker", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

	public String getUnityURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("unityURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("unity", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

	public String getVendorTermsUIURL() {
		String url = "";
		if (environment != null && environment.toLowerCase().equals("local")) {
			url = context.getSuite().getParameter("vendortermsuiURL");
		} else {
			Svc service = SystemConfigProvider.getTopology().getService("vendortermsui", "");
			try {
				url = service.getEndpoint().toURL().toString();
			} catch (MalformedURLException e) {
				log.debug(e.getMessage());
			}
		}
		return url;
	}

}
