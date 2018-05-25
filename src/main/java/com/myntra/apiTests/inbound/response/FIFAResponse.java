package com.myntra.apiTests.inbound.response;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;



@JsonIgnoreProperties(ignoreUnknown = true)
public class FIFAResponse {
	private Status status;
	//private List<Data> data;
	//private Data[] data;
	private Aggregations aggregations;
	private Hits hits;
	private String timed_out;
	private String[] style_id;
    private String[] fields;
    private String collection_id;
    public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	public String getNum_styles() {
		return num_styles;
	}

	public void setNum_styles(String num_styles) {
		this.num_styles = num_styles;
	}
	private String num_styles;
    public String[] getStyle_id ()
    {
        return style_id;
    }

    public void setStyle_id (String[] style_id)
    {
        this.style_id = style_id;
    }

    public String[] getFields ()
    {
        return fields;
    }

    public void setFields (String[] fields)
    {
        this.fields = fields;
    }


	
	public Aggregations getAggregations ()
    {
        return aggregations;
    }

    public void setAggregations (Aggregations aggregations)
    {
        this.aggregations = aggregations;
    }
    
    public Hits getHits ()
    {
        return hits;
    }

    public void setHits (Hits hits)
    {
        this.hits = hits;
    }
	
    public String getTimed_out ()
    {
        return timed_out;
    }

    public void setTimed_out (String timed_out)
    {
        this.timed_out = timed_out;
    }


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
//	public List<Data> getData() {
//		return data;
//	}
//
//
//	public void setData(List<Data> data) {
//		this.data = data;
//	}
	
	@Override
    public String toString()
    {
        //return "ClassPojo [status = "+status+", data = "+data+",]";
        return "ClassPojo [status = "+status+",hits = "+hits+",  timed_out = "+timed_out+", aggregations = "+aggregations+"]";

    }
	
	public String getCollectionName(String responseBody) throws Exception{
		String datasetName=null;
		JSONObject jsonObject=new JSONObject(responseBody);
		JSONObject myResponse = jsonObject.getJSONObject("data");
		Iterator<String> itr=myResponse.keys();
		while(itr.hasNext()){
			//System.out.println(itr.next());
			datasetName=itr.next();
		}
		

		return datasetName;
		
	}
	public String getProcessedCollectionName(String responseBody) throws Exception{
		String datasetName=null;
		JSONObject jsonObject=new JSONObject(responseBody);
		datasetName= jsonObject.get("data").toString();
		return datasetName;
		
	}
	
//	public void getCollectionName123(String responseBody) throws Exception{
//		//String datasetName=null;
//		JSONObject jsonObject=new JSONObject(responseBody);
//		JSONObject myResponse = jsonObject.getJSONObject("brand");
//		Iterator<String> itr=myResponse.keys();
//		while(itr.hasNext()){
//			System.out.println(itr.next());
//			//datasetName=itr.next();
//		}
//		
//
//		//return datasetName;
//		
//	}
	
	public void compareSpotJsonResponse(String responseBody, String start_date_from, String asp, String gender)
			throws Exception {
		Date input_start_date;
		Date response_start_date;
		DateFormat formatter;

		JSONObject jsonObject = new JSONObject(responseBody);
		JSONArray myResponse = jsonObject.getJSONArray("data");
		for (int i = 0; i < myResponse.length(); i++) {

			JSONObject jsondataobjects = myResponse.getJSONObject(i);
			Assert.assertEquals(Double.parseDouble(jsondataobjects.get("asp").toString()), Double.parseDouble(asp));
			Assert.assertEquals(jsondataobjects.get("gender").toString().toLowerCase(), gender.toLowerCase());

			formatter = new SimpleDateFormat("yyyyMMdd");
			input_start_date = formatter.parse(start_date_from);
			response_start_date = formatter.parse(jsondataobjects.get("style_catalogued_date").toString());
			Assert.assertTrue(response_start_date.compareTo(input_start_date) >= 0,
					"'style_date_from' Date expected to be less than" + input_start_date + " but actual date is:"
							+ response_start_date + "for the style_id:" + jsondataobjects.get("style_id"));

		}

//	public Data[] getData() {
//		return data;
//	}
//
//	public void setData(Data[] data) {
//		this.data = data;
//	}
	
	

	}
    
}
