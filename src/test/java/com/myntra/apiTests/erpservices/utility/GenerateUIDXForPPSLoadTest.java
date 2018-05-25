package com.myntra.apiTests.erpservices.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 8403 on 03/03/16.
 */
public class GenerateUIDXForPPSLoadTest {


    public static void main(String[] args) throws IOException {
        int xidcount = 200000;
        GenerateUIDXForPPSLoadTest generateUIDXForPPSLoadTest = new GenerateUIDXForPPSLoadTest();
        generateUIDXForPPSLoadTest.generateUIDX(16, 20, xidcount);
    }
    
    
	/**
	 * Generate UIDX pattern For the Load Test
	 * @param startUidxPatternCount
     * @param endUidxPatternCount
     * @param xidcount
	 * @throws IOException
	 */
	private void generateUIDX(int startUidxPatternCount, int endUidxPatternCount, int xidcount) throws IOException {
		File file = new File("uidx16_20.csv");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());

		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		System.out.println((endUidxPatternCount-startUidxPatternCount)+2);
		for (int j = 1; j <= (xidcount / (endUidxPatternCount-startUidxPatternCount)+2); j++) {
			for (int i = startUidxPatternCount; i <= endUidxPatternCount; i++) {
				String uidxInital = "scmloadtest" + i + "-96d9c0c9.f839.4f03.ab74.test." + j;
				bw.write(uidxInital + "\n");
			}
		}
		bw.close();
	}
}
