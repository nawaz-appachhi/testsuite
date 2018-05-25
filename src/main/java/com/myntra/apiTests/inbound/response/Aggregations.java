package com.myntra.apiTests.inbound.response;

public class Aggregations {
	private Docs docs;
	private Rdf_forecast rdf_forecast;
    private Ros_forecast ros_forecast;

    public Rdf_forecast getRdf_forecast ()
    {
        return rdf_forecast;
    }

    public void setRdf_forecast (Rdf_forecast rdf_forecast)
    {
        this.rdf_forecast = rdf_forecast;
    }

    public Ros_forecast getRos_forecast ()
    {
        return ros_forecast;
    }

    public void setRos_forecast (Ros_forecast ros_forecast)
    {
        this.ros_forecast = ros_forecast;
    }

	public Docs getDocs() {
		return docs;
	}

	public void setDocs(Docs docs) {
		this.docs = docs;
	}

	@Override
	public String toString() {
		return "ClassPojo [docs = " + docs + ",rdf_forecast = "+rdf_forecast+", ros_forecast = "+ros_forecast+"]";
	}

}
