package com.myntra.apiTests.erpservices.ims.Tests;

import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.redis.RedisUtil;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Sneha.Agarwal on 16/02/18.
 */
public class redisTest extends BaseTest{
    static Logger log = Logger.getLogger(redisTest.class);
    @Test
    public void setData(){
        String dbQuery="select sequence_key, max(sequence_number)\n" +
                "from sequence_generation_backup\n" +
                "group by sequence_key";
        RedisUtil redisUtil= new RedisUtil();
        List<Map<String, Object>> listData=DBUtilities.exSelectQuery(dbQuery,"myntra_munshi");
        for(Map<String, Object> hm:listData) {
            String key=hm.get("sequence_key").toString();
            String value=hm.get("max(sequence_number)").toString();

            log.info("DB data: key: " + key + " value: " + value);
            log.info("Initial Redis value " + redisUtil.getValue("erpredis", key));

            redisUtil.setValue("erpredis", key, String.valueOf(Integer.parseInt(value)+1));

            log.info("Final Redis value "+redisUtil.getValue( "erpredis", key));
        }
    }

}
