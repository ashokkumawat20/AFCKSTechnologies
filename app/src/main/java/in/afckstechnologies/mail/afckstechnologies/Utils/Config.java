package in.afckstechnologies.mail.afckstechnologies.Utils;

import java.util.ArrayList;

/**
 * Created by admin on 12/9/2016.
 */

public class Config {

    // public static final String BASE_URL = "http://192.168.1.64:8088/afcks/api/";
    // public static final String BASE_URL = "http://testprojects.in/afcks/api/";
    public static final String BASE_URL = "http://afckstechnologies.in/afcks/api/";
    public static final String URL_STUDENT_REGISTRATION = BASE_URL + "user/adduserByAdmin";
    public static final String URL_CENTER_DETAILS = BASE_URL + "user/branches";
    public static final String URL_COURSE_DETAILS = BASE_URL + "user/cources";
    public static final String URL_SEND_DETAILS = BASE_URL + "user/savecourse";
    public static final String URL_SEND_LOCATION_DETAILS = BASE_URL + "user/savebranch";
    public static final String URL_BANK_DETAILS = BASE_URL + "user/getbankdetails";
    public static final String URL_GET_USER_DETAILS = BASE_URL + "user/getuserdetails";
    public static final String URL_UPDATE_USER_DETAILS = BASE_URL + "user/updateuserdetails";
    public static final String URL_COURSE_DETAILS_BY_USERID = BASE_URL + "user/courcesbyuserid";
    public static final String URL_BATCHES_DETAILS = BASE_URL + "user/batches";
    public static final String URL_BOOKING_BATCH = BASE_URL + "user/bookingbatch";
    public static final String URL_DELETE_COURSE = BASE_URL + "user/deleteusercource";
    public static final String URL_DELETE_CENTER = BASE_URL + "user/deleteuserbranch";
    public static final String URL_DISPLAY_CENTER = BASE_URL + "user/getallbranches";
    public static final String URL_GETTRANS_DETAILS = BASE_URL + "user/getTransdetails";
    public static final String URL_DELETE_STBATCH_BOOKING = BASE_URL + "user/deleteStBatchBookingByuser";
    public static final String URL_GETALLUSERBTACH = BASE_URL + "user/getallUserBtach";
    public static final String URL_GETALLUSERCLASSDETAILS = BASE_URL + "user/getallUserClassDetails";
    public static final String URL_GETZIPFILENAMES = BASE_URL + "user/getZipFileNames";
    public static final String URL_GETALLCOURSES = BASE_URL + "user/getallCourses";
    public static final String URL_GETALLCOURSESDEMO = BASE_URL + "user/getallCoursesDemo";
    public static final String URL_GETUSERNAMEPASSSMS = BASE_URL + "user/getUserNamePassSMS";
    public static final String URL_ADDSTUDENTINBATCHDEMO = BASE_URL + "user/addStudentInBatchDemo";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "AFCKS Images";
    public static  String DATA_ENTERLEVEL_COURSES ="";
    public static  String DATA_SPLIZATION_COURSES ="";

    public static  String DATA_MOVE_FROM_LOCATION ="";
    public static ArrayList<String> VALUE=new ArrayList<String>();
 public static final String SMS_ORIGIN = "AFCKST";
}
