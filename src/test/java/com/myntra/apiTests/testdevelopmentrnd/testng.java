package com.myntra.apiTests.testdevelopmentrnd;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.services.drive.DriveScopes;;

public class testng {
	
	// path to local key file - /Users/mohit.jain/Downloads/My Project-1026ab85224b.p12
	
	/*public testng()
	{
	Configuration con1 = new Configuration("/Data/configuration");
	con1.SetProperty("environment", "Fox7");
	
	}*/
//	static Initialize init = new Initialize("/Data/configuration");
	
	/*@Test
	public void testNg()
	{
		Configuration con1 = new Configuration("/Data/configuration");
		con1.SetProperty("environment", "Fox7");
		
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.myntra.lordoftherings.MyntraListener"));
	//	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices.CartServiceTests"));
		classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices.IDPService"));
		
		
		test.setXmlClasses(classes) ;
		test.setClasses(classes);
		
		
		
	//	TestNG tng = new TestNG();
		
		
	//	ITestListener listn = new ITestListener();
		
		
	//	XmlClass xmlClass = new XmlClass();
	//    xmlClass.setClass(MyntraListener.class);
	    
	   
	//    test.setClasses(classes);
	//    test.setXmlClasses(classes);
		
		List<String> g = new ArrayList<String>();
	//	g.add("Sanity");
		g.add("Sanity");
		test.setIncludedGroups(g);
		
		
		
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		
		List<Class> c1 = new ArrayList<Class>();
		c1.add(MyntraListener.class);
		tng.setListenerClasses(c1);
		
	    
	    TestListenerAdapter tla = new TestListenerAdapter();
	    tng.addListener(MyListener.class);
	    tng.addListener(tla);
	  
		tng.run();

	}*/
	
	/*@Test
	public void testNg1()
	{
		Configuration con1 = new Configuration("/Data/configuration");
		con1.SetProperty("environment", "Fox7");
		
		XmlSuite suite = new XmlSuite();
		suite.setName("ReportGenerationSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("ReportGenerationTest");
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.myntra.lordoftherings.MyntraListener"));
//		classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices.IDPService"));
		
		List<String> b = getClassName();
		classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+b.get(1)));
		
		List<XmlPackage> packages = new ArrayList<XmlPackage>();
		packages.add(new XmlPackage("com.myntra.apiTests.test.regression.portalservices.*"));
		test.setPackages(packages);
		
		
		
		
		test.setXmlClasses(classes) ;
		test.setClasses(classes);
		
		
		
	//	TestNG tng = new TestNG();
		
		
	//	ITestListener listn = new ITestListener();
		
		
	//	XmlClass xmlClass = new XmlClass();
	//    xmlClass.setClass(MyntraListener.class);
	    
	   
	//    test.setClasses(classes);
	//    test.setXmlClasses(classes);
		
		List<String> g = new ArrayList<String>();
	//	g.add("Sanity");
		g.add("Sanity");
		test.setIncludedGroups(g);
		
		
		
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		
		List<Class> c1 = new ArrayList<Class>();
		c1.add(MyntraListener.class);
		tng.setListenerClasses(c1);
		
	    
	    TestListenerAdapter tla = new TestListenerAdapter();
	    tng.addListener(MyListener.class);
	    tng.addListener(tla);
	  
		tng.run();

	}*/
	
	

	/*private CellFeed useExistingSpredsheetAndWorksheet() throws IOException, ServiceException
	{
	//	HttpTransport transport = new NetHttpTransport();
	//	HttpTransport transport = GoogleTransport.create();
	//	GoogleHeaders headers = (GoogleHeaders) transport.defaultHeaders;
		 
	//	 GoogleHeaders headers = transport.createRequestFactory();
	//	 HttpHeaders headers = new HttpHeaders();
		 
	//	 headers.setApplicationName("google-youtubesample-1.0");
	//	 headers.gdataVersion = "2";
		
		
		 GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
		            .setServiceAccountId(confBean.getServiceAccountId()).setServiceAccountScopes("https://www.googleapis.com/auth/drive")
		            .setServiceAccountPrivateKeyFromP12File(new File("path to the P12File"))
		            .setServiceAccountUser("user@domain.com")
		            .build();

		    Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();
		
		SpreadsheetService service =   new SpreadsheetService("MySpreadsheetIntegration-v1");
	//	service.setProtocolVersion(SpreadsheetService.Versions.V3);
		
		String USERNAME = "mohitmj11@gmail.com";
        String PASSWORD = "ambition2011";
        
       ClientLogin authenticator = new ClientLogin();
        authenticator.authTokenType = "youtube";
        authenticator.username = USERNAME;
        authenticator.password = PASSWORD;
   //     authenticator.authenticate().setAuthorizationHeader(transport);
        
        service.setUserCredentials(USERNAME, PASSWORD);
        
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        
        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
             if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
             {
             	myspreadsheet = spreadsheet;
             }
         }   
         System.out.println("spreadsheet name = " +myspreadsheet);
        
         URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();   
         WorksheetFeed worksheetFeed= service.getFeed (
          	    worksheetFeedUrl, WorksheetFeed.class);
         
         List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
         WorksheetEntry myworksheet = new WorksheetEntry();
         
         for (WorksheetEntry worksheetEntry :worksheetEntrys)
         {
         	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase("Details"))
              {
         		 myworksheet = worksheetEntry;
              }
         }
         
         URL cellFeedUrl= myworksheet.getCellFeedUrl ();
         CellFeed cellFeed= service.getFeed (cellFeedUrl,
         	    CellFeed.class);
         
         return cellFeed;
		
	}*/
	
	/*@Test
	public void oauth  () throws AuthenticationException 
	{
		String clientID = "";
		String clientSecret = "";
		String SCOPES = "https://docs.google.com/feeds https://spreadsheets.google.com/feeds";
		
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		
		 oauthParameters.setOAuthConsumerKey(clientID);
		 
		 OAuthSigner signer = null;
	     oauthParameters.setOAuthConsumerSecret(clientSecret);
	     signer = (OAuthSigner) new OAuthHmacSha1Signer();
	     
	     GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper((com.google.gdata.client.authn.oauth.OAuthSigner) signer);
	     
	     oauthParameters.setScope(SCOPES);
	     
	     try {
	    	    oauthHelper.getUnauthorizedRequestToken(oauthParameters);
	    	   } catch (OAuthException e) {
	    	    // TODO Auto-generated catch block
	    	    e.printStackTrace();
	    	   }
		
	}*/
	
	private List<String> getServiceNamesList (int min_col, int max_col) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException
	{
		
		 ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/root/Desktop/JulyEORS/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);
        
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        
        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
             System.out.println(spreadsheet.getTitle().getPlainText());
             if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
             {
             	myspreadsheet = spreadsheet;
             	System.out.println("spreadsheet name in loop = " +myspreadsheet);
             }
         }
          
         System.out.println("spreadsheet name = " +myspreadsheet);
         System.out.println(myspreadsheet.getTitle().getPlainText());

         
         URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
         System.out.println("worksheet feed URL = " +worksheetFeedUrl);
         
         WorksheetFeed worksheetFeed= service.getFeed (
          	    worksheetFeedUrl, WorksheetFeed.class);
         
         List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
         WorksheetEntry myworksheet = new WorksheetEntry();
         
         for (WorksheetEntry worksheetEntry :worksheetEntrys)
         {
         	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
         	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase("WebServices Lists"))
              {
         		 myworksheet = worksheetEntry;
              	System.out.println("worksheet name in loop = " +myworksheet);
              }
         }
         
/*
     //    URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col=1&max-col=1").toURL();
         URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col=1&max-row=1").toURL();
         CellFeed cellfeed = service.getFeed(cellFeedUrl, CellFeed.class);
         List<String> mylist = new ArrayList<String>();
         int count = 0;
         for (CellEntry cell :  cellfeed.getEntries()) {

             mylist.add(cell.getCell().getInputValue());
             count++;
           }
         System.out.println("list prepared = "+mylist);
         System.out.println("total col counts = " + count);
         int tem = count / 4;
         System.out.println("total count of effective services = " + tem);
     //    System.out.println("heading = " + mylist.get(0)+"\n");
*/          
      //   int min_col=1,max_col=1;
         
        	 
        	 URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col="+min_col+"&max-col="+max_col).toURL();
             CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
             List<String> mylist1 = new ArrayList<String>();
      
             for (CellEntry cell :  cellfeed1.getEntries()) {

                 mylist1.add(cell.getCell().getInputValue());
                 
               }
        	 
             System.out.println("effective list = "+mylist1);
            /* min_col = min_col + 4;
             max_col = max_col + 4;*/
        	 
         
         
        /* for (int i=1;i<mylist.size();i++)
         System.out.println(mylist.get(i) + "\n");*/
         
         return mylist1;
	}
	/*	
	@Test public void executeTestCase () throws IOException, ServiceException, URISyntaxException
	{
		List<String> myList = getServiceNamesList();
		
		List<String> envList = new ArrayList<String>();
		envList.add("Production");
		envList.add("Fox7");
		envList.add("Functional");
		
		List<String> groupName = new ArrayList<String>();
		groupName.add("ProdSanity");
		groupName.add("Sanity");
		groupName.add("MiniRegression");
		groupName.add("Regression");
		groupName.add("ExhaustiveRegression");
		
		setHeaders(envList,groupName);
		
		triggersuite(myList,envList,groupName);
		
		CellFeed cellfeed = useExistingSpredsheetAndWorksheet();
	
		CellEntry cellEntry4 = new CellEntry (4, 1, myList.get(1));
		cellfeed.insert (cellEntry4);
		
		Configuration con1 = new Configuration("/Data/configuration");
		con1.SetProperty("environment",envList.get(0)); // Production
		
		XmlSuite suite = new XmlSuite();
		suite.setName("ReportGenerationSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("ReportGenerationTest");
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.myntra.lordoftherings.MyntraListener"));

		classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(1))); // style service
				
		test.setXmlClasses(classes) ;
		test.setClasses(classes);
				
		List<String> g = new ArrayList<String>();
		g.add(groupName.get(0));
		test.setIncludedGroups(g); 
		
		System.out.println("Executing suite- "+myList.get(1)+" group - "+groupName.get(0)+ "env - " + envList.get(0) );
		
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		
		List<Class> c1 = new ArrayList<Class>();
		c1.add(MyntraListener.class);
		tng.setListenerClasses(c1);
	  
		tng.run();
		
		triggersuite(myList,envList,groupName);
		
		CellFeed cellfeed = useExistingSpredsheetAndWorksheet();
		
		MyntraListener mylisten = new MyntraListener();
		List<Integer> status = mylisten.printresults();
		System.out.println("In test case... executed..." + status.get(0));
		System.out.println("In test case... passed..." + status.get(1));
		System.out.println("In test case... failed..." + status.get(2));
		
       	 CellEntry cellEntry7= new CellEntry (4, 2, status.get(0).toString());
            cellfeed.insert (cellEntry7);
            
            CellEntry cellEntry8= new CellEntry (4, 3, status.get(1).toString());
            cellfeed.insert (cellEntry8);
            
            CellEntry cellEntry9= new CellEntry (4, 4, status.get(2).toString());
            cellfeed.insert (cellEntry9);
        
		}*/
		
	/*private void triggersuite(List<String>myList,List<String>envList,List<String>groupName) throws IOException, ServiceException
	{
		
		CellFeed cellfeed = useExistingSpredsheetAndWorksheet();
		

		
		for (int j =0;j<envList.size();j++)
		{
		Configuration con1 = new Configuration("/Data/configuration");
		con1.SetProperty("environment",envList.get(j)); 
		
		for(int i=1;i<myList.size();i++)
		{
			int row_count_suite = i +3;
			int fixed_col_count = 2;
			for (int h =1 ; h<groupName.size();h++)
			{
		//enter suit name
		CellEntry cellEntry4 = new CellEntry (row_count_suite, 1, myList.get(i));
		cellfeed.insert (cellEntry4);
	
		XmlSuite suite = new XmlSuite();
		suite.setName("ReportGenerationSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("ReportGenerationTest");
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.myntra.lordoftherings.MyntraListener"));

		classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(i))); // style service
				
		test.setXmlClasses(classes) ;
		test.setClasses(classes);
		
	    if(envList.get(j).equals("Production"))
	    {
		List<String> g = new ArrayList<String>();
		g.add(groupName.get(0));
		test.setIncludedGroups(g); 
	    }
	    else
	    {
	    	List<String> g = new ArrayList<String>();
			g.add(groupName.get(h));
			test.setIncludedGroups(g); 
	    	
	    }
	    
	    String id = generateRandomId1();
	    
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		
		List<Class> c1 = new ArrayList<Class>();
		c1.add(MyntraListener.class);
		tng.setListenerClasses(c1);
	  
		tng.run();
		
//		List <Integer> status = displayResults();

		
//		 CellEntry cellEntry7= new CellEntry (row_count_suite, fixed_col_count, status.get(0).toString());
 //        cellfeed.insert (cellEntry7);
         
         fixed_col_count = fixed_col_count +1;
         
  //       CellEntry cellEntry8= new CellEntry (row_count_suite, fixed_col_count, status.get(1).toString());
   //      cellfeed.insert (cellEntry8);
         
         fixed_col_count = fixed_col_count + 1;
         
   //      CellEntry cellEntry9= new CellEntry (row_count_suite, fixed_col_count, status.get(2).toString());
    //     cellfeed.insert (cellEntry9);
         
         fixed_col_count = fixed_col_count + 1;
		}
		
		
		
				
		}
	}
	}*/
	

	/*private String generateRandomId1()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}*/
	
	
	/*private List<Integer> displayResults() throws IOException, ServiceException
	{
       // CellFeed cellfeed = useExistingSpredsheetAndWorksheet();
		
		MyntraListener mylisten = new MyntraListener();
	//	List<Integer> status = mylisten.printresults();
	//	System.out.println("In test case... executed..." + status.get(0));
	//	System.out.println("In test case... passed..." + status.get(1));
	//	System.out.println("In test case... failed..." + status.get(2));
		
       	 CellEntry cellEntry7= new CellEntry (4, 2, status.get(0).toString());
            cellfeed.insert (cellEntry7);
            
            CellEntry cellEntry8= new CellEntry (4, 3, status.get(1).toString());
            cellfeed.insert (cellEntry8);
            
            CellEntry cellEntry9= new CellEntry (4, 4, status.get(2).toString());
            cellfeed.insert (cellEntry9);
		
	//	return status;
	}*/
	
	/*@Test
	public void modifiedTest() throws IOException, ServiceException, URISyntaxException
	{
		LinkedHashMap<String,String> mapping = new LinkedHashMap<String,String>();
		mapping.put("Production", "ProdSanity");
		mapping.put("Fox7", "Sanity");
		
		List<String> myList = getServiceNamesList();
		
		
		Configuration con1 = new Configuration("/Data/configuration");
		con1.SetProperty("environment","Production"); 
		
		XmlSuite suite = new XmlSuite();
		suite.setName("ReportGenerationSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("ReportGenerationTest");
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		
		List<XmlClass> classes = new ArrayList<XmlClass>();
     	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(2))); // style service
				
		test.setXmlClasses(classes) ;
		test.setClasses(classes);
		
		List<String> g = new ArrayList<String>();
		g.add("ProdSanity");
		test.setIncludedGroups(g);
		
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		
		List<Class> c1 = new ArrayList<Class>();
		c1.add(MyListener.class);
		tng.setListenerClasses(c1);
	  
		tng.run();
		
		MyListener mylisten = new MyListener();
		List<Integer> status = mylisten.printresults();
		System.out.println("In test case... executed..." + status.get(0));
		System.out.println("In test case... passed..." + status.get(1));
		System.out.println("In test case... failed..." + status.get(2));
		
		// Second execution
		
		
		Configuration con2 = new Configuration("/Data/configuration");
		con2.SetProperty("environment","Fox7"); 
		
		XmlSuite suite1 = new XmlSuite();
		suite1.setName("ReportGenerationSuite");
		 
		XmlTest test1 = new XmlTest(suite1);
		test1.setName("ReportGenerationTest");
		
		List<XmlSuite> suites1 = new ArrayList<XmlSuite>();
		suites1.add(suite1);
		
		List<XmlClass> classes1 = new ArrayList<XmlClass>();
	    classes1.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(2))); // style service
				
		test1.setXmlClasses(classes1) ;
		test1.setClasses(classes1);
		
		List<String> g1 = new ArrayList<String>();
		g1.add("Sanity");
		test1.setIncludedGroups(g1);
		
		TestNG tng1 = new TestNG();
		tng1.setXmlSuites(suites1);
		
		List<Class> c2 = new ArrayList<Class>();
		c2.add(MyListener.class);
		tng1.setListenerClasses(c2);
	  
		tng1.run();
		MyntraListener mylisten1 = new MyntraListener();
		List<Integer> status1 = mylisten1.printresults();
		System.out.println("In test case... executed second..." + status1.get(0));
		System.out.println("In test case... passed second..." + status1.get(1));
		System.out.println("In test case... failed second..." + status1.get(2));
		
		
		
	}*/
	
	/*@Test
	public void newLogic() throws IOException, ServiceException, URISyntaxException, AuthenticationException, GeneralSecurityException
	{
		
		
	//	CellFeed cellfeed = newlogicspreadsheet();
		
		int tem = getcolcount();
		 int min_col=1,max_col=1;
		 int row_count_suite = 4, col_count_suite = 1;
		for (int k =1;k<= tem; k++)
		{
		List<String> myList = getServiceNamesList(min_col,max_col);
		String workSheetName = "Details-"+myList.get(0);
	//	CellFeed cellfeed = createWorkSheet(workSheetName);
		CellFeed cellfeed = readWorksheet(workSheetName);
		
		List<String> Environ = new ArrayList<String>();
		Environ.add("Fox7");
		Environ.add("Functional");
		
		List<String> runType = new ArrayList<String>();
		runType.add("Sanity");
		runType.add("MiniRegression");
		runType.add("Regression");
		runType.add("ExhaustiveRegression");
		
		Multimap<String,String> multimap1 = createMap (Environ,runType);
		
	//	newSetHeaders(multimap1);
		newSetHeaders(multimap1,cellfeed);
		
		//int row_count_suite = 4, col_count_suite = 1;
		for (int i=1; i< myList.size();i++)
		{
			MyListener listen = new MyListener();
			listen.setSheetName(workSheetName);
			listen.restRowAndCol(row_count_suite,2);
			
			CellEntry cellEntry = new CellEntry (row_count_suite, col_count_suite, myList.get(i));
			cellfeed.insert (cellEntry);
					
		Set keySet = multimap1.keySet();
		Iterator keyIterator = keySet.iterator();
		while (keyIterator.hasNext())
		{
			String key = (String) keyIterator.next();
			List values = (List) multimap1.get(key);
			System.out.println("values size is:" + values.size());
			for (int j=0;j<values.size();j++)
			{
				System.out.println(key+":value:" +values.get(j));
				
				Initialize init3 = new Initialize("/Data/configuration");
				init3.Configurations.properties.SetProperty("environment",key);
		
				Configuration con1 = new Configuration("/Data/configuration");
				con1.SetProperty("environment",key); 
				
				
				
				XmlSuite suite = new XmlSuite();
				suite.setName("ReportGenerationSuite");
				 
				XmlTest test = new XmlTest(suite);
				test.setName("ReportGenerationTest");
				
				List<XmlSuite> suites = new ArrayList<XmlSuite>();
				suites.add(suite);
				
				List<XmlClass> classes = new ArrayList<XmlClass>();
				String pname = getPackageName (myList.get(0));
				
				classes.add(new XmlClass(pname+"."+myList.get(i))); 
		     //	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(i))); // style service
						
				test.setXmlClasses(classes) ;
				test.setClasses(classes);
				
				List<String> g = new ArrayList<String>();
				g.add((String) values.get(j));
				test.setIncludedGroups(g);
				
				TestNG tng = new TestNG();
				tng.setXmlSuites(suites);
				
				List<Class> c1 = new ArrayList<Class>();
				c1.add(MyListener.class);
				tng.setListenerClasses(c1);
			  
				tng.run();
				
			} // end of for loop
			
		}
		
		row_count_suite = row_count_suite + 1;
		}
		
		min_col = min_col + 4;
        max_col = max_col + 4;
        row_count_suite = 4;
    //    row_count_suite = row_count_suite + 1;
		}	
	}*/
	
	
	@Test(groups="onlyProd")
	public void onlyProd() throws IOException, ServiceException, URISyntaxException, AuthenticationException, GeneralSecurityException
	{
		int tem = getcolcount();
		int min_col=1,max_col=1;
		int row_count_suite = 4, col_count_suite = 1;
		
		
		for (int k =1;k<= tem; k++)
		{
		List<String> myList = getServiceNamesList(min_col,max_col);
		String workSheetName = "Details-"+myList.get(0);
		CellFeed cellfeed = readWorksheet(workSheetName);
		
		String environ = "Production";
		String runtype = "ProdSanity";
		
		for (int i=1; i< myList.size();i++)
		{
			MyListener listen = new MyListener();
			listen.setSheetName(workSheetName);
		//	listen.restRowAndCol_onlyProd(row_count_suite,col_count_suite);
			listen.restRowAndCol(row_count_suite,2);
			
			CellEntry cellEntry = new CellEntry (row_count_suite, col_count_suite, myList.get(i));
			cellfeed.insert (cellEntry);
			
			Initialize init3 = new Initialize("/Data/configuration");
			init3.Configurations.properties.SetProperty("environment","Production");
			
			XmlSuite suite = new XmlSuite();
			suite.setName("ReportGenerationSuite");
			 
			XmlTest test = new XmlTest(suite);
			test.setName("ReportGenerationTest");
			
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			
			List<XmlClass> classes = new ArrayList<XmlClass>();
			String pname = getPackageName (myList.get(0));
			
			classes.add(new XmlClass(pname+"."+myList.get(i)));
			
			test.setXmlClasses(classes) ;
			test.setClasses(classes);
			
			List<String> g = new ArrayList<String>();
			g.add("ProdSanity");
			test.setIncludedGroups(g);
			
			TestNG tng = new TestNG();
			tng.setXmlSuites(suites);
			
			List<Class> c1 = new ArrayList<Class>();
			c1.add(MyListener.class);
			tng.setListenerClasses(c1);
		  
			tng.run();
			row_count_suite = row_count_suite + 1;
		}	
		
		min_col = min_col + 4;
        max_col = max_col + 4;
        row_count_suite = 4;
		
		}
		
		
		/*
		String write_workSheetName ="Details-All";
		String workSheetName="";
		// copy and write - loop through entire sheets
		for (int k =1;k<= tem; k++)
		{
			//String write_workSheetName ="Details-All";
			List<String> myList5 = getServiceNamesList(min_col,max_col);
			String workSheetName1 = "Details-"+myList5.get(0);
			workSheetName=workSheetName1;
			//CellFeed cellfeed = readWorksheet(workSheetName);
			for(int j=1;j<=4;j++)
			{
				int row = 4;
				List<String>data = readEntireSheet(4,2,2,workSheetName); //min-col, max-col
				System.out.println("data="+data);
				
				CellFeed cellfeed = readWorksheet(write_workSheetName);
				for (int i=0; i< data.size();i++)
					{
					int col=1;
					CellEntry cellEntry = new CellEntry (row, col, data.get(i));
					cellfeed.insert (cellEntry);
					row=row+1;
					} 
			}
			min_col = min_col + 4;
	        max_col = max_col + 4;
		}*/
	}
	
	@Test(groups="onlyFox")
	public void onlyFox() throws IOException, ServiceException, URISyntaxException, AuthenticationException, GeneralSecurityException
	{
		int tem = getcolcount();
		 int min_col=1,max_col=1;
		 int row_count_suite = 4, col_count_suite = 1;
		for (int k =1;k<= tem; k++)
		{
		List<String> myList = getServiceNamesList(min_col,max_col);
		String workSheetName = "Details-"+myList.get(0);
		CellFeed cellfeed = readWorksheet(workSheetName);
		
		String Environ ="Fox7";
		
		List<String> runType = new ArrayList<String>();
		runType.add("Sanity");
		runType.add("MiniRegression");
		runType.add("Regression");
		runType.add("ExhaustiveRegression");
		
		//Multimap multimap2 = createMap_onlyFox (Environ,runType);
		for (int i=1; i< myList.size();i++)
		{
			MyListener listen = new MyListener();
			listen.setSheetName(workSheetName);
			listen.restRowAndCol_onlyFox(row_count_suite,5);
			
			CellEntry cellEntry = new CellEntry (row_count_suite, col_count_suite, myList.get(i));
			cellfeed.insert (cellEntry);
			
			for (int j=0;j<runType.size();j++)
			{
				System.out.println("Environment -"+Environ+":runtype-" +runType.get(j));
				Initialize init3 = new Initialize("/Data/configuration");
				init3.Configurations.properties.SetProperty("environment",Environ);
				
				XmlSuite suite = new XmlSuite();
				suite.setName("ReportGenerationSuite");
				 
				XmlTest test = new XmlTest(suite);
				test.setName("ReportGenerationTest");
				
				List<XmlSuite> suites = new ArrayList<XmlSuite>();
				suites.add(suite);
				
				List<XmlClass> classes = new ArrayList<XmlClass>();
				String pname = getPackageName (myList.get(0));
				
				classes.add(new XmlClass(pname+"."+myList.get(i))); 
		     //	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(i))); // style service
						
				test.setXmlClasses(classes) ;
				test.setClasses(classes);
				
				List<String> g = new ArrayList<String>();
				g.add((String) runType.get(j));
				test.setIncludedGroups(g);
				
				TestNG tng = new TestNG();
				tng.setXmlSuites(suites);
				
				List<Class> c1 = new ArrayList<Class>();
				c1.add(MyListener.class);
				tng.setListenerClasses(c1);
			  
				tng.run();	
			}	
			row_count_suite = row_count_suite + 1;
		}
		
		min_col = min_col + 4;
        max_col = max_col + 4;
        row_count_suite = 4;
		
		}
	}
	
	@Test(groups="onlyQA")
	public void onlyQA() throws IOException, ServiceException, URISyntaxException, AuthenticationException, GeneralSecurityException
	{
		int tem = getcolcount();
		 int min_col=1,max_col=1;
		 int row_count_suite = 4, col_count_suite = 1;
		for (int k =1;k<= tem; k++)
		{
		List<String> myList = getServiceNamesList(min_col,max_col);
		String workSheetName = "Details-"+myList.get(0);
		CellFeed cellfeed = readWorksheet(workSheetName);
		
		String Environ ="Functional";
		
		List<String> runType = new ArrayList<String>();
		runType.add("Sanity");
		runType.add("MiniRegression");
		runType.add("Regression");
		runType.add("ExhaustiveRegression");
		
		//Multimap multimap2 = createMap_onlyFox (Environ,runType);
		for (int i=1; i< myList.size();i++)
		{
			MyListener listen = new MyListener();
			listen.setSheetName(workSheetName);
			listen.restRowAndCol_onlyQA(row_count_suite,17);
			
			CellEntry cellEntry = new CellEntry (row_count_suite, col_count_suite, myList.get(i));
			cellfeed.insert (cellEntry);
			
			for (int j=0;j<runType.size();j++)
			{
				System.out.println("Environment -"+Environ+":runtype-" +runType.get(j));
				Initialize init3 = new Initialize("/Data/configuration");
				init3.Configurations.properties.SetProperty("environment",Environ);
				
				XmlSuite suite = new XmlSuite();
				suite.setName("ReportGenerationSuite");
				 
				XmlTest test = new XmlTest(suite);
				test.setName("ReportGenerationTest");
				
				List<XmlSuite> suites = new ArrayList<XmlSuite>();
				suites.add(suite);
				
				List<XmlClass> classes = new ArrayList<XmlClass>();
				String pname = getPackageName (myList.get(0));
				
				classes.add(new XmlClass(pname+"."+myList.get(i))); 
		     //	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(i))); // style service
						
				test.setXmlClasses(classes) ;
				test.setClasses(classes);
				
				List<String> g = new ArrayList<String>();
				g.add((String) runType.get(j));
				test.setIncludedGroups(g);
				
				TestNG tng = new TestNG();
				tng.setXmlSuites(suites);
				
				List<Class> c1 = new ArrayList<Class>();
				c1.add(MyListener.class);
				tng.setListenerClasses(c1);
			  
				tng.run();	
			}	
			row_count_suite = row_count_suite + 1;
		}
		
		min_col = min_col + 4;
        max_col = max_col + 4;
        row_count_suite = 4;
		
		}
	}
	
	@Test(groups="onDemand")
	public void onDemand() throws IOException, ServiceException, URISyntaxException, AuthenticationException, GeneralSecurityException
	{
	
		List<String> myList = readCols();
		System.out.println(myList);
		
	//	String Environ ="Functional";
				MyListener listen = new MyListener();
				listen.setSheetName("OnDemand");
				listen.restRowAndCol_onDemand(2,5);
	//			System.out.println("Environment -"+Environ+":runtype-" +runType.get(j));
				Initialize init3 = new Initialize("/Data/configuration");
				init3.Configurations.properties.SetProperty("environment",myList.get(2));
				
				XmlSuite suite = new XmlSuite();
				suite.setName("ReportGenerationSuite");
				 
				XmlTest test = new XmlTest(suite);
				test.setName("ReportGenerationTest");
				
				List<XmlSuite> suites = new ArrayList<XmlSuite>();
				suites.add(suite);
				
				List<XmlClass> classes = new ArrayList<XmlClass>();
				String pname = getPackageName (myList.get(0));
				
				classes.add(new XmlClass(pname+"."+myList.get(1))); 
		     //	classes.add(new XmlClass("com.myntra.apiTests.test.regression.portalservices."+myList.get(i))); // style service
						
				test.setXmlClasses(classes) ;
				test.setClasses(classes);
				
				List<String> g = new ArrayList<String>();
				g.add((String) myList.get(3));
				test.setIncludedGroups(g);
				
				TestNG tng = new TestNG();
				tng.setXmlSuites(suites);
				
				List<Class> c1 = new ArrayList<Class>();
				c1.add(MyListener.class);
				tng.setListenerClasses(c1);
			  
				tng.run();	
	//		}	
//			row_count_suite = row_count_suite + 1;
	//	}
		
//		min_col = min_col + 4;
 //       max_col = max_col + 4;
  //      row_count_suite = 4;
		
		//}
	}
	
	/*@Test
	public void data_prod() throws GeneralSecurityException, IOException, ServiceException, URISyntaxException {
	
		int tem = getcolcount();
		int min_col=1,max_col=1;
		int mincol=1,maxcol=1;
		for (int i=0;i<tem;i++) 
		{
			List<String> myList = getServiceNamesList(min_col,max_col);
			String read_workSheetName = "Details-"+myList.get(0).trim()	;
			
			for (int j=0;j<3;j++)
			{
			List<String>data = readEntireSheet(1,mincol,maxcol,read_workSheetName);
			System.out.println("data="+data);
			
			mincol=mincol+1;
			maxcol=maxcol+1;
			}
		}
	
	
	}*/
	
	private List<String> readCols() throws GeneralSecurityException, IOException, ServiceException, URISyntaxException {
		
		ArrayList scopes = new ArrayList();
        scopes.add(0, DriveScopes.DRIVE_FILE);
        scopes.add(1, "https://spreadsheets.google.com/feeds");
        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(new NetHttpTransport())
            .setJsonFactory(new JacksonFactory())
            .setServiceAccountId(
                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
            .setServiceAccountPrivateKeyFromP12File(new File("/root/Desktop/JulyEORS/My Project-1026ab85224b.p12"))
            .setServiceAccountScopes(scopes).build();

        credential.refreshToken();

        SpreadsheetService service = new SpreadsheetService("tmp");
        service.setOAuth2Credentials(credential);
    
    URL SPREADSHEET_FEED_URL = new URL(
            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
    
    SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
    for (SpreadsheetEntry spreadsheet : spreadsheets) {
         System.out.println(spreadsheet.getTitle().getPlainText());
         if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
         {
         	myspreadsheet = spreadsheet;
         	System.out.println("spreadsheet name in loop = " +myspreadsheet);
         }
     }
      
     System.out.println("spreadsheet name = " +myspreadsheet);
     System.out.println(myspreadsheet.getTitle().getPlainText());

     
     URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
     System.out.println("worksheet feed URL = " +worksheetFeedUrl);
     
     WorksheetFeed worksheetFeed= service.getFeed (
      	    worksheetFeedUrl, WorksheetFeed.class);
     
     List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
     WorksheetEntry myworksheet = new WorksheetEntry();
     
     for (WorksheetEntry worksheetEntry :worksheetEntrys)
     {
     	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
     	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase("OnDemand"))
          {
     		 myworksheet = worksheetEntry;
          	System.out.println("worksheet name in loop = " +myworksheet);
          }
     }
     

 //    URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col=1&max-col=1").toURL();
     URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=2&min-col=1&max-col=4").toURL();
     CellFeed cellfeed = service.getFeed(cellFeedUrl, CellFeed.class);
     List<String> mylist = new ArrayList<String>();
  //   int count = 0;
     for (CellEntry cell :  cellfeed.getEntries()) {

         mylist.add(cell.getCell().getInputValue());
    //     count++;
       }
     System.out.println("list prepared = "+mylist);
//     System.out.println("total col counts = " + count);
 //    int tem = count / 4;
 //    System.out.println("total count of effective services = " + tem);
		
     return mylist;
	}
	
	private int getcolcount() throws GeneralSecurityException, IOException, ServiceException, URISyntaxException {
		
		ArrayList scopes = new ArrayList();
        scopes.add(0, DriveScopes.DRIVE_FILE);
        scopes.add(1, "https://spreadsheets.google.com/feeds");
        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(new NetHttpTransport())
            .setJsonFactory(new JacksonFactory())
            .setServiceAccountId(
                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
            .setServiceAccountPrivateKeyFromP12File(new File("/root/Desktop/JulyEORS/My Project-1026ab85224b.p12"))
            .setServiceAccountScopes(scopes).build();

        credential.refreshToken();

        SpreadsheetService service = new SpreadsheetService("tmp");
        service.setOAuth2Credentials(credential);
    
    URL SPREADSHEET_FEED_URL = new URL(
            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
    
    SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
    for (SpreadsheetEntry spreadsheet : spreadsheets) {
         System.out.println(spreadsheet.getTitle().getPlainText());
         if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
         {
         	myspreadsheet = spreadsheet;
         	System.out.println("spreadsheet name in loop = " +myspreadsheet);
         }
     }
      
     System.out.println("spreadsheet name = " +myspreadsheet);
     System.out.println(myspreadsheet.getTitle().getPlainText());

     
     URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
     System.out.println("worksheet feed URL = " +worksheetFeedUrl);
     
     WorksheetFeed worksheetFeed= service.getFeed (
      	    worksheetFeedUrl, WorksheetFeed.class);
     
     List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
     WorksheetEntry myworksheet = new WorksheetEntry();
     
     for (WorksheetEntry worksheetEntry :worksheetEntrys)
     {
     	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
     	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase("WebServices Lists"))
          {
     		 myworksheet = worksheetEntry;
          	System.out.println("worksheet name in loop = " +myworksheet);
          }
     }
     

 //    URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col=1&max-col=1").toURL();
     URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=1&min-col=1&max-row=1").toURL();
     CellFeed cellfeed = service.getFeed(cellFeedUrl, CellFeed.class);
     List<String> mylist = new ArrayList<String>();
     int count = 0;
     for (CellEntry cell :  cellfeed.getEntries()) {

         mylist.add(cell.getCell().getInputValue());
         count++;
       }
     System.out.println("list prepared = "+mylist);
     System.out.println("total col counts = " + count);
     int tem = count / 4;
     System.out.println("total count of effective services = " + tem);
		
     return tem;
	}

	private String getPackageName (String temp)
	{
		String initial = temp;
		String pname = "";
		
		switch (initial)
		{
			case "PortalServices" : 
			pname = "com.myntra.apiTests.test.regression.portalservices";
			break;
			
			case "RevLabsServices":
			pname = "com.myntra.apiTests.test.regression.portalservices";
			break;
			
			case "MobileAppServices":
			pname =  "com.myntra.apiTests.test.regression.appservices";
			break;
			
			case "MarketPlaceServices":
			pname = "com.myntra.apiTests.test.regression.marketplace";
			break;
			
			case "CrmServices":
			pname = "com.myntra.apiTests.test.regression.crmservices";	
			break;
			
			case "ERPServices":
			pname = "com.myntra.apiTests.test.regression.erpservices";	
			break;
			
			case "OmsServices":
			pname = "com.myntra.apiTests.test.regression.erpservices.oms";	
			break;
			
			case "LmsServices":
			pname = "com.myntra.apiTests.test.regression.erpservices.lms";	
			break;
				
			case "WmsServices":
			pname = "com.myntra.apiTests.test.regression.erpservices.wms";	
			break;
				
		}
		return pname;
	}
	
	/* @Test
	    public void modified () throws IOException, ServiceException, GeneralSecurityException
	    {
	    	
	    	ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/Users/mohit.jain/Downloads/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);

	        // Define the URL to request. This should never change.
	        URL SPREADSHEET_FEED_URL = new URL(
	            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

	        // Make a request to the API and get all spreadsheets.
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
	            SpreadsheetFeed.class);
	        
	        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
	        
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

	        // Iterate through all of the spreadsheets returned
	        int i = 0;
	        for (SpreadsheetEntry spreadsheet : spreadsheets) {
	        	 System.out.println(spreadsheet.getTitle().getPlainText());
	        	 myspreadsheet = spreadsheet;
	          ++i;
	          System.out.println(spreadsheet.getId());
	        }
	        System.out.println("Done " + i);  
	        
	        WorksheetEntry worksheet = new WorksheetEntry();
	        worksheet.setTitle(new PlainTextConstruct("xyz worksheet"));
	        worksheet.setColCount(10);
	        worksheet.setRowCount(20);
	        
	        URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
	        System.out.println("worksheet feed URL = " +worksheetFeedUrl);
	        WorksheetEntry insert = service.insert(worksheetFeedUrl, worksheet);
	      }*/
	
	/* private CellFeed newlogicspreadsheet() throws IOException, ServiceException, GeneralSecurityException
	 {
		 
		 ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/Users/mohit.jain/Downloads/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);
		 
		 
	        URL SPREADSHEET_FEED_URL = new URL(
		            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	        
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
	        
	        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
	        for (SpreadsheetEntry spreadsheet : spreadsheets) {
	             if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
	             {
	             	myspreadsheet = spreadsheet;
	             }
	         }   
	         System.out.println("spreadsheet name = " +myspreadsheet);
	        
	         URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();   
	         WorksheetFeed worksheetFeed= service.getFeed (
	          	    worksheetFeedUrl, WorksheetFeed.class);
	         
	         List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
	          
	         WorksheetEntry myworksheet = new WorksheetEntry();
	         
	         for (WorksheetEntry worksheetEntry :worksheetEntrys)
	         {
	         	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase("Details"))
	              {
	         		 myworksheet = worksheetEntry;
	              }
	         }
	         
	         URL cellFeedUrl= myworksheet.getCellFeedUrl ();
	         CellFeed cellFeed= service.getFeed (cellFeedUrl,
	         	    CellFeed.class);
	         
	         return cellFeed;
	        
	 }*/
	 
	 private CellFeed readWorksheet(String wSheetName) throws IOException, ServiceException, GeneralSecurityException
	 {
		 
		 ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/root/Desktop/JulyEORS/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);
		 
		 
	        URL SPREADSHEET_FEED_URL = new URL(
		            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	        
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
	        
	        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
	        for (SpreadsheetEntry spreadsheet : spreadsheets) {
	             if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
	             {
	             	myspreadsheet = spreadsheet;
	             }
	         }   
	         System.out.println("spreadsheet name = " +myspreadsheet);
	        
	         URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();   
	         WorksheetFeed worksheetFeed= service.getFeed (
	          	    worksheetFeedUrl, WorksheetFeed.class);
	         
	         List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
	          
	         WorksheetEntry myworksheet = new WorksheetEntry();
	         
	         for (WorksheetEntry worksheetEntry :worksheetEntrys)
	         {
	         	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase(wSheetName))
	              {
	         		 myworksheet = worksheetEntry;
	              }
	         }
	         
	         URL cellFeedUrl= myworksheet.getCellFeedUrl ();
	         CellFeed cellFeed= service.getFeed (cellFeedUrl,
	         	    CellFeed.class);
	         
	         return cellFeed;
	        
	 }
	 
	 
	
	/* private CellFeed createWorkSheet(String wsheet) throws IOException, ServiceException, GeneralSecurityException
	 {
		 
		 
		 ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/Users/mohit.jain/Downloads/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);
		 
		 
	        URL SPREADSHEET_FEED_URL = new URL(
		            "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	        
	        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
	        
	        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
	        for (SpreadsheetEntry spreadsheet : spreadsheets) {
	             if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("Reports"))
	             {
	             	myspreadsheet = spreadsheet;
	             }
	         }   
	         System.out.println("spreadsheet name = " +myspreadsheet);
	        
	         URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();   
	         WorksheetFeed worksheetFeed= service.getFeed (
	          	    worksheetFeedUrl, WorksheetFeed.class);
	         
	         List <WorksheetEntry> worksheetEntrys= worksheetFeed.getEntries ();
	         
	         	
	         	WorksheetEntry worksheet = new WorksheetEntry();
		        worksheet.setTitle(new PlainTextConstruct(wsheet));
		        worksheet.setColCount(1000);
		        worksheet.setRowCount(50);
		        
		        URL worksheetFeedUrl1 = myspreadsheet.getWorksheetFeedUrl();
		        System.out.println("worksheet feed URL = " +worksheetFeedUrl1);
		        WorksheetEntry insert = service.insert(worksheetFeedUrl1, worksheet);
	          
	         WorksheetEntry myworksheet = new WorksheetEntry();
	         
	         for (WorksheetEntry worksheetEntry :worksheetEntrys)
	         {
	         	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase(wsheet))
	              {
	         		 myworksheet = worksheetEntry;
	              }
	         }
	         
	         URL cellFeedUrl= myworksheet.getCellFeedUrl ();
	         CellFeed cellFeed= service.getFeed (cellFeedUrl,
	         	    CellFeed.class);
	         
	         return cellFeed;	 
		 
	 }*/
	
	private Multimap<String,String> createMap (List<String>Environ,List<String>runType)
	{
		Multimap<String,String> multimap = LinkedListMultimap.create();
		
		multimap.put("Production", "ProdSanity");
		
		for (int i =0; i< Environ.size();i++)
		{
			for ( int j =0; j<runType.size(); j++)
			{
				multimap.put(Environ.get(i), runType.get(j));
			}
		}
		
		System.out.println("map =" +multimap);		
		return multimap;
	}
	
	/*private Multimap createMap_onlyFox (String Environ,List<String>runType)
	{
		MultiMap map = new MultiHashMap();
	    map.put("ONE","TEST");  
	    map.put("ONE",1);
	 //   map.put("ONE", result);
		
		System.out.println("map =" +map);		
		return (Multimap) map;
	}*/
	
	/*public static void main (String[] args)
	{
		List<String> l1 = new ArrayList();
		l1.add("sanity");
		l1.add("miniregressiom");
		
		MultiMap map = new MultiHashMap();
	    map.put("ONE",l1);  
	//    map.put("ONE",1);
	 //   map.put("ONE", result);
	    
	    Set keySet = map.keySet( );
	    Iterator keyIterator = keySet.iterator();
	 
	    while( keyIterator.hasNext( ) ) {
	        Object key = keyIterator.next( );
	        System.out.print( "Key: " + key + ", " );
	         
	        Collection values = (Collection) map.get( key );
	        Iterator valuesIterator = values.iterator( );
	        while( valuesIterator.hasNext( ) ) {
	            System.out.print( "Value: " + valuesIterator.next( ) + ". " );
	        }
	        System.out.print( "\n" );
	    }
	    }*/
		
		
	
	private void newSetHeaders(Multimap<String,String> multimap,CellFeed cellfeed) throws IOException, ServiceException, GeneralSecurityException
	{
	
//		CellFeed cellfeed = newlogicspreadsheet();
//		CellFeed cellfeed = readWorksheet(wSheetName);
		int row_serviceName =1, col_serviceName =1,row_envi=1,col_envi=3,row_status=3,col_status =1;
		
		CellEntry cellEntry_serviceName = new CellEntry (row_serviceName, col_serviceName, "Service Name");
		cellfeed.insert (cellEntry_serviceName);	
		
	//	setHeadersforProd(cellfeed);
		
		Set keySet = multimap.keySet();
		Iterator keyIterator = keySet.iterator();
		while (keyIterator.hasNext())
		{
			String key = (String) keyIterator.next();
			List values = (List) multimap.get(key);
			
			for (int j=0;j<values.size();j++)
			{
				//System.out.println(key+":value:" +values.get(j));
				
				CellEntry cellEntry = new CellEntry(row_envi,col_envi,key);
				cellfeed.insert(cellEntry);
				
				row_envi = row_envi +1;
				
				CellEntry cellEntry1 = new CellEntry(row_envi,col_envi,(String) values.get(j));
				cellfeed.insert(cellEntry1);
				
				col_envi = col_envi + 3;
				row_envi = row_envi -1;
			}
		}
		
		setStatus (cellfeed,multimap.size());
			
			/*for (int j =0; j<3; j++)
			{
				CellEntry cellEntry = new CellEntry(row_status,col_status,status.get(j));
				cellfeed.insert(cellEntry);
				col_status = col_status +1;
			}*/
	
		
		}
		
	private void setStatus(CellFeed cellfeed,int size) throws ServiceException, IOException
	{
        int row_status = 3, col_status = 2;
		List<String> status = new ArrayList<String>();
		status.add("Total");
		status.add("Pass");
		status.add("Fail");
		
		for (int j=0;j<size;j++)
		{
			
			for (int k =0; k <3; k++)
			{
			CellEntry cellEntry = new CellEntry(row_status,col_status,status.get(k));
			cellfeed.insert(cellEntry);
			
			col_status = col_status +1;
		}
		}
		
	}
	/*private void setHeadersforProd(CellFeed cellfeed) throws ServiceException, IOException
	{
		CellEntry cellEntry_prodEnv = new CellEntry (1, 3, "Production");
		cellfeed.insert (cellEntry_prodEnv);
		
		CellEntry cellEntry_prodGroup = new CellEntry (2, 3, "ProdSanity");
		cellfeed.insert (cellEntry_prodGroup);
	}*/
	
	/*private void setHeaders(List<String>envi,List<String>groupi) throws IOException, ServiceException, GeneralSecurityException
	{
		
		CellFeed cellfeed = newlogicspreadsheet();
		
		CellEntry cellEntry = new CellEntry (1, 1, "Portal Service Name");
		cellfeed.insert (cellEntry);	

		
		// Set headers for Production
		CellEntry cellEntry5 = new CellEntry (1, 3, envi.get(0));
		cellfeed.insert (cellEntry5);
		
		CellEntry cellEntry6 = new CellEntry (2, 3, groupi.get(0));
		cellfeed.insert (cellEntry6);
		
		CellEntry cellEntry1 = new CellEntry (3, 2, "Total");
		cellfeed.insert (cellEntry1);
		
		CellEntry cellEntry2 = new CellEntry (3, 3, "Pass");
		cellfeed.insert (cellEntry2);
		
		CellEntry cellEntry3 = new CellEntry (3, 4, "Fail");
		cellfeed.insert (cellEntry3);
		
		// Set headers for fox7
		int fixed_column = 6;
		
		CellEntry cellEntry10 = new CellEntry (1, 6, envi.get(1));
		cellfeed.insert (cellEntry10);
		
		for (int j=1; j<envi.size();j++)
		{
		for (int i =1;i<groupi.size();i++)
		{
			
		CellEntry cellEntry10 = new CellEntry (1, fixed_column, envi.get(j)); // fox
		cellfeed.insert (cellEntry10);
			
		CellEntry cellEntry11 = new CellEntry (2, fixed_column, groupi.get(i)); //sanity
		cellfeed.insert (cellEntry11);
		
		fixed_column = fixed_column -1;
		CellEntry cellEntry12 = new CellEntry (3, fixed_column, "Total");
		cellfeed.insert (cellEntry12);
		
		fixed_column = fixed_column +1;
		CellEntry cellEntry13 = new CellEntry (3, fixed_column, "Pass");
		cellfeed.insert (cellEntry13);
		
		fixed_column = fixed_column + 1;
		
		CellEntry cellEntry14 = new CellEntry (3, fixed_column, "Fail");
		cellfeed.insert (cellEntry14);
		
		fixed_column = fixed_column + 2;
		}
		}
	}*/
	
	/*private List<String> getClassName ()
	{
		List<String> a = new ArrayList<String>();
		String pathOfFile = "/Users/mohit.jain/Desktop/testng/ListOfClasses.csv";
		BufferedReader fileReader = null;
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(pathOfFile));
		
			while ((line = fileReader.readLine()) != null) {
				a.add(line);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("list= " + a);
		return a;
	}*/
	
	private List<String> readEntireSheet (int min_row,int min_col,int max_col,String sheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException
	{
		System.out.println("sheet name inside mqin loop="+sheetName);
		 ArrayList scopes = new ArrayList();
	        scopes.add(0, DriveScopes.DRIVE_FILE);
	        scopes.add(1, "https://spreadsheets.google.com/feeds");
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setTransport(new NetHttpTransport())
	            .setJsonFactory(new JacksonFactory())
	            .setServiceAccountId(
	                "323208552244-7hrl19deb248h9akq196rctsphrpvar0@developer.gserviceaccount.com")
	            .setServiceAccountPrivateKeyFromP12File(new File("/root/Desktop/JulyEORS/My Project-1026ab85224b.p12"))
	            .setServiceAccountScopes(scopes).build();

	        credential.refreshToken();

	        SpreadsheetService service = new SpreadsheetService("tmp");
	        service.setOAuth2Credentials(credential);
        
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        
        SpreadsheetEntry myspreadsheet = new SpreadsheetEntry();
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
             System.out.println(spreadsheet.getTitle().getPlainText());
             if (spreadsheet.getTitle().getPlainText().compareToIgnoreCase("Reports")==0)
             {
             	myspreadsheet = spreadsheet;
             	System.out.println("spreadsheet name in loop = " +myspreadsheet);
             }
         }
          
         System.out.println("selected spreadsheet name = " +myspreadsheet);
         System.out.println(myspreadsheet.getTitle().getPlainText());

         
     //    URL worksheetFeedUrl = myspreadsheet.getWorksheetFeedUrl();
      //   System.out.println("worksheet feed URL = " +worksheetFeedUrl);
         
    //     WorksheetFeed worksheetFeed= service.getFeed (
     //     	    worksheetFeedUrl, WorksheetFeed.class);
         
         List <WorksheetEntry> worksheetEntrys= myspreadsheet.getWorksheets();
         WorksheetEntry myworksheet = new WorksheetEntry();
         
         for (WorksheetEntry worksheetEntry :worksheetEntrys)
         {
         	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
         	 if (worksheetEntry.getTitle().getPlainText().compareToIgnoreCase(sheetName.trim())==0);
              {
         		 myworksheet = worksheetEntry;
              	System.out.println("worksheet name in loop = " +myworksheet.getTitle().getPlainText());
              }
         }
         System.out.println("selected worksheet =" +myworksheet.getTitle().getPlainText());
         
         URL cellFeedUrl1 = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row="+min_row+"&min-col="+min_col+"&max-col="+max_col).toURL();
         CellFeed cellfeed1 = service.getFeed(cellFeedUrl1, CellFeed.class);
         List<String> mylist2 = new ArrayList<String>();
  
         for (CellEntry cell :  cellfeed1.getEntries()) {

             mylist2.add(cell.getCell().getInputValue().toString());
             
           }
    	 
      //   System.out.println("effective list = "+mylist1);
         return mylist2;
	}
}
