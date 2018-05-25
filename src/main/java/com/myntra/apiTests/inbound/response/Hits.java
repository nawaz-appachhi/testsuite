package com.myntra.apiTests.inbound.response;

public class Hits {

	private String total;

private String[] hits;

private String max_score;

public String getTotal ()
{
    return total;
}

public void setTotal (String total)
{
    this.total = total;
}

public String[] getHits ()
{
    return hits;
}

public void setHits (String[] hits)
{
    this.hits = hits;
}

public String getMax_score ()
{
    return max_score;
}

public void setMax_score (String max_score)
{
    this.max_score = max_score;
}

@Override
public String toString()
{
    return "ClassPojo [total = "+total+", hits = "+hits+", max_score = "+max_score+"]";
}

}
