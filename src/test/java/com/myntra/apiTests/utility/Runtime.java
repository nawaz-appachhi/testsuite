package com.myntra.apiTests.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Vishwa on 28/05/17.
 */
public class Runtime {
    public static void Sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String runShellCommandAndGetResponse(String command) {

        String s = "";
        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = java.lang.Runtime.getRuntime().exec(command);
            Sleep(60000);
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n"
                    + command + "\n");
            String str, str1;
            while ((str = stdInput.readLine()) != null) {
                s = s + str;
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out
                    .println("Here is the standard error of the command (if any):\n");
            while ((str1 = stdError.readLine()) != null) {
                // s=s+str1;
                // System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
        return s;

    }
}
