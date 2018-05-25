package com.myntra.apiTests.common.createOrder;

import java.util.Random;

public class GenerateRandomData {
	
	private final static String[] userNameAndPassword = {"notifyme763@gmail.com:myntra@123"};//,"shekhar:123457","Rahul:123458"};
	private final static Long[] skuIds = {3831L,3832L,3833L};
	private final static Long[] pincodes = {560068L};//,560069L,560070L};
			
	private int getRandonIndexFromList(int listSize){
		Random rand = new Random();
		return rand.nextInt(listSize);
	}
	
	public String[] getRandomUserNameAndPassword(){
		return userNameAndPassword[getRandonIndexFromList(userNameAndPassword.length)].split(":");
	}
	
	public Long getRandomSkuId(){
		return skuIds[getRandonIndexFromList(skuIds.length)];
	}
	
	public Long getRandomPincode(){
		return pincodes[getRandonIndexFromList(pincodes.length)];
	}
	
	
	public static void main(String args[]){
		
		String[] userNameAndPassword = new GenerateRandomData().getRandomUserNameAndPassword();
		System.out.println(userNameAndPassword[0]
				+"  "+ userNameAndPassword[1]);
	}

}
