package com.myntra.apiTests.common.entries;

import java.util.List;

public class ReleaseEntryList {
	
	List<ReleaseEntry> releaseEntries;

	public ReleaseEntryList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReleaseEntryList(List<ReleaseEntry> releaseEntries) {
		super();
		this.releaseEntries = releaseEntries;
	}

	public List<ReleaseEntry> getReleaseEntries() {
		return releaseEntries;
	}

	public void setReleaseEntries(List<ReleaseEntry> releaseEntries) {
		this.releaseEntries = releaseEntries;
	}
	
	
}
