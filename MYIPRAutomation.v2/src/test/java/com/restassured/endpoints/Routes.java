package com.restassured.endpoints;

public class Routes 
{
	public static String base_url="https://qa-myiprapi.p2eppl.com";  //https://qa-myiprapi.p2eppl.com
	public static String user_url=base_url+"/user";
	public static String verify_email=base_url+"/user/force-verify-email";
	public static String login_url=base_url+"/auth/login";
	public static String kyc_update=base_url+"/webhook/sumsubUserKycUpdate";
	public static String profile_update=base_url+"/user/update-profile";
	public static String profile_view=base_url+"/user/profile";
	public static String create_file=base_url+"/file/create";
	public static String get_all_certificate=base_url+"/file/v2";         //"/v1/get-certificate";
	public static String get_file_version=base_url+"/file/"; //734c7069-6dd5-42fe-8af3-260284c40ef0/certificate";
	public static String Recyble_bin_certificate_folder=base_url+"/file/recycle-bin/trash/files"; //734c7069-6dd5-42fe-8af3-260284c40ef0/burn";
	public static String create_folder=base_url+"/file/create/folder";
	public static String rename_folder=base_url+"/file/folder/"; //09d20e6a-bd48-4462-8eb6-5da1390b1de1/rename";
	//public static String delete_folder=base_url+"/file/folder/"; //4ea38143-0874-4ccc-b2df-412daa19697c";
	public static String get_all_folder=base_url+"/file/folders";
	public static String logout_url=base_url+"/auth/logout";
	public static String verify_auth="SECRET M0VqH8SLzu2ktheKeDQxrpxXDmsXQjb8E1eD6hPHAHrVyctlZDN0aYbgfBO7qpj/";
	public static String category_id=base_url+"/file/categories";
	public static String download_certificate=base_url+"/file/version/";  //version_id/download;
	public static String move_file=base_url+"/file/move-many";
	public static String restore_file_from_recyclebin=base_url+"/file/recycle-bin/restore/files";
	public static String delete_file_version=base_url+"/file/recycle-bin/trash/version/";
	public static String restore_file_version=base_url+"/file/recycle-bin/restore/version/";
	public static String delete_file_folder_permanently=base_url+"/file/recycle-bin/delete/file";
}
