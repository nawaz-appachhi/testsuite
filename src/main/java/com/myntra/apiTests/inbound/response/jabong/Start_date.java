package com.myntra.apiTests.inbound.response.jabong;

public class Start_date {
	
	private String $date;

    public String get$date ()
    {
        return $date;
    }

    public void set$date (String $date)
    {
        this.$date = $date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [$date = "+$date+"]";
    }

}
