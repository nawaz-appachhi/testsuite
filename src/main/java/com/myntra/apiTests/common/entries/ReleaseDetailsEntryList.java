package com.myntra.apiTests.common.entries;

import java.util.List;

public class ReleaseDetailsEntryList {

	List<ReleaseDetailsEntry> releaseDetailsEntries;

	
	public ReleaseDetailsEntryList() {

	}

	public ReleaseDetailsEntryList(List<ReleaseDetailsEntry> releaseDetailsEntries) {
		this.releaseDetailsEntries = releaseDetailsEntries;
	}

	public List<ReleaseDetailsEntry> getReleaseDetailsEntries() {
		return releaseDetailsEntries;
	}

	public void setReleaseDetailsEntries(List<ReleaseDetailsEntry> releaseDetailsEntries) {
		this.releaseDetailsEntries = releaseDetailsEntries;
	}

	@Override
	public String toString() {
		return "ReleaseDetailsEntryList [releaseDetailsEntries=" + releaseDetailsEntries + "]";
	}
	
	
}
