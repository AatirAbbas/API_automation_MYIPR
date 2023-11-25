package com.restassured.test;

import com.jayway.jsonpath.JsonPath;
import com.restassured.endpoints.UserEndPoints;

import com.restassured.payload.User;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java. util. UUID;
import com.restassured.endpoints.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.Writer;

public class UserTests
{
	UserEndPoints userEndPoints;
	
	static User userPayload;
	//static File filePayload;
	String Json;
	static String id;
	static String email;
	static String username;
	static String FirstName;
	static String LastName;
	static String password;
	static String token;
	static String str;
	static String refresh;
	static String message;
	static String version_id;
	static String version_id1;
	static String file_id;
	static String folder_id;
	static String random_email=RandomStringUtils.random(8, "0123456789abcdefghijkl");
	static String random_username=RandomStringUtils.random(8, "0123456789abcdefghijkl");
	static String randomfilename;
	public Logger logger;
	
	@BeforeClass
	public void setupData()
	{
		userPayload=new User();
		 
		userPayload.setFirst_name("Test");
		userPayload.setLast_name("Final");
		userPayload.setEmail("test_"+random_email+"@gmail.com");
		userPayload.setPassword("P@ssw0rd");
		userPayload.setUsername("test"+ random_username);
		userPayload.setType("individual");
		//userPayload.setName("File_"+RandomStringUtils.random(8, "0123456789abcdefghijkl"));
		//userPayload.setversion_id("");
		System.out.println("******************************************************");
		System.out.println(random_username);
		System.out.println("******************************************************");
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		logger.debug("debugging.................");
	}
	
	
	@Test(priority=1)
	public void testCreateUser()
	{
		logger.info("*****************Creating user************");
		System.out.println(random_username);
		System.out.println("******************************************************");
		System.out.println(userPayload);
		System.out.println("******************************************************");
		Response response= userEndPoints.createUser(userPayload);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		System.out.println(Json);
		id=JsonPath.read(Json, "$.result.id");
		username=JsonPath.read(Json, "$.result.username");
		email=JsonPath.read(Json, "$.result.email");
		FirstName=JsonPath.read(Json, "$.result.first_name");
		LastName=JsonPath.read(Json, "$.result.last_name");
		System.out.println("******************************************************");
		System.out.println(id);
		System.out.println("******************************************************");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("first_name"));
		Assert.assertTrue(Json.contains("last_name"));
		Assert.assertTrue(Json.contains("email"));
		Assert.assertTrue(Json.contains("username"));
		Assert.assertTrue(Json.contains("kycStatus"));
		Assert.assertTrue(Json.contains("type"));
		
		//writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
		
		logger.info("*********************User Created******************");
	}
	
	@Test(priority=2)
	public void testVerifyUser()
	{
		logger.info("*****************Verifying email************");
		
		Response response=userEndPoints.verifyUser(id);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("email_verified"));
		Assert.assertTrue(Json.contains("user_id"));
		Assert.assertTrue(Json.contains("name"));
		Assert.assertTrue(Json.contains("nickname"));
		Assert.assertTrue(Json.contains("first_name"));
		Assert.assertTrue(Json.contains("last_name"));
		Assert.assertTrue(Json.contains("id"));
		Assert.assertTrue(Json.contains("type"));
		
		logger.info("*********************User email verified******************");
	}
	
	@Test(priority=3)
	public void testLogin()
	{
		logger.info("*****************User Logging in************");
		String [][]loginrandom= {{email},{"P@ssw0rd"}};
		Response response=userEndPoints.loginUser(loginrandom);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		token=JsonPath.read(Json, "$.result.access_token");
		refresh=JsonPath.read(Json, "$.result.refresh_token");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("access_token"));
		Assert.assertTrue(Json.contains("refresh_token"));
		Assert.assertTrue(Json.contains("expires_in"));
		Assert.assertTrue(Json.contains("token_type"));
		Assert.assertTrue(Json.contains("userId"));
		
		logger.info("*****************User Logged in************");
	}
	
	@Test(priority=4)
	public void testKycUpdate()
	{
		logger.info("*****************User KYC Updating************");
		
		Response response=userEndPoints.kycUser(id);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		
		logger.info("*****************User KYC Updated************");
	}

//	@Test(priority=5)
//	public void testProfileUpdater()
//	{
//		//userPayload.setFirst_name("Testupdate");
//		//userPayload.setLast_name("Finalupdate");
//		
//		logger.info("*****************Profile Updating************");
//			
//		HashMap<String,String> profile=new HashMap<String,String>();
//		profile.put("first_name", "Test");
//		profile.put("last_name", "Final");
//		
//		Response response=userEndPoints.profileUpdateUser(token, profile);
//		System.out.println("******************************************************");
//		response.then().log().all();
//		System.out.println("******************************************************");
//		Json=response.toString();
//		System.out.println(Json);
//		Assert.assertEquals(response.getStatusCode(), 201);
//		Assert.assertTrue(Json.contains("success"));
//		Assert.assertTrue(Json.contains("User Profile Update Succesfully"));
//		
//		logger.info("*****************Profile Updated************");
//	}

	@Test(priority=6)
	public void testProfileView()
	{
		logger.info("*****************Profile Viewing************");
		
		Response response=userEndPoints.profileViewUser(token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("id"));
		Assert.assertTrue(Json.contains("first_name"));
		Assert.assertTrue(Json.contains("last_name"));
		Assert.assertTrue(Json.contains("email"));
		Assert.assertTrue(Json.contains("type"));
		Assert.assertTrue(Json.contains("username"));
		//Assert.assertTrue(Json.contains("email_verified"));
		
		logger.info("*****************Profile Viewed************");
	}
	
	@Test(priority=7)
	public void testCreateFile()
	{
		logger.info("*****************File Creating************");
		Random rand=new Random();
		//Certificate filePayload=new Certificate();
		randomfilename=RandomStringUtils.random(10, "0123456789abcdefghijkl");
		List a=userEndPoints.categories(token);
		int n= (Integer) a.get(rand.nextInt(a.size()));
		str=String.valueOf(n);
		
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("name", randomfilename);
		map.put("category_id", str);
		map.put("status", "in_progress");
		map.put("file_source", "local");
		map.put("description", "This is just a test File");
		
//		filePayload.setName(RandomStringUtils.random(sizename, "0123456789abcdefghijkl"));
//		filePayload.setversion_id(str);
//		filePayload.setFile_source("local");
//		filePayload.setStatus("in_progress");
//		filePayload.setDescription("This is just a test File");
//		System.out.println("++++++++++=================+++++++++++++++++++++++++++");
//		System.out.println(filePayload.getClass().getSimpleName());
//		System.out.println("++++++++++=================+++++++++++++++++++++++++++");
		
		Response response=userEndPoints.createfile(token, map);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		message=JsonPath.read(Json, "$.result.message");
		version_id=message.substring(42,78);
				
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("message"));
		
		logger.info("*****************File Created************");
	}
	
	@Test(priority=8)
	public void testgetFileCertificate()
	{
		logger.info("*****************Getting File Certificate************");
		
		Response response=userEndPoints.getfileCertificate(token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asPrettyString();
		
//		for(int i=0;i<=((List) response).size();i++)
//		{
//			if(JsonPath.read(Json, "$.result.data["+i+"].name").equals(randomfilename))
//			{
				file_id=JsonPath.read(Json, "$.result.data[0].id");
//			}else {
//				continue;
//			}
//		}
		
		
//		Assert.assertEquals(response.getStatusCode(), 200);
//		Assert.assertTrue(Json.contains("message"));
////		Assert.assertTrue(Json.contains("details"));
//		Assert.assertTrue(Json.contains("id"));
//		Assert.assertTrue(Json.contains("userId"));
//		Assert.assertTrue(Json.contains("asset_id_from_blockchain"));
//		Assert.assertTrue(Json.contains("certificate_link"));
//		Assert.assertTrue(Json.contains("transaction_id"));
//		Assert.assertTrue(Json.contains("file_name"));
//		Assert.assertTrue(Json.contains("created_at"));
//		Assert.assertTrue(Json.contains("fileLink"));
		
		logger.info("*****************Getting File Certificate complete************");
	}
	
	@Test(priority=9)
	public void testcreateFolder()
	{
		Random rand=new Random();
		//userPayload.setName("Folder_test_"+rand.nextInt(50));
		
		logger.info("*****************Creating Folder************");
		
		Response response=userEndPoints.createFolder(token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		folder_id=JsonPath.read(Json, "$.result.id");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("id"));
		Assert.assertTrue(Json.contains("name"));
		Assert.assertTrue(Json.contains("file_count"));
		Assert.assertTrue(Json.contains("updatedAt"));
		
		logger.info("*****************Folder Created************");
	}
	
	@Test(priority=10)
	public void testMoveFile()
	{
		Response response=userEndPoints.moveFile(file_id, token, folder_id);
		System.out.println(")))))))))))))++++++++++++++++++++++++++)))))))))))))))))");
		response.then().log().all();
		System.out.println(")))))))))))))++++++++++++++++++++++++++)))))))))))))))))");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("status"));
		Assert.assertTrue(Json.contains("message"));
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		//Assert.assertTrue(Json.contains("id"));
//		Assert.assertTrue(Json.contains(file_id));
//		Assert.assertTrue(Json.contains("name"));
//		Assert.assertTrue(Json.contains("status"));
//		Assert.assertTrue(Json.contains("user_id"));
//		Assert.assertTrue(Json.contains("version_count"));
//		Assert.assertTrue(Json.contains("category"));
//		Assert.assertTrue(Json.contains("type"));
//		Assert.assertTrue(Json.contains("icon"));
//		Assert.assertTrue(Json.contains("description"));
//		Assert.assertTrue(Json.contains("is_active"));
//		Assert.assertTrue(Json.contains("folder"));
	}
	
	@Test(priority=11)
	public void testgetFileVersion() throws InterruptedException
	{
		logger.info("*****************Getting File Version************");
		
		Response response=userEndPoints.getfileVersion(file_id, token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		//file_id=JsonPath.read(Json, "$.result.fileId");
		//Thread.sleep(11000);
		
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("message"));
		Assert.assertTrue(Json.contains("version_id"));
		Assert.assertTrue(Json.contains("fileId"));
		Assert.assertTrue(Json.contains("errorId"));
		Assert.assertTrue(Json.contains("version_name"));
		Assert.assertTrue(Json.contains("is_primary"));
		//Assert.assertTrue(Json.contains("user_status"));
		Assert.assertTrue(Json.contains("description"));
		Assert.assertTrue(Json.contains("certificate_id"));
//		Assert.assertTrue(Json.contains("certifiate_owner_name"));
//		Assert.assertTrue(Json.contains("transaction_id"));
//		Assert.assertTrue(Json.contains("thumbnail"));
//		Assert.assertTrue(Json.contains("hash"));
//		Assert.assertTrue(Json.contains("bkc_enrollment_id"));
//		Assert.assertTrue(Json.contains("user"));
		
		logger.info("*****************Getting File Version complete************");
	}
	
	@Test(priority=12, invocationCount=2)
	public void createVersion1()
	{
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("name", RandomStringUtils.random(10, "0123456789abcdefghijkl"));
		map.put("category_id", str);
		map.put("status", "in_progress");
		map.put("file_source", "local");
		map.put("description", "This is just a test File");
		map.put("file_id", file_id);
		
		Response response=UserEndPoints.createFileVersion1(token, map);
		Json=response.asString();
		System.out.println(Json);
		message=JsonPath.read(Json, "$.result.message");
		version_id1=message.substring(42,78);
		System.out.println(version_id1);
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("message"));
			
	}
	
//	@Test(priority=11)
//	public void downloadCertificate()
//	{
//		Response response=UserEndPoints.downloadFile(token, version_id);
//		Json=response.asString();
//		response.then().log().all();
//		System.out.println(Json);
//		
//		Assert.assertEquals(response.getStatusCode(), 201);
//		Assert.assertTrue(Json.contains("message"));
//		Assert.assertTrue(Json.contains("success"));
//		Assert.assertTrue(Json.contains("result"));
//		
//	}
	
	@Test(priority=13)
	public void testSendCertificatetoRecycleBin()
	{
		logger.info("*****************Burning Certificate************");
		
		Response response=userEndPoints.moveFileToRecycleBin(file_id, token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("Certificate(s)/Folder(s) moved to recycle bin."));   //Requested files has been moved to Recycle-bin
		
		logger.info("*****************Certificate Burnt************");
	}
	
	@Test(priority=14)
	public void testrenameFolder()
	{
		Random rand=new Random();
		//userPayload.setName("Folder_test_"+rand.nextInt(50));
		
		logger.info("*****************Renaming Folder************");
		
		Response response=userEndPoints.renameFolder(token, folder_id);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("id"));
		Assert.assertTrue(Json.contains("name"));
		Assert.assertTrue(Json.contains("file_count"));
		Assert.assertTrue(Json.contains("updatedAt"));
		Assert.assertTrue(Json.contains("userId"));
		Assert.assertTrue(Json.contains("inTrash"));
		Assert.assertTrue(Json.contains("trashedAt"));
		Assert.assertTrue(Json.contains("deletedAt"));
		
		
		logger.info("*****************Folder renamed************");
	}
	
	@Test(priority=15)
	public void testdeleteFolder()
	{
		logger.info("*****************Folder deleting************");
		
		Response response=userEndPoints.deleteFolder(token, folder_id);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("Certificate(s)/Folder(s) moved to recycle bin."));
		Assert.assertTrue(Json.contains("message"));
		//Assert.assertTrue(Json.contains("affected"));
				
		logger.info("*****************Folder deleted************");
	}
	
	@Test(priority=16)
	public void testRestoreFileFromRecycleBin()
	{
		Response response=userEndPoints.restoreFileFromRecycleBin(token, folder_id, file_id);
		System.out.println(")))))))))))))++++++++++++++++++++++++++)))))))))))))))))");
		response.then().log().all();
		System.out.println(")))))))))))))++++++++++++++++++++++++++)))))))))))))))))");
		Json=response.asString();
		testSendCertificatetoRecycleBin();
		testdeleteFolder();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(Json.contains("status"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("message"));
		//Assert.assertTrue(Json.contains("Requested folders restored from Recycle-bin"));
	}
	
	@Test(priority=17)
	public void testDeleteFilePermanently()
	{
		Response response=userEndPoints.deleteFilePermanently(token, folder_id, file_id);
		response.then().log().all();
		Json=response.asString();
	}
	
	@Test(priority=18)
	public void testgetAllFolders()
	{
		logger.info("*****************All Folders************");
		
		Response response=userEndPoints.getAllFolder(token);
		System.out.println("******************************************************");
		response.then().log().all();
		System.out.println("******************************************************");
		Json=response.asString();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(Json.contains("success"));
		Assert.assertTrue(Json.contains("result"));
		Assert.assertTrue(Json.contains("folders"));
		Assert.assertTrue(Json.contains("message"));
		Assert.assertTrue(Json.contains("total"));
		Assert.assertTrue(Json.contains("page"));
		Assert.assertTrue(Json.contains("limit"));
		Assert.assertTrue(Json.contains("totalPages"));
		
		logger.info("*****************All Folders completed************");
	}
	
	@Test(priority=19)
	public void testDeleteFileVersion()
	{
		Response response=userEndPoints.deleteFileVersion(token, version_id1);
		response.then().log().all();
		Json=response.asString();
		
	}
	
	@Test(priority=20)
	public void testRestoreFileVersion()
	{
		Response response=userEndPoints.restoreFileVersion(token, version_id1);
		response.then().log().all();
		Json=response.asString();
	}
}
