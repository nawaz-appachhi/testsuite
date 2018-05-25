package com.myntra.apiTests.erpservices.myntra;

import com.myntra.lordoftherings.boromir.DBUtilities;

/**
 * Created by 16553 on 07/03/17.
 */
public class MyntraWidgetDataHelper {

    //to update the myntra table for key value combinations
    public void updatewidgetpairs(String[] key_value){
        for(String key_value_pair : key_value){
            String data[]=key_value_pair.split(":");
            DBUtilities.exUpdateQuery("update mk_widget_key_value_pairs set value="+data[1]+" where 'key'='"+data[0]+"';","myntra");
        }

    }
}
