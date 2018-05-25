package com.myntra.qnotifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.Test;

public class ConvertxmltoString {
	public static String filename=System.getProperty("user.dir")+"/src/test/java/com/myntra/qnotifications/NotificationEvents";
	@Test
	public void convert() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		String line;
		StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		
		System.out.println(sb);
	}

}
