package com.myntra.apiTests.testdevelopmentrnd;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.myntra.apiTests.dataproviders.IdeaMigrationDBDataProvider;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class ideaMigrationDBUtility extends IdeaMigrationDBDataProvider {
	
	private Pattern pattern;
	private Matcher matcher ;
	
	private CellFeed getSpreadsheet(String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		
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
	      if (spreadsheet.getTitle().getPlainText().equalsIgnoreCase("IdeaMigration"))
	      {
	      	myspreadsheet = spreadsheet;
	      	System.out.println("spreadsheet name in loop = " +myspreadsheet);
	      }
	  }
	   
	  System.out.println("spreadsheet name = " +myspreadsheet);
	  System.out.println(myspreadsheet.getTitle().getPlainText());
	  List <WorksheetEntry> worksheetEntrys= myspreadsheet.getWorksheets();
	  WorksheetEntry myworksheet = new WorksheetEntry();
	  
	  for (WorksheetEntry worksheetEntry :worksheetEntrys)
	  {
	  	 System.out.println("Worksheet enteries =" +worksheetEntry.getTitle().getPlainText());
	  	 if (worksheetEntry.getTitle().getPlainText().equalsIgnoreCase(worksheetName))
	       {
	  		 myworksheet = worksheetEntry;
	       	System.out.println("worksheet name in loop = " +myworksheet);
	       }
	  }
	  URL cellFeedUrl = new URI(myworksheet.getCellFeedUrl().toString()+"?min-row=3&min-col=2&max-col=4").toURL();
	  CellFeed cellfeed = service.getFeed(cellFeedUrl, CellFeed.class);
	  
	  return cellfeed;
	}
	
	private void fetchSpreadsheetData(CellFeed cellfeed, int row, int col,List<String> value ) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		int index =0;
		for(int j=2;j<row;j++){
			for (int i=0;i<col;i++){
				  
				  CellEntry cellEntry= new CellEntry (j, i+1, value.get(index++));

			      cellfeed.insert (cellEntry);
			}
			System.out.println("inserted into spreadsheet row no:"+j);
		}	
	}
	
	private void fetchSpreadsheetDataForEmailIDColumns(CellFeed cellfeed, int row, int col,List<String> value ) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		int index =0;
			for (int i=0;i<col;i++){
				  
				  CellEntry cellEntry= new CellEntry (row, i+1, value.get(index++));

			      cellfeed.insert (cellEntry);
			}
		}

	
	private void getForeignKeyConstraints(String conString, String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		Statement stmt = null;
		Connection con = null;
		String table_name = "";
		String table_schema ="";
		String constraint_type ="";
		String constraint_name ="";
		String column_name = "";
		String ref_table_schema = "";
		String ref_table_name = "";
		String ref_column_name = "";
		List<String> array = new ArrayList<String>();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString,"myntraAppDBUser","9eguCrustuBR");
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		String query = "select t.constraint_name, t.table_schema, t. table_name, t.constraint_type ,k.column_name, k.referenced_table_schema, k.referenced_table_name, k.referenced_column_name from "
				+ "information_schema.table_constraints t, information_schema.key_column_usage k where t.constraint_type = 'FOREIGN KEY' and k.constraint_name = t.constraint_name;";
		
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			System.out.format("%-20s%-50s%-20s%-50s%-50s%-20s%-50s%-50s","TABLE NAME", "TABLE_SCHEMA", "CONSTRAINT TYPE", "CONSTRAINT_NAME", "COLUMN NAME", "REFERENCED TABLE SCHEMA", "REFERENCED TABLE NAME", "REFERENCED TABLE COLUMN");
			int rowCount = 2;
			int colCount = rs.getMetaData().getColumnCount();
			CellFeed cellfeed = getSpreadsheet(worksheetName);
			while(rs.next())
			{
				table_schema= rs.getString("TABLE_SCHEMA");
				table_name = rs.getString("TABLE_NAME");
				constraint_type =rs.getString("CONSTRAINT_TYPE");
				constraint_name = rs.getString("CONSTRAINT_NAME");
				column_name = rs.getString("COLUMN_NAME");
				ref_table_schema = rs.getString("REFERENCED_TABLE_SCHEMA");
				ref_table_name = rs.getString("REFERENCED_TABLE_NAME");
				ref_column_name = rs.getString("REFERENCED_COLUMN_NAME");
				System.out.println();
				System.out.format("%-20s%-50s%-20s%-50s%-50s%-20s%-50s%-50s",table_schema,table_name,constraint_type, constraint_name,column_name,ref_table_schema,ref_table_name,ref_column_name );
				array.add(table_schema);
				array.add(table_name);
				array.add(constraint_type);
				array.add(constraint_name);
				array.add(column_name);
				array.add(ref_table_schema);
				array.add(ref_table_name);
				array.add(ref_column_name);
				rowCount++;
			}
			fetchSpreadsheetData(cellfeed,rowCount,colCount,array);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void getIndexData(String conString, String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		Statement stmt = null;
		Connection con = null;
		String table_name = "";
		String table_schema ="";
		String index_name ="";
		String index_schema ="";
		String column_name ="";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//con = DriverManager.getConnection(conString);
			con = DriverManager.getConnection(conString,"myntraAppDBUser","9eguCrustuBR");
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String query = "select * from statistics where table_schema in ('myntra')";
		System.out.println(query);
		List<String> array = new ArrayList<String>();
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			System.out.format("%s-%50s-%50s-%50s-%50s","TABLE SCHEMA","TABLE NAME","INDEX SCHEMA","INDEX NAME","COLUMN NAME");
			CellFeed cellfeed = getSpreadsheet(worksheetName);
			int rowCount =2, colCount = 5;
			while(rs.next())
			{
				table_schema= rs.getString("TABLE_SCHEMA");
				table_name = rs.getString("TABLE_NAME");
				index_schema =rs.getString("INDEX_SCHEMA");
				index_name = rs.getString("INDEX_NAME");
				column_name = rs.getString("COLUMN_NAME");
				System.out.println();
				System.out.format("%s-%50s-%50s-%50s-%50s",table_schema,table_name,index_schema,index_name,column_name);
				array.add(table_schema);
				array.add(table_name);
				array.add(index_schema);
				array.add(index_name);
				array.add(column_name);
				rowCount++;
				System.out.println("inside while: total row count"+rowCount);
			}
			
			System.out.println("total rows to be printed:"+rowCount);
			fetchSpreadsheetData(cellfeed,rowCount,colCount,array);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getTableSchemaNames(String conString){
		Statement stmt = null;
		Connection con = null;
		StringBuilder table_schema = new StringBuilder();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(conString,"myntraAppDBUser","9eguCrustuBR");
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		String query = "select distinct(table_schema) from information_schema.tables where table_type = 'BASE TABLE'";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				table_schema.append(rs.getString("TABLE_SCHEMA"));
				table_schema.append(",");
				System.out.println();
					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return table_schema.toString();
	}
	
	
	private int getEmailIdColumns(String conString, String worksheetName, String schema, int row) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		Statement stmt1 = null;
		Statement stmt2 =null;
		Statement stmt3 = null;
		Connection con = null;
		String table_name = "";
		String column_name ="";
		//String regex = "(?s).*[\\@].*[\\.].*"; // for all columns having emailId somewhere bw the data
		String regex =".*[\\@].*[\\.].*";
		int count;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		    con = DriverManager.getConnection(conString,"myntraAppDBUser","9eguCrustuBR");
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();
			stmt3 = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		String query = "select table_name from information_schema.tables where table_type= 'BASE TABLE' and table_schema = '"+schema+"'";
		try {
			ResultSet rs = stmt1.executeQuery(query);
			CellFeed cellfeed = getSpreadsheet(worksheetName);
			while(rs.next())
			{	List<String> array = new ArrayList<String>();
				table_name = rs.getString("TABLE_NAME");
				String query1 = "select * from "+schema+"."+table_name+" limit 1";
				System.out.println(" outer loop table schema is :" +schema);
				System.out.println(" outer loop table name is :" +rs.getString("TABLE_NAME"));
				ResultSet rs1 = stmt2.executeQuery(query1);
				count = 0;
				while(rs1.next()){
					for(int i=1; i <= rs1.getMetaData().getColumnCount(); i++){
						column_name =rs1.getMetaData().getColumnName(i);
						String data = rs1.getString(column_name);
						pattern = pattern.compile(regex);
							if(!(data == null)){
								matcher = pattern.matcher(data);
								boolean matches = matcher.matches();
								if (matches){
									if(count == 0)
									{
										array.add(schema);
										array.add(table_name);
										
									}
									System.out.println("inner column ****"+column_name+"data is"+data);
							
									array.add(column_name);
									count++;
								}
							}
						}
						System.out.println("----------------------////--------");
					}
				    if(count >0){
				    	int colCount = 2 + count;
						fetchSpreadsheetDataForEmailIDColumns(cellfeed,row,colCount,array);
						row++;
					 }
				  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return row;
	}
	@Test(groups ="foreignKey", dataProvider = "GetFKConstraintsDataProvider")
	public void foreignKeyConstraints(String connection, String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		getForeignKeyConstraints(connection,worksheetName);
		
	}
	
	@Test(groups="getIndexes",dataProvider = "GetIndexConstraintsDataProvider")
	public void getIndexes(String connection, String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		getIndexData(connection,"myntra_portal_indexes");
		
	}
	
	
	@Test(groups="getEmailCols",dataProvider = "GetEmailIDColumnsDataProvider" )
	public void getEmails(String connection,String worksheetName) throws IOException, ServiceException, URISyntaxException, GeneralSecurityException{
		
		String table_schema = getTableSchemaNames(connection);
		String [] schemas = table_schema.split(",");
		int rowCount = 2, newRowCount;
		int count =1;
		for(int i=0; i < schemas.length; i++){
			System.out.println("before flow "+count++);
			newRowCount = getEmailIdColumns(connection,worksheetName,schemas[i],rowCount);
			rowCount = newRowCount;	
		}
	}
	

}