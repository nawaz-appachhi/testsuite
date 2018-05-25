package com.myntra.apiTests.portalservices.atsService;

import java.util.*;

public class TestPhenom {
	
	public static void main(String args[])
    {
		  // Write your code here
        Scanner s = new Scanner(System.in);
        String name = s.nextLine(); 
        String input = name.toLowerCase();
        int[] alphabetArray = new int[26];
        
       for( int i = 0; i < input.length(); i++ ) {
         char ch=  input.charAt(i);
         int value = (int) ch;
         if (value >= 97 && value <= 122){
        	 int k = (int) ch-'a';
        	 System.out.println("k "+ k);
         alphabetArray[k]++;
        }
       }
       for(int j=0; j<26; j++) {

           System.out.print(alphabetArray[j]+" ");   //Show the result.
         }
       }
    }

