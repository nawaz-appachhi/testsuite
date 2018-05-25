package com.myntra.apiTests.erpservices.lms.lmsClient;

/**
 * Created by Shubham Gupta on 5/15/17.
 */
public class TmsMasterBag {

    private TmsMasterbagEntry tmsMasterbagEntry;

    public TmsMasterbagEntry getTmsMasterbagEntry() {
        return tmsMasterbagEntry;
    }

    public void setTmsMasterbagEntry(TmsMasterbagEntry tmsMasterbagEntry) {
        this.tmsMasterbagEntry = tmsMasterbagEntry;
    }

    @Override
    public String toString() {
        return "TmsMasterBag{" +
                "tmsMasterbagEntry=" + tmsMasterbagEntry +
                '}';
    }
}
