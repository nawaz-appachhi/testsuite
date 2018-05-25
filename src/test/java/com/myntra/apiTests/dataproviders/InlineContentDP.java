package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

public class InlineContentDP {
	Commons comUtil = new Commons();
	
	@DataProvider
	public Object[][]createIc(ITestContext testContext){
		Object[][] dataSet={new String[]{getEmail(),"red","female","Revv","Bangle","Apparel"},
				new String[]{"sabyasachi.mishra@myntra.com","green","male","Adidas","Shoes","Sports"},
				};
		
//		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 6);
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
		
	}
	
	@DataProvider
	public Object[][]createIcNegative(ITestContext testContext){
		Object[][] dataSet={new String[]{getEmail(),"red","female","Revv","Bangle",""},
				new String[]{"","","male","Adidas","Shoes","Sports"},
				new String[]{"@#$","abc","male","Adidas","Shoes","Sports"},
				new String[]{"","","","","",""},
				};
		
//		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 6);
		 return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 6);
	}
	
	@DataProvider
	public Object[][]getIcAdmin(ITestContext testContext){
		Object[][] dataSet={new String[]{"41185ad0-59f7-4e75-a3f4-6749e08e7969"}
				//new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public Object[][]getIcAdminNegative(ITestContext testContext){
		Object[][] dataSet={new String[]{"abcxyz"},
							new String[]{"1234"},
							new String[]{"@#$"},
							new String[]{""}
				};
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][]deleteIcAdmin(ITestContext testContext){
		Object[][] dataSet={new String[]{"41185ad0-59f7-4e75-a3f4-6749e08e7969"}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]deleteIcAdminNegative(ITestContext testContext){
		Object[][] dataSet={new String[]{"abc"},
							new String[]{"123"},
							new String[]{"@#$"},
							new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]clientSearchIc(ITestContext testContext){
		Object[][] dataSet={new String[]{"204b19e4-7b94-48c9-b851-4e2348ec1a16"}
				//new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]clientSearchIcNegative(ITestContext testContext){
		Object[][] dataSet={new String[]{"abc"},
							new String[]{"123"},
							new String[]{"@#$"},
							new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]updateIcAdmin(ITestContext testContext){
		Object[][] dataSet={new String[]{"41185ad0-59f7-4e75-a3f4-6749e08e7969","type2"}
				//new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][]searchIcAdmin(ITestContext testContext){
		Object[][] dataSet={new String[]{"805a90ef-92ca-4515-b9e5-fe7e52f21bb4"}
				//new String[]{""}
				};
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][]searchIcAdminNegative(ITestContext testContext){
		Object[][] dataSet={new String[]{"abc"},
							new String[]{"123"},
							new String[]{"@#$"},
							new String[]{""}
				};
		
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	private String getEmail(){
		String[] emails = {"@gmail.com", "@myntra.com", "@rediffmail.com", "@times.com", "@xyz.com"};
		return getName()+emails[comUtil.randomNumberUptoLimit(emails.length)];
	}
	private String getName(){
		String[] all =  {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String toReturn = null ;
		try {
			toReturn = comUtil.getCommaSeperatedValuesFromArray(all, 10, true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn.replaceAll(",", "").toLowerCase();
	}


}
