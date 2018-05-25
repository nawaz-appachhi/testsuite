package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.*;

/**
 * Created by ashish.bajpai on 21/05/14.
 */
public class devRequestsDP {


    @DataProvider
    public Object[][] DevaskDP(ITestContext context)
    {

        try {


            BufferedReader br = new BufferedReader(new FileReader("/Users/ashish.bajpai/Downloads/keywordgreaterthan100.txt"));
            String line;
            LineNumberReader lnr = new LineNumberReader(new FileReader(new File("/Users/ashish.bajpai/Downloads/keywordgreaterthan100.txt")));
            lnr.skip(Long.MAX_VALUE);

            int linecount = lnr.getLineNumber();
            //System.out.println("Total lines"+linecount);
            lnr.close();
            int i = 0;
            Object[][] dataSet = new Object[linecount][];
            while ((line = br.readLine()) != null) {
                //System.out.println("line = " + line);
                //Object[] item = {};
                dataSet[i]= new Object[]{line,"500000","false","false"};
                i++;
            }
            br.close();
            System.out.println("dataSet.length = " + dataSet.length);
            /*for (Object[] arr : dataSet) {
                System.out.println(Arrays.toString(arr));
            }*/
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
