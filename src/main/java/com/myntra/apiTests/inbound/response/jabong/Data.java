package com.myntra.apiTests.inbound.response.jabong;

public class Data { 

private End_date end_date;

private String source;

private String gender;

private String brand;

private String article_type;

private Style_break_date style_break_date;

//private Activated_at activated_at;

private Start_date start_date;

private String style_id;

public End_date getEnd_date ()
{
    return end_date;
}

public void setEnd_date (End_date end_date)
{
    this.end_date = end_date;
}

public String getSource ()
{
    return source;
}

public void setSource (String source)
{
    this.source = source;
}

public String getGender ()
{
    return gender;
}

public void setGender (String gender)
{
    this.gender = gender;
}

public String getBrand ()
{
    return brand;
}

public void setBrand (String brand)
{
    this.brand = brand;
}

public String getArticle_type ()
{
    return article_type;
}

public void setArticle_type (String article_type)
{
    this.article_type = article_type;
}

public Style_break_date getStyle_break_date ()
{
    return style_break_date;
}

public void setStyle_break_date (Style_break_date style_break_date)
{
    this.style_break_date = style_break_date;
}

//public Activated_at getActivated_at ()
//{
//    return activated_at;
//}
//
//public void setActivated_at (Activated_at activated_at)
//{
//    this.activated_at = activated_at;
//}

public Start_date getStart_date ()
{
    return start_date;
}

public void setStart_date (Start_date start_date)
{
    this.start_date = start_date;
}

public String getStyle_id ()
{
    return style_id;
}

public void setStyle_id (String style_id)
{
    this.style_id = style_id;
}

@Override
public String toString()
{
    return "ClassPojo [end_date = "+end_date+", source = "+source+", gender = "+gender+", brand = "+brand+", article_type = "+article_type+", style_break_date = "+style_break_date+", start_date = "+start_date+", style_id = "+style_id+"]";
}
}