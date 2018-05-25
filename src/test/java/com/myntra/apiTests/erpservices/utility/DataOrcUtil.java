package com.myntra.apiTests.erpservices.utility;

/*
 * File:	DataOrc.java
 * Class Description:	This is class to generate the test data variants and would be used in TestNG @Dataproviders used for 
 * 						Testcases.
 * Project:		"Lordoftherings" - The Test harness for API, UI, DB, Non Functional, Functional Testing.
 * Created:     2013/12/17
 * Author:      <A HREF="mailto:ashish.bajpai@myntra.com">[Ashish Kumar Bajpai]</A>

 * This code is copyright (c) 2013, Myntra Designs Pvt Ltd, Bangalore
 * 
 * */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.ParameterData;

public class DataOrcUtil
{

	public Object[][] dataset;
	HashMap<Integer, Object> datamap;
	private Object[][] objectifieddata;
	
	public DataOrcUtil()
	{
		// TODO Auto-generated constructor stub
	}

	public DataOrcUtil(Object[][] datacombinations)
	{
		int combinations = 0;
		for (Object[] datarow : datacombinations)
		{
			for (Object column : datarow)
			{

			}
		}
	}

	public DataOrcUtil(Object[] parameters)
	{
		int combinations = 0;
		for (Object datarow : parameters)
		{
			
		}
	}

	public DataOrcUtil(List<?>... lists)
	{
        dataset = generatevariants2(lists);
	}

    private Object[][] generatevariants(List<?>... lists)
    {

        int[] size = new int[lists.length];
        int counter = 0;

        for(List lst:lists)
        {
            size[counter] = lst.size();
            counter++;

        }
        int arraylength = 1;
        for(int i=0; i < size.length;i++)
        {
            arraylength = arraylength * size[i];
        }

        Object[][] returnval = new Object[arraylength][lists.length];
        int column = 0, row;
        for(List lst:lists)
        {
            row = 0;
            int vertical = arraylength/lst.size();
            for(int i=0;i<vertical;i++)
            {
                for(int j=0;j<lst.size();j++)
                {
                    returnval[row][column] = lst.get(j);
                    row++;
                }
            }

            column++;
        }
        objectifieddata = returnval;
        printarray(objectifieddata);
        return returnval;
    }



    public Object[][] generatevariants2(List<?>... lists)
    {

        int[] size = new int[lists.length];
        int counter = 0;

        for(List lst:lists)
        {
            size[counter] = lst.size();
            counter++;

        }
        int arraylength = 1;
        for(int i=0; i < size.length;i++)
        {
            arraylength = arraylength * size[i];
        }

        int[] resetthreshhold = new int[lists.length];
        int newarrlength = 1;

        int temp = 1;
        for(int i=0; i < lists.length;i++)
        {
            System.out.println("size[i] = " + size[i]);
            newarrlength = newarrlength*size[i];
            resetthreshhold[i] = arraylength/newarrlength;
        }
        System.out.println("Size values = " + Arrays.toString(size));
        System.out.println("Threshhold values = " + Arrays.toString(resetthreshhold));


        Object[][] returnval = new Object[arraylength][lists.length];
        int column = 0, row=0, itr=0, itemindex = 0,counter1 = 0, index = 0;
        Object value = "";
        int totallists = lists.length;
        System.out.println("totallists = " + totallists);
        for(List lst:lists)
        {
            counter1 = 0;
            index=0;
            itr = resetthreshhold[itemindex];
            System.out.println("itr = " + itr);
            for(int i=0;i<arraylength;i++)
            {
                if (counter1 == itr)
                {
                    counter1 = 0;
                    index++;

                }
                if (index >= lst.size())
                {
                    index = 0;
                }
                System.out.println("lstsize ="+lst.size()+", index = " + index+": counter = "+counter1);
                value = lst.get(index);
                System.out.println("row = " + i+",column = "+column+" :: value ="+value);

               returnval[i][column] = value;
                counter1++;
                //index++;
            }
            column++;
            itemindex++;
        }
        objectifieddata = returnval;
        printarray(objectifieddata);
        return returnval;
    }

    public Object[][] Explode()
    {
        return  objectifieddata;
    }

    private void printlist(List<Object> list)
    {
        for (Object item:list)
        {
            System.out.println(item + " : ");

        }
    }
    private void printarray(Object[][] objectarray)
    {
            for (Object[] arr : objectarray) {
            System.out.println(Arrays.toString(arr));
        }
    }

	public DataOrcUtil(List<Object> list)
	{
		for (Object value : list)
		{
			System.out.println(value.getClass().getName().toString());
		}
	}

	public DataOrcUtil(Enum<?> enumname)
	{
		GetEnumValues(enumname);
	}

	public DataOrcUtil(ParameterData... paramdata)
	{

	}

	public void runtestmethod(String methodname, String[] paramlist)
	{

		Method methd;
		Configuration conf = new Configuration();
		String name = methodname;
		try
		{
			methd = conf.getClass().getMethod(name, String.class);
			for (String str : paramlist)
			{
				methd.invoke(conf, str);
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	public void GetEnumValues(Enum<?> enumname)
	{
		Object[] possibleValues = enumname.getDeclaringClass().getEnumConstants();
		for (Object enumvalues : possibleValues)
		{
			System.out.println(enumvalues.toString());
		}

	}

	public void print(Object[]... arguments)
	{
		System.out.println(arguments);
	}

	/*public Object[] objectify(String[] strarray)
	{
		objectifieddata = null;

		int counter = 0;
		for (String str : strarray)
		{
			objectifieddata[counter] = str;
			counter++;
		}
		return objectifieddata;
	}*/

	public <T> T[] toArray(List<T> list, Class<T> k)
	{
		return list.toArray((T[]) java.lang.reflect.Array.newInstance(k, list.size()));
	}

	/*public <T> T[] toArray(List<T> list, T[] a) 
	{
		  if (a.length < list.size()) 
		  {
			  a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), list.size()));
		  }
		  return list.toArray(a);
		}*/
}
