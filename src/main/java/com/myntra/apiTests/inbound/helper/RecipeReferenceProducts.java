package com.myntra.apiTests.inbound.helper;

public class RecipeReferenceProducts {

	private String styleId;

    private StyleParams styleParams;

    private String source;

    private String styleType;

    private String imageURL;
    
    private String id;
    private String createdBy;
    private String createdOn;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	private String lastModifiedOn;

    public String getStyleId ()
    {
        return styleId;
    }

    public void setStyleId (String styleId)
    {
        this.styleId = styleId;
    }

    public StyleParams getStyleParams ()
    {
        return styleParams;
    }

    public void setStyleParams (StyleParams styleParams)
    {
        this.styleParams = styleParams;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getStyleType ()
    {
        return styleType;
    }

    public void setStyleType (String styleType)
    {
        this.styleType = styleType;
    }

    public String getImageURL ()
    {
        return imageURL;
    }

    public void setImageURL (String imageURL)
    {
        this.imageURL = imageURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [styleId = "+styleId+", styleParams = "+styleParams+", source = "+source+", styleType = "+styleType+", imageURL = "+imageURL+"]";
    }
}
