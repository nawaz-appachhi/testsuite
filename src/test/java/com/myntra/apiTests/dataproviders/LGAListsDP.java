package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class LGAListsDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public LGAListsDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

		//Get List Count
		@DataProvider
		public Object[][] getUserLists(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Create List
		@DataProvider
		public Object[][] CreateUserList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}

		//Edit List
		@DataProvider
		public Object[][] EditUserList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Delete List
		@DataProvider
		public Object[][] DeleteUserList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}

		//Tag&Untag List
		@DataProvider
		public Object[][] TagUnTagList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Add or Remove Style from User List
		@DataProvider
		public Object[][] AddRemoveStyleToList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","297222","915706","#cold","#winter","297222"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","297222","679571","#hot","#sunny","297222"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter","785115"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny","1033397"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter","785115"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny","1033397"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Add or Remove Style from User List
		@DataProvider
		public Object[][] AddRemoveStyleToDefaultList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "1052662","1033397"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","1033397","1052662",};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","785115","1052662"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","1033397","1052662"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","785115","1052662"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","1033397","1052662"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Modify List Access
		@DataProvider
		public Object[][] ModifyListAccess(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list004@myntra.com", "randompass", "private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter","public"};
			String[] UserData2 = { "Fox7", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny","public"};
			String[] UserData3 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter","public"};
			String[] UserData4 = { "Production", "aravind.list004@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny","public"};
			String[] UserData5 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Winter Collection","1052662","915706","#cold","#winter","public"};
			String[] UserData6 = { "Functional", "aravind.list002@myntra.com", "randompass","private","Be warm!","user_list","Summer Collection","687778","679571","#hot","#sunny","public"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
		
		//Remove Style from WishList
		@DataProvider
		public Object[][] RemoveStylefromDefaultList(ITestContext testContext) {
			String[] UserData1 = { "Fox7", "aravind.list005@myntra.com@myntra.com", "randompass", "305119","350470"};
			String[] UserData2 = { "Fox7", "aravind.list005@myntra.com@myntra.com", "randompass","333473","340162",};
			String[] UserData3 = { "Production", "aravind.list006@myntra.com", "randompass","785115","1052662"};
			String[] UserData4 = { "Production", "aravind.list007@myntra.com", "randompass","1033397","1052662"};
			String[] UserData5 = { "Functional", "aravind.lists.Automation99@myntra.com", "randompass","785115","1052662"};
			String[] UserData6 = { "Functional", "aravind.lists.Automation98@myntra.com", "randompass","1033397","1052662"};
			Object[][] dataSet = new Object[][] { UserData1, UserData2, UserData3, UserData4, UserData5, UserData6 };
			return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 2, 2);

		}
}
	