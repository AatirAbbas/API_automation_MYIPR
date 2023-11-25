package com.restassured.endpoints;

import java.io.StringWriter;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

//import com.restassured.reports.LogStatus;


public class BaseFunctions
{
	static Random rand=new Random();
	static int b;
	protected static StringWriter writer;
	
	public static int randomnumber()
	{
		int a[]= {1,4,2,5,6,7,8,9,10,11,12,13,14,15,17,18,19,20,21,22,3};
		
		for(int i=0;i<a.length;i++)
		{
			b=rand.nextInt(23);
			if(a[i]!=b)
			{
				continue;
			}else
			{
				break;
			}
		}
		return b;
	}
	
	public static int randomfirstlastname()
	{
		int a[]= {0,26,27,28,29,30};
		int num=a[rand.nextInt(a.length)];
		System.out.println(num);
			return num;
	}
	
	public static String[][] loginNegativedata()
	{
		//String useremail[]= {"sharmila217@yopmail.com", "sharmila300@yopmail.com", "sharmila66@yopmail.com","", "IDonotknow@gmail.com"};
		//String userpass[]= {"Admin@1234", "Admin@12345678", "Sharmila@4321", "", "jfgjfg@#67Q"};
		String userneglogin[][]= {{"sharmila217@yopmail.com", "Admin@1234"}, {"sharmila300@yopmail.com", "Admin@12345678"}, {"sharmila66@yopmail.com", "Sharmila@4321"}, {"", ""},{"IDonotknow@gmail.com", "jfgjfg@#67Q"}}; 
			
			
		int numemail= rand.nextInt(userneglogin[0].length);
		int numpass= rand.nextInt(userneglogin[0].length);
	
		System.out.println("########################");
		System.out.println("userneglogin[0][0] is " +userneglogin[0][0]);
		System.out.println("userneglogin[0][1] is " +userneglogin[0][1]);
		
		System.out.println("userneglogin[1][0] is " +userneglogin[1][0]);
		System.out.println("userneglogin[1][1] is " +userneglogin[1][1]);
		
		System.out.println("userneglogin[2][0] is " +userneglogin[2][0]);
		System.out.println("userneglogin[2][1] is " +userneglogin[2][1]);
		System.out.println("########################");
		
		String col1= userneglogin[numemail][0];
		String col2= userneglogin[numpass][1];
		//System.out.println(col1);
		//System.out.println(col2);
		int indexOfYellow = ArrayUtils.indexOf(userneglogin, "sharmila217@yopmail");
		
		System.out.println("Index of sharmila217@yopmail is " +indexOfYellow);
		
		String loginrandom[][] = {{col1},{col2}}; //= new String[col1][col2];
//		loginrandom[0][0]=col1;
//		loginrandom[0][1]=col2;
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("[0][0] is " +loginrandom[0][0]);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("[1][0] is " +loginrandom[1][0]);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return loginrandom;
	}

	
//	public void writeRequestAndResponseInReport(String request,String response,String message) 
//	{
//		LogStatus.info("---- Request ---");
//		formatAPIAndLogInReport(request);
//		LogStatus.info("---- Response ---");
//		formatAPIAndLogInReport(response);
//		LogStatus.info("---- Message ---");
//		formatAPIAndLogInReport(message);
//	}
//	
//	protected void formatAPIAndLogInReport(String content) 
//	{
//		String prettyPrint = content.replace("\n", "<br>");
//		LogStatus.info("<pre>" + prettyPrint + "</pre>");
//	}

}
