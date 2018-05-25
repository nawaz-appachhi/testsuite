package com.myntra.apiTests.inbound.response;

public class Docs {
	private String doc_count;

    public String getDoc_count ()
    {
        return doc_count;
    }

    public void setDoc_count (String doc_count)
    {
        this.doc_count = doc_count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [doc_count = "+doc_count+"]";
    }
}
