package com.restassured.endpoints;


import com.restassured.payload.User;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;

import org.json.JSONArray;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;

public class UserEndPoints 
{
	static Random rand=new Random();
	static BaseFunctions base=new BaseFunctions();
	
	public static Response createUser(User payload)
	{
		System.out.println("**************************");
		Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(payload)
				
		.when()
			.post(Routes.user_url);
		System.out.println("**************************");
		System.out.println(response.asString());
		return response;
	}
	
	public static Response verifyUser(String id)
	{
		HashMap ver=new HashMap();
		ver.put("userId", id);
		Response response=
			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", Routes.verify_auth)
				.body(ver)
		.when()
			.patch(Routes.verify_email);
		
		return response;
	}
	
	public static Response loginUser(String[][] loginrandom)  //String email
	{
		HashMap login=new HashMap();
		login.put("email", loginrandom[0][0]); //email ;
		login.put("password", loginrandom[1][0]);  //"P@ssw0rd"  loginrandom[1][0]);
		
		Response response= 
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(login)
			.when()
				.post(Routes.login_url);
		
		return response;
	}
	
	public static Response kycUser(String id)
	{
		System.out.println(id);
		HashMap kyc=new HashMap();
		kyc.put("status", "COMPLETED");
		kyc.put("userId", id);
		kyc.put("id", 97);
		
		Response response= given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(kyc)
			.when()
				.post(Routes.kyc_update);
		
		return response;
	}
	
	public static Response profileUpdateUser(String token, HashMap profile)
	{
//		HashMap<String,String> profile=new HashMap<String,String>();
//		profile.put("first_name", "Test");
//		profile.put("last_name", "Final");
		File profileImage=new File("C:/Users/fahim.abbas/Downloads/All JPG files/JPEG_ (12).jpg");
		
		System.out.println(profileImage);
		
		Response response= given()
//				.header("Content-Type","multipart/form-data")
				.multiPart("profileImage", profileImage)
//				.relaxedHTTPSValidation()
//				.formParams(profile)
//				.accept(ContentType.JSON)
				.header("Content-Type","multipart/form-data")
				.header("Authorization", "Bearer " +token)
				.accept(ContentType.JSON)
				.relaxedHTTPSValidation()
                .formParams(profile)
                     
              .when()
				.post(Routes.profile_update);
		String req=given().log().body().toString();
		System.out.println(req);
		return response;
	}
	
	public static Response moveFile(String file_id,String token, String folder_id)
	{
		ArrayList<String> fields=new ArrayList<String>();
		fields.add(file_id);
//		ArrayList<String> folderIds=new ArrayList<String>();
//		folderIds.add(folder_id);
//		System.out.println(folder_id);
		System.out.println(file_id);
				
		Map<String, Object> fileid = new HashMap<String, Object>();
        fileid.put("fileIds", fields);
        HashMap folderid=new HashMap();
		folderid.put("folderId", folder_id);
		folderid.put("fileIds", fields);
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.println(folder_id.getClass().getSimpleName());
		System.out.println(fields.getClass().getSimpleName());
		System.out.println(fileid.getClass().getSimpleName());
		System.out.println("---------------------------------------------------------------------------------------------");
		Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(folderid)
				.header("Authorization", "Bearer " +token)
			.when()
				.post(Routes.move_file);
		
		return response;
	}
	
	public static Response profileViewUser(String token)
	{
		Response response= given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
			.when()
				.get(Routes.profile_view);
		
		return response;
		
	}
	
	public static List categories(String token)
	{
		Response response= given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
			.when()
				.get(Routes.category_id);
		List<Long> idList = response.jsonPath().getList("result.id");
		return idList;
	}
	public static Response createfile(String token, HashMap map)
	{
		
		//int a=base.randomnumber();
		//System.out.println(a);
		//String str=String.valueOf(a);
		
		File thumbnail=new File(System.getProperty("user.dir")+"//Thumbnail//JPEG_ ("+rand.nextInt(11,20)+").jpg");
		File file=new File(System.getProperty("user.dir")+"//File//JPEG_ ("+rand.nextInt(1,10)+").jpg");
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//		System.out.println(str);
//		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//		HashMap filecreate=new HashMap();
//		filecreate.put("name", "File"+RandomStringUtils.random(8, "0123456789abcdefghijkl"));
//		filecreate.put("category_id", str);
//		filecreate.put("file_source", "local");
//		filecreate.put("status", "in_progress");
//		filecreate.put("description", "This is test file");
		
		Response response= given()
				.multiPart("thumbnail", thumbnail)
				.multiPart("local_file", file)
				.header("Content-Type","multipart/form-data")
				.formParams(map)
				.relaxedHTTPSValidation()
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
			.when()
				.post(Routes.create_file);
		
		return response;
	}
	
	public static Response createFileVersion1(String token, HashMap map)
	{
		File thumbnail=new File(System.getProperty("user.dir")+"//Thumbnail//JPEG_ ("+rand.nextInt(11,20)+").jpg");
		File file=new File(System.getProperty("user.dir")+"//File//JPEG_ ("+rand.nextInt(1,10)+").jpg");
		
		Response response= given()
				.multiPart("thumbnail", thumbnail)
				.multiPart("local_file", file)
				.header("Content-Type","multipart/form-data")
				.formParams(map)
				.relaxedHTTPSValidation()
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
			.when()
				.post(Routes.create_file);
		
		return response;
	}
	
	public static Response getfileCertificate(String token)
	{
		//HashMap getfilecertificate=new HashMap();
		//getfilecertificate.put("categoryId", str);
		
		Response response=given()
				//.header("Content-Type","multipart/form-data")
				//.formParams(getfilecertificate)
				//.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
				//.body(getfilecertificate)
			.when()
				.get(Routes.get_all_certificate);
		
		return response;
	}
	
	public static Response getfileVersion(String file_id, String token)
	{
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(file_id);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		Response response=given()
				.header("Authorization", "Bearer " +token)
			.when()
				.get(Routes.get_file_version+file_id+"/versions");
		
		return response;
	}
	
	public static Response downloadFile(String token, String version_id)
	{
		Response response=given()
				.header("Authorization", "Bearer " +token)
				//.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				//.param("version", version_id)
			.when()
				.post(Routes.download_certificate+version_id+"/download");
		
		System.out.println(Routes.download_certificate+version_id+"/download");
		
		return response;
	}
	
	public static Response moveFileToRecycleBin(String file_id, String token)
	{
		//Map<String,Object> recycle=new HashMap<String,Object>();
		//recycle.put("fileIds", Certificate_id);
		
//		List<Map<String,Object>> recyclebin=new ArrayList<>();
//		recyclebin.add(recycle);
//		JSONArray rcarray = new JSONArray();
//		rcarray.put(Certificate_id);
		
		ArrayList<String> fileIds = new ArrayList<String>();
        fileIds.add(file_id);
//		
		Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("fileIds", fileIds);
        //hashMap.put("2", "two");
        // array to store keys of the `HashMap`
//        String[] key = new String[hashMap.size()];
//         //array to store values of the `HashMap`
//	        String[] value = new String[hashMap.size()];
//	        int i = 0;
//	        for (Map.Entry<String, String> entry: hashMap.entrySet())
//	        {
//	            key[i] = entry.getKey();
//	            value[i++] = entry.getValue();
//	        }
//	        for (i = 0; i < hashMap.size(); i++) {
//	            System.out.println( "{" + key[i] + ":" + value[i] + "}" );
//	        }
        System.out.println("******************************************************");
        System.out.println(hashMap);
        System.out.println("******************************************************");
		//Map<String, Object> map = new LinkedHashMap<>();
		//map.put("fileIds", Certificate_id);
		    
		Response response=given()
				.header("Authorization", "Bearer " +token)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(hashMap)
				//.param("fileIds", Certificate_id)
				
			.when()
				.post(Routes.Recyble_bin_certificate_folder);
		
		//RequestSpecification recycle= given().param("fileIds", Certificate_id).log().all();
		
		return response;
	}
	
	public static Response deleteFileVersion(String token, String version_id1)
	{
		Response response=given()
				.header("Authorization", "Bearer " +token)
			.when()
				.post(Routes.delete_file_version+version_id1);
		
		return response;
	}
	
	public static Response restoreFileVersion(String token, String version_id1)
	{
		Response response=given()
				.header("Authorization", "Bearer " +token)
			.when()
				.post(Routes.restore_file_version+version_id1);
		
		return response;
	}
	
	public static Response restoreFileFromRecycleBin(String token, String folder_id, String file_id)
	{
		ArrayList<String> restorefile = new ArrayList<String>();
        restorefile.add(file_id);
        
        
       ArrayList<String> restorefolder = new ArrayList<String>();
       restorefolder.add(folder_id);
        
        Map<String, Object> hashMap1 = new HashMap<String, Object>();
        hashMap1.put("fileIds", restorefile);
        hashMap1.put("folderIds", restorefolder);
        
        Response response=given()
				.header("Authorization", "Bearer " +token)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(hashMap1)
				//.param("fileIds", Certificate_id)
				
			.when()
				.post(Routes.restore_file_from_recyclebin);
        
        return response;
        
	}
	
	public static Response deleteFilePermanently(String token, String folder_id, String file_id)
	{
		ArrayList<String> permanentfolder=new ArrayList<String>();
		permanentfolder.add(folder_id);
		
		ArrayList<String> permanentfile=new ArrayList<String>();
		permanentfile.add(file_id);
		
		Map<String, Object> hashDel=new HashMap<String, Object>();
		hashDel.put("folderIds", permanentfolder);
		hashDel.put("fileIds", permanentfile);
		
		Response response=given()
				.header("Authorization", "Bearer " +token)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(hashDel)
			.when()
				.delete(Routes.delete_file_folder_permanently);
		
		return response;
	}
	
	public static Response createFolder(String token)
	{
		HashMap create=new HashMap();
		create.put("name", "Folder_test_"+rand.nextInt(50));
		Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
				.body(create)
			.when()
				.post(Routes.create_folder);
		
		System.out.println(create);
		
		return response;
	}
	
	public static Response renameFolder(String token, String folder_id)
	{
		HashMap re=new HashMap();
		re.put("name", "Folder_test_update_"+rand.nextInt(50));
		Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " +token)
				.body(re)
			.when()
				.post(Routes.rename_folder+folder_id+"/rename");
		
		return response;
	}
	
	public static Response deleteFolder(String token, String folder_id)
	{
		System.out.println(folder_id);
		ArrayList<String> folderid = new ArrayList<String>();
		folderid.add(folder_id);
//		
		Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("folderIds", folderid);
        System.out.println(folderid);
		Response response=given()
				.header("Authorization", "Bearer " +token)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(hashMap)
			.when()
				.post(Routes.Recyble_bin_certificate_folder);
		
		return response;
	}
	
	public static Response getAllFolder(String token)
	{
		Response response=given()
				.header("Authorization", "Bearer " +token)
				
			.when()
				.get(Routes.get_all_folder);
		
		return response;
	}
	
	public static Response logoutUser(String token, String refresh)
	{
		HashMap logout=new HashMap();
		logout.put("refresh_token", refresh);
		Response response=given()
				.header("Authorization", "Bearer " +token)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(logout)
			.when()
				.post(Routes.logout_url);
		
		return response;
	}

}
