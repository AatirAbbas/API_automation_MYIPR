package com.restassured.test;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import com.restassured.endpoints.BaseFunctions;
import com.restassured.endpoints.UserEndPoints;
import com.restassured.payload.User;
import com.testing.framework.EmailUtils;

import io.restassured.response.Response;
import java.util.*;
import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java. util. UUID;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Random;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;

public class UserNegativeTests 
{
	UserEndPoints userEndpoints;
	
	String Json;
	 static String FempL25;
	 static String LempF25;
	 static String F25L25;
	 static String FempLemp;
	 static String validation_message;
	 
	 static String mismatchlogin;
	 static String EexistPemp;
	 static String EempPemp;
	 static String EempPent;
	 static String EnotexistPent;
	 static String token;
	 
	 static String Nemp;
	 static String Nmax;
	 
	static User userpayload; //= new User();
	static int naming=BaseFunctions.randomfirstlastname();
	static String firstname=RandomStringUtils.random(naming,"0123456789abcdefghijklmnopqrstuvwxyz");
	static String lastname=RandomStringUtils.random(naming,"0123456789abcdefghijklmnopqrstuvwxyz");
	
	@BeforeClass
	public void setInvalidFirstLastName()
	{
		userpayload=new User();
//		System.out.println("**************************************************");
//		System.out.println(BaseFunctions.randomfirstlastname());
//		System.out.println("**************************************************");
//		System.out.println(RandomStringUtils.random(naming, "0123456789abcdefghijklmnopqrstuvwxyz"));
		userpayload.setFirst_name(firstname);
		userpayload.setLast_name(lastname);
		userpayload.setEmail("test_"+RandomStringUtils.random(8, "0123456789abcdefghijkl")+"@gmail.com");
		userpayload.setUsername("test"+ RandomStringUtils.random(8, "0123456789abcdefghijkl"));
		userpayload.setPassword("P@ssw0rd");
		userpayload.setType("individual");
	}
	@Test(priority=1)
	public void createUser()
	{
		
		System.out.println("****************************************************");
		System.out.println(userpayload.getFirst_name());
		System.out.println("****************************************************");
		Response response=userEndpoints.createUser(userpayload);
		response.then().log().all();
		Json=response.asString();
		validation_message=JsonPath.read(Json, "$.message");
		
		FempL25="Validation errors with properties Invalid first name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,first_name must be longer than or equal to 1 characters,first_name should not be empty,Invalid last name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,last_name must be shorter than or equal to 25 characters";
		
		LempF25="Validation errors with properties Invalid first name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,first_name must be shorter than or equal to 25 characters,Invalid last name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,last_name must be longer than or equal to 1 characters,last_name should not be empty";
		
		F25L25="Validation errors with properties Invalid first name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,first_name must be shorter than or equal to 25 characters,Invalid last name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,last_name must be shorter than or equal to 25 characters";
		
		FempLemp="Validation errors with properties Invalid first name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,first_name must be longer than or equal to 1 characters,first_name should not be empty,Invalid last name format. The first character should be an alphabet, and only period(.), apostrophes('), dash(-), and space are allowed.,last_name must be longer than or equal to 1 characters,last_name should not be empty;";
		
		   

		         System.out.println(validation_message);
		         System.out.println("$$$$$$$$$$$$$$$################%%%%%%%%%%%%%%%%%%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		         System.out.println(FempLemp);
		
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertTrue(Json.contains("errorField"));
		Assert.assertTrue(Json.contains("first_name,last_name"));
	
		if((validation_message.equalsIgnoreCase(FempL25)))
		{
			System.out.println("Fistname empty and Lastname more than 25 char");
		}else if(validation_message.equalsIgnoreCase(LempF25))
		{
			System.out.println("Fistname more than 25 char and Lastname empty");
		}else if(validation_message.equalsIgnoreCase(F25L25))
		{
			System.out.println("Fistname more than 25 char and Lastname more than 25 char");
		}else if(validation_message.equalsIgnoreCase(FempLemp))
		{
			System.out.println("Fistname empty and Lastname empty");
		}else {
			System.out.println("Test case is failing");
		}
	}
	@Test(priority=2)
	public void loginNegative()
	{
		
		Response response=userEndpoints.loginUser(BaseFunctions.loginNegativedata());
		response.then().log().all();
		Json=response.asString();
		/*token=JsonPath.read(Json, "$.result.access_token");*/
		validation_message=JsonPath.read(Json, "$.message");
		
		mismatchlogin="Wrong email or password.";
		
		EexistPemp="Validation errors with properties password must be longer than or equal to 8 characters,Password should be between 8 to 20 	characters. Should only have alphabets, numbers and special characters  \"! @ # $ % & * ( ) - + = ^\". Should have at least one uppercase letter";
		
		EempPemp="Validation errors with properties email should not be empty,password must be longer than or equal to 8 characters,Password should be between 8 to 20 characters. Should only have alphabets, numbers and special characters  \"! @ # $ % & * ( ) - + = ^\". Should have at least one uppercase letter";
		
		EempPent="Validation errors with properties email should not be empty";
		
		EnotexistPent="User does not exist for the given email or username.";
		
		if(validation_message.equalsIgnoreCase(mismatchlogin))
		{
			Assert.assertEquals(response.getStatusCode(), 400);
			Assert.assertTrue(Json.contains("9008"));
			System.out.println("email and password does not match");
		}else if(validation_message.equalsIgnoreCase(EexistPemp))
		{
			Assert.assertEquals(response.getStatusCode(), 400);
			Assert.assertTrue(Json.contains("errorField"));
			Assert.assertTrue(Json.contains("password"));
			Assert.assertTrue(Json.contains("customErrorNumber"));
			Assert.assertTrue(Json.contains("-2"));
			System.out.println("email correct and password is blank");
		}else if(validation_message.equalsIgnoreCase(EempPemp))
		{
			Assert.assertEquals(response.getStatusCode(), 400);
			Assert.assertTrue(Json.contains("errorField"));
			Assert.assertTrue(Json.contains("email,password"));
			Assert.assertTrue(Json.contains("customErrorNumber"));
			Assert.assertTrue(Json.contains("-2"));
			System.out.println("Email and Password are empty");
		}else if(validation_message.equalsIgnoreCase(EempPent))
		{
			Assert.assertEquals(response.getStatusCode(), 400);
			Assert.assertTrue(Json.contains("errorField"));
			Assert.assertTrue(Json.contains("email"));
			Assert.assertTrue(Json.contains("customErrorNumber"));
			Assert.assertTrue(Json.contains("-2"));
			System.out.println("Email is empty but password entered");
		}else if(validation_message.equalsIgnoreCase(EnotexistPent))
		{
			Assert.assertEquals(response.getStatusCode(), 400);
			Assert.assertTrue(Json.contains("customErrorNumber"));
			Assert.assertTrue(Json.contains("1000"));
			System.out.println("Email is not registered");
		}else
		{
			token=JsonPath.read(Json, "$.result.access_token");
			Assert.assertTrue(Json.contains(token));
			System.out.println("Successful sign in");
		}
		
	}
	@Test(priority=3)
	public void createfileNegative()
	{
		Random rand=new Random();
		int []namesize= {0,51,52,53,54,55,56};
		int sizename=namesize[rand.nextInt(namesize.length)];
		
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("name", RandomStringUtils.random(sizename, "0123456789abcdefghijkl#@!$%^&mnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		map.put("category_id", UserTests.str);
		map.put("status", "in_progress");
		map.put("file_source", "local");
		map.put("description", "This is just a test File");
		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
		System.out.println(UserTests.token);
		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
		Response response=UserEndPoints.createfile(UserTests.token, map);
		response.then().log().all();
		Json=response.asString();
		validation_message=JsonPath.read(Json, "$.message");
		
		Nemp="Validation errors with properties Invalid file name format. File name must be between 1 and 50 characters long and can have numbers, special characters, and spaces (but not at the start).,name must be longer than or equal to 1 characters,name should not be empty";
				
		Nmax="Validation errors with properties Invalid file name format. File name must be between 1 and 50 characters long and can have numbers, special characters, and spaces (but not at the start).,name must be shorter than or equal to 50 characters";
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertTrue(Json.contains("errorField"));
		Assert.assertTrue(Json.contains("customErrorNumber"));
		Assert.assertTrue(Json.contains("name"));
		
		if(validation_message.equals(Nemp))
		{
			System.out.println("Name field is empty");
		}else if(validation_message.equals(Nmax))
		{
			System.out.println("Name field is more than 50 char");
		}else {
			System.out.println("Test is failing");
		}
		
	}
	
//	@Test(priority=4)
//	public void testLogout()
//	{
//		//logger.info("*****************User Logout************");
//		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
//		System.out.println(UserTests.token);
//		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
//		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
//		System.out.println(UserTests.refresh);
//		System.out.println("@@@@@@@!!!!!!!!!!!%%%%%%%%%%%%");
//		Response response=UserEndPoints.logoutUser(UserTests.token, UserTests.refresh);
//		System.out.println("******************************************************");
//		response.then().log().all();
//		System.out.println("******************************************************");
//		Json=response.asString();
//		
//		Assert.assertEquals(response.getStatusCode(), 200);
//		Assert.assertTrue(Json.contains("success"));
//		Assert.assertTrue(Json.contains("result"));
//		
//		//logger.info("*****************User Logged out************");
//	}
	
//	@AfterTest
//	public void afterTest() 
//	{
//		      // Recipient's email ID needs to be mentioned.
//		      String to = "Nitin.Kumar@mai.io";
//
//		      // Sender's email ID needs to be mentioned
//		      String from = "Fahim.Abbas@mai.io";
//
//		      final String username = "fahimzaidi75@gmail.com";//change accordingly
//		      final String password = "kisaaeman";//change accordingly
//
//		      // Assuming you are sending email through relay.jangosmtp.net
//		      String host = "smtp.office365.com";
//
//		      Properties props = new Properties();
//		      props.put("mail.smtp.auth", "true");
//		      props.put("mail.smtp.starttls.enable", "true");
//		      props.put("mail.smtp.host", host);
//		      props.put("mail.smtp.port", "587");
//
//		      // Get the Session object.
//		      Session session = Session.getInstance(props,
//		         new javax.mail.Authenticator() {
//		            protected PasswordAuthentication getPasswordAuthentication() {
//		               return new PasswordAuthentication(username, password);
//		            }
//		         });
//
//		      try {
//		         // Create a default MimeMessage object.
//		         Message message = new MimeMessage(session);
//
//		         // Set From: header field of the header.
//		         message.setFrom(new InternetAddress(from));
//
//		         // Set To: header field of the header.
//		         message.setRecipients(Message.RecipientType.TO,
//		            InternetAddress.parse(to));
//
//		         // Set Subject: header field
//		         message.setSubject("Test Execution result");
//
//		         // Create the message part
//		         BodyPart messageBodyPart = new MimeBodyPart();
//
//		         // Now set the actual message
//		         messageBodyPart.setText("Hi team, execution is successful, Please find attached  ");
//
//		         // Create a multipar message
//		         Multipart multipart = new MimeMultipart();
//
//		         // Set text message part
//		         multipart.addBodyPart(messageBodyPart);
//		         
//		         // Part two is attachment
//		         messageBodyPart = new MimeBodyPart();
//		         String filename = "C:\\\\Users\\\\fahim.abbas\\\\eclipse-workspace\\\\MYIPRAutomation.v2\\\\reports\\\\Test-Report-2023.09.20.13.34.09.html";
//		         DataSource source = new FileDataSource(filename);
//		         messageBodyPart.setDataHandler(new DataHandler(source));
//		         messageBodyPart.setFileName(filename);
//		         multipart.addBodyPart(messageBodyPart);
//
//		         // Send the complete message parts
//		         message.setContent(multipart);
//
//		         // Send message
//		         Transport.send(message);
//
//		         System.out.println("Sent message successfully....");
//		  
//		      } catch (MessagingException e) {
//		    	  System.out.println("#############################");
//		    	  System.out.println(e.getMessage());
//		    	  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		         throw new RuntimeException(e);
//		      }
//		   }
		
//	public void afterTest() throws Exception
//	{
//		EmailUtils emailutils=new EmailUtils();
//		Properties prop=new Properties();
//		File src=new File("C:\\Users\\fahim.abbas\\eclipse-workspace\\MYIPRAutomation.v2\\src\\test\\java\\com\\restassured\\utilities\\email_config.properties");
//		FileInputStream fis=new FileInputStream(src);
//		prop.load(fis);
//		List<String> attachment=new ArrayList<String>();
//		attachment.add("C:\\Users\\fahim.abbas\\eclipse-workspace\\MYIPRAutomation.v2\\reports\\Test-Report-1899.12.31.00.00.00.html");
//		emailutils.sendUsingGmail(prop, "Test Execution result ", "Hi team, execution is successful, Please find attached  ", attachment);
//	}
}
