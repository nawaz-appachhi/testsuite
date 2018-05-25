package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.idpservice.IDPServiceHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.lordoftherings.legolas.Commons;

public class OrderFlowWithCouponAndDiscountDP {
	public static long currentTime = System.currentTimeMillis()/1000;
	static String startTime = "" , endTime = "";
	Configuration con = new Configuration("./Data/configuration");
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	static IDPServiceHelper idpServiceHelper = new IDPServiceHelper();

	Commons commonUtil = new Commons();
	
	

	@DataProvider
	public static Object[][] OrderFlowDiscountDP(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+3600000);
		String[] arr1 = {endTime,startTime,"31","auomationcoupon123@myntra.com","123456","1","ADD"};
		
		
       Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}

	
	
	
	
	@DataProvider
	public static Object[][] connectionPoolTst100Usrs(ITestContext testContext)
	{
		String[] arr1 = {"niuku1@myntra.com","password1","1","ADD"};
		String[] arr2 = {"bisnv1@myntra.com","password1","1","ADD"};
		String[] arr3 = {"jmpjd1@myntra.com","password1","1","ADD"};
		String[] arr4 = {"pialm1@myntra.com","password1","1","ADD"};
		String[] arr5 = {"pnkqp1@myntra.com","password1","1","ADD"};
		String[] arr6 = {"shyfp1@myntra.com","password1","1","ADD"};
		String[] arr7 = {"kjlml1@myntra.com","password1","1","ADD"};
		String[] arr8 = {"uvsqu1@myntra.com","password1","1","ADD"};

        String[] arr9 = {"sejuw1@myntra.com","password1","1","ADD"};

        String[] arr10 = {"jithq1@myntra.com","password1","1","ADD"};

        String[] arr11 = {"oiqmg1@myntra.com","password1","1","ADD"};

        String[] arr12 = {"opycl1@myntra.com","password1","1","ADD"};

        String[] arr13 = {"jtsaq1@myntra.com","password1","1","ADD"};

        String[] arr14 = {"jbnju1@myntra.com","password1","1","ADD"};

        String[] arr15 = {"ukxkg1@myntra.com","password1","1","ADD"};

        String[] arr16 = {"tnopb1@myntra.com","password1","1","ADD"};

        String[] arr17 = {"bvngj1@myntra.com","password1","1","ADD"};

        String[] arr18 = {"cgngu1@myntra.com","password1","1","ADD"};

        String[] arr19 = {"ywedt1@myntra.com","password1","1","ADD"};

        String[] arr20 = {"kwmwy1@myntra.com","password1","1","ADD"};

        String[] arr21 = {"qulwf1@myntra.com","password1","1","ADD"};

        String[] arr22 = {"kpplb1@myntra.com","password1","1","ADD"};

        String[] arr23 = {"rnewq1@myntra.com","password1","1","ADD"};

        String[] arr24 = {"hwqny1@myntra.com","password1","1","ADD"};

        String[] arr25 = {"cogbt1@myntra.com","password1","1","ADD"};

        String[] arr26 = {"nvbbx1@myntra.com","password1","1","ADD"};

        String[] arr27 = {"yretj1@myntra.com","password1","1","ADD"};

        String[] arr28 = {"hdfyq1@myntra.com","password1","1","ADD"};

        String[] arr29 = {"ydwal1@myntra.com","password1","1","ADD"};

        String[] arr30 = {"aqfvg1@myntra.com","password1","1","ADD"};

        String[] arr31 = {"gixkt1@myntra.com","password1","1","ADD"};

        String[] arr32 = {"cnesq1@myntra.com","password1","1","ADD"};

        String[] arr33 = {"egpic1@myntra.com","password1","1","ADD"};

        String[] arr34 = {"tijca1@myntra.com","password1","1","ADD"};

        String[] arr35 = {"wjaqd1@myntra.com","password1","1","ADD"};

        String[] arr36 = {"fcfes1@myntra.com","password1","1","ADD"};

        String[] arr37 = {"pxrgs1@myntra.com","password1","1","ADD"};

        String[] arr38 = {"weqpk1@myntra.com","password1","1","ADD"};

        String[] arr39 = {"oxbtv1@myntra.com","password1","1","ADD"};

        String[] arr40 = {"xiscs1@myntra.com","password1","1","ADD"};

        String[] arr41 = {"ehpco1@myntra.com","password1","1","ADD"};

        String[] arr42 = {"uvmja1@myntra.com","password1","1","ADD"};

        String[] arr43 = {"rnjih1@myntra.com","password1","1","ADD"};

        String[] arr44 = {"wowgy1@myntra.com","password1","1","ADD"};

        String[] arr45 = {"mwskb1@myntra.com","password1","1","ADD"};

        String[] arr46 = {"xcpuq1@myntra.com","password1","1","ADD"};

        String[] arr47 = {"xlclj1@myntra.com","password1","1","ADD"};

        String[] arr48 = {"etqdn1@myntra.com","password1","1","ADD"};

        String[] arr49 = {"cbte1@myntra.com","password1","1","ADD"};

        String[] arr50 = {"qklob1@myntra.com","password1","1","ADD"};

        String[] arr51 = {"juubk1@myntra.com","password1","1","ADD"};

        String[] arr52 = {"mifdh1@myntra.com","password1","1","ADD"};

        String[] arr53 = {"bcrqa1@myntra.com","password1","1","ADD"};

        String[] arr54 = {"figks1@myntra.com","password1","1","ADD"};

        String[] arr55 = {"gxfdx1@myntra.com","password1","1","ADD"};

        String[] arr56 = {"jkept1@myntra.com","password1","1","ADD"};

        String[] arr57 = {"qbmff1@myntra.com","password1","1","ADD"};

        String[] arr58 = {"abguy1@myntra.com","password1","1","ADD"};

        String[] arr59 = {"jsdxf1@myntra.com","password1","1","ADD"};

        String[] arr60 = {"smovp1@myntra.com","password1","1","ADD"};

        String[] arr61 = {"xrjqv1@myntra.com","password1","1","ADD"};

        String[] arr62 = {"kebmh1@myntra.com","password1","1","ADD"};

        String[] arr63 = {"kdwfp1@myntra.com","password1","1","ADD"};

        String[] arr64 = {"ogypf1@myntra.com","password1","1","ADD"};

        String[] arr65 = {"kfkap1@myntra.com","password1","1","ADD"};

        String[] arr66 = {"wekmv1@myntra.com","password1","1","ADD"};

        String[] arr67 = {"mlmio1@myntra.com","password1","1","ADD"};

        String[] arr68 = {"yvpef1@myntra.com","password1","1","ADD"};

        String[] arr69 = {"jhubb1@myntra.com","password1","1","ADD"};

        String[] arr70 = {"rinre1@myntra.com","password1","1","ADD"};

        String[] arr71 = {"atxlb1@myntra.com","password1","1","ADD"};

        String[] arr72 = {"enitc1@myntra.com","password1","1","ADD"};

        String[] arr73 = {"sfsjq1@myntra.com","password1","1","ADD"};

        String[] arr74 = {"slfqp1@myntra.com","password1","1","ADD"};

        String[] arr75 = {"hjbdk1@myntra.com","password1","1","ADD"};

        String[] arr76 = {"oacqw1@myntra.com","password1","1","ADD"};

        String[] arr77 = {"lmvki1@myntra.com","password1","1","ADD"};

        String[] arr78 = {"ldyvv1@myntra.com","password1","1","ADD"};

        String[] arr79 = {"mvpra1@myntra.com","password1","1","ADD"};

        String[] arr80 = {"ssblm1@myntra.com","password1","1","ADD"};

        String[] arr81 = {"qmhgf1@myntra.com","password1","1","ADD"};

        String[] arr82 = {"obngl1@myntra.com","password1","1","ADD"};

        String[] arr83 = {"hcjdm1@myntra.com","password1","1","ADD"};

        String[] arr84 = {"xmlin1@myntra.com","password1","1","ADD"};

        String[] arr85 = {"fsmpp1@myntra.com","password1","1","ADD"};

        String[] arr86 = {"ejdtc1@myntra.com","password1","1","ADD"};

        String[] arr87 = {"tubgu1@myntra.com","password1","1","ADD"};

        String[] arr88 = {"qecsx1@myntra.com","password1","1","ADD"};

        String[] arr89 = {"vkvww1@myntra.com","password1","1","ADD"};

        String[] arr90 = {"ewjyn1@myntra.com","password1","1","ADD"};

        String[] arr91 = {"bcsdy1@myntra.com","password1","1","ADD"};

        String[] arr92 = {"vdhmy1@myntra.com","password1","1","ADD"};

        String[] arr93 = {"jkphe1@myntra.com","password1","1","ADD"};

        String[] arr94 = {"edfeg1@myntra.com","password1","1","ADD"};

        String[] arr95 = {"ruysy1@myntra.com","password1","1","ADD"};

        String[] arr96 = {"awniy1@myntra.com","password1","1","ADD"};

        String[] arr97 = {"edjey1@myntra.com","password1","1","ADD"};

        String[] arr98 = {"vpiai1@myntra.com","password1","1","ADD"};

        String[] arr99 = {"eupxt1@myntra.com","password1","1","ADD"};

        String[] arr100 = {"jfdep1@myntra.com","password1","1","ADD"};

        String[] arr101= {"qulwf1@myntra.com","password1","1","ADD"};

        String[] arr102= {"kwmwy1@myntra.com","password1","1","ADD"};
        String[] arr103 = {"ywedt1@myntra.com","password1","1","ADD"};

        String[] arr104 = {"cgngu1@myntra.com","password1","1","ADD"};

        String[] arr105 = {"bvngj1@myntra.com","password1","1","ADD"};

        String[] arr106 = {"tnopb1@myntra.com","password1","1","ADD"};

        String[] arr107 = {"ukxkg1@myntra.com","password1","1","ADD"};

        String[] arr108 = {"jbnju1@myntra.com","password1","1","ADD"};

        String[] arr109 = {"jtsaq1@myntra.com","password1","1","ADD"};

        String[] arr110 = {"opycl1@myntra.com","password1","1","ADD"};

        String[] arr111 = {"oiqmg1@myntra.com","password1","1","ADD"};

        String[] arr112 = {"jithq1@myntra.com","password1","1","ADD"};

        String[] arr113= {"sejuw1@myntra.com","password1","1","ADD"};

        String[] arr114= {"uvsqu1@myntra.com","password1","1","ADD"};

        String[] arr115 = {"kjlml1@myntra.com","password1","1","ADD"};

        String[] arr116= {"pnkqp1@myntra.com","password1","1","ADD"};

        String[] arr117= {"pialm1@myntra.com","password1","1","ADD"};

        String[] arr118 = {"jmpjd1@myntra.com","password1","1","ADD"};

        String[] arr119 = {"bisnv1@myntra.com","password1","1","ADD"};

        String[] arr120= {"niuku1@myntra.com","password1","1","ADD"};
        
        String[] arr121 = {"tubgu1@myntra.com","password1","1","ADD"};

        String[] arr122 = {"qecsx1@myntra.com","password1","1","ADD"};

        String[] arr123 = {"vkvww1@myntra.com","password1","1","ADD"};

        String[] arr124 = {"ewjyn1@myntra.com","password1","1","ADD"};

        String[] arr125 = {"bcsdy1@myntra.com","password1","1","ADD"};

        String[] arr126 = {"vresd1@myntra.com","password1","1","ADD"};

        String[] arr127 = {"tcbdc1@myntra.com","password1","1","ADD"};

        String[] arr128 = {"cjqwe1@myntra.com","password1","1","ADD"};

        String[] arr129 = {"ertqj1@myntra.com","password1","1","ADD"};

        String[] arr130 = {"tpuja1@myntra.com","password1","1","ADD"};

        String[] arr131 = {"qnsgq1@myntra.com","password1","1","ADD"};

        String[] arr132 = {"ncsia1@myntra.com","password1","1","ADD"};

        String[] arr133 = {"omhmf1@myntra.com","password1","1","ADD"};

        String[] arr134 = {"rgwgr1@myntra.com","password1","1","ADD"};

        String[] arr135 = {"hlwrb1@myntra.com","password1","1","ADD"};

        String[] arr136 = {"awveq1@myntra.com","password1","1","ADD"};

        String[] arr137 = {"xmimd1@myntra.com","password1","1","ADD"};

        String[] arr138 = {"hkyqu1@myntra.com","password1","1","ADD"};

        String[] arr139 = {"wnwrd1@myntra.com","password1","1","ADD"};

        String[] arr140 = {"dtxyf1@myntra.com","password1","1","ADD"};

        String[] arr141 = {"dvofx1@myntra.com","password1","1","ADD"};

        String[] arr142 = {"bjuea1@myntra.com","password1","1","ADD"};

        String[] arr143 = {"jjhtu1@myntra.com","password1","1","ADD"};

        String[] arr144 = {"nedem1@myntra.com","password1","1","ADD"};

        String[] arr145 = {"jdujo1@myntra.com","password1","1","ADD"};

        String[] arr146 = {"gbyuu1@myntra.com","password1","1","ADD"};

        
		
		
       Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr11,arr12,arr13,arr14,arr15,arr16,arr17,arr18,arr19,arr20,arr21,arr22,arr23,arr24,arr25,arr26,arr27,arr27,arr28,arr29,arr30,arr31,arr32,arr33,arr34,arr35,arr36,arr37,arr38,arr39,arr40,arr41,arr42,arr43,arr44,arr45,arr46,arr47,arr48,arr49,arr50,arr51,arr52,arr53,arr54,arr55,arr56,arr57,arr58,arr59,arr60,arr61,arr62,arr63,arr64,arr65,arr66,arr67,arr68,arr69,arr70,arr71,arr72,arr73,arr74,arr75,arr76,arr77,arr78,arr79,arr80,arr81,arr82,arr83,arr84,arr85,arr86,arr87,arr88,arr89,arr90,arr91,arr92,arr93,arr94,arr95,arr96,arr97,arr98,arr99,arr100,arr101,arr102,arr103,arr104,arr105,arr106,arr107,arr108,arr109,arr110,arr111,arr112,arr113,arr114,arr115,arr116,arr117,arr118,arr119,arr120,arr121,arr122,arr123,arr124,arr125,arr126,arr127,arr128,arr129,arr130,arr131,arr132,arr133,arr134,arr135,arr136,arr137,arr138,arr139,arr140,arr141,arr142,arr143,arr144,arr145,arr146};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 146, 146);
	}
	
	public static String generatenumber(){
	     Double number = (Math.floor(Math.random() * 90 + 10));
	     Integer num = number.intValue();
	     
	     String Randomnum = String.valueOf(num);
	      return Randomnum;

		 
	 }
	
	
	@DataProvider
	public static Object[][] connectionPool(ITestContext testContext)
	{
		
		String randomemail = generateRandomString01();
		String SIgnup= v_connectionPoolFor100Users_myntraemp20_fix(randomemail, randomemail);
		String[] arr1 = {SIgnup,"password1","1","ADD"};
		

        
		
		
       Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
	
	
	public static String v_connectionPoolFor100Users_myntraemp20_fix(String login, String email) //String email
    
    { 
        RequestGenerator signUpReqGen = idpServiceHelper.performSignUpOperation(login, "password1", "C", "M", "Mr", "FirstName", "LastName",email, "9898989898");//email
        System.out.println("\nPrinting IDPService signUp API response :\n\n"+signUpReqGen.respvalidate.returnresponseasstring());
        String emialishnedup = signUpReqGen.respvalidate.returnresponseasstring();
        return emialishnedup;
    }
	 
	public static String generateRandomString01()
    {
        
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        String conctString="";
        StringBuilder buffer = new StringBuilder(targetStringLength);
        List<String> emails=new ArrayList<String>();
        
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int) 
                (new Random().nextFloat() * (rightLimit - leftLimit));
                buffer.append((char) randomLimitedInt);
                }
                String generatedString = buffer.toString();
                conctString=generatedString+"@gmail.com";
                
                System.out.println("Random String  for myntra signup is : "+conctString);
        
        
        return conctString;
        
    }

}
