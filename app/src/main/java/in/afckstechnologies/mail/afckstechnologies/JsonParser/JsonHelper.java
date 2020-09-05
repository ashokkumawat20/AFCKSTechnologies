package in.afckstechnologies.mail.afckstechnologies.JsonParser;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.afckstechnologies.mail.afckstechnologies.Models.BankDeatilsDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.CenterDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.CoursesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.FileNamesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewCoursesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.UserClassesDAO;


public class JsonHelper {
    int temp;
    private ArrayList<CenterDAO> centerDAOArrayList = new ArrayList<CenterDAO>();
    private CenterDAO centerDAO;
    private ArrayList<NewCoursesDAO> newCoursesDAOArrayList = new ArrayList<NewCoursesDAO>();
    private NewCoursesDAO newCoursesDAO;

    private ArrayList<CoursesDAO> coursesDAOArrayList = new ArrayList<CoursesDAO>();
    private CoursesDAO coursesDAO;

    private ArrayList<NewBatchesDAO> newBatchesDAOArrayList = new ArrayList<NewBatchesDAO>();
    private NewBatchesDAO newBatchesDAO;


    private ArrayList<BankDeatilsDAO> bankDeatilsDAOArrayList = new ArrayList<BankDeatilsDAO>();
    private BankDeatilsDAO bankDeatilsDAO;

    private ArrayList<UserClassesDAO> userClassesDAOArrayList = new ArrayList<UserClassesDAO>();
    private UserClassesDAO userClassesDAO;

    private ArrayList<FileNamesDAO> fileNamesDAOArrayList = new ArrayList<FileNamesDAO>();
    private FileNamesDAO fileNamesDAO;


    //bankdetailsPaser
    public ArrayList<BankDeatilsDAO> parseBankList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                bankDeatilsDAO = new BankDeatilsDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                bankDeatilsDAO.setId(json_data.getString("id"));
                bankDeatilsDAO.setBranch_name(json_data.getString("branch_name"));
                bankDeatilsDAO.setBank_name(json_data.getString("bank_name"));
                bankDeatilsDAO.setAcc_holder_name(json_data.getString("acc_holder_name"));
                bankDeatilsDAO.setAccount_no(json_data.getString("account_no"));
                bankDeatilsDAO.setAccount_type(json_data.getString("account_type"));
                bankDeatilsDAO.setIfsc_code(json_data.getString("ifsc_code"));
                bankDeatilsDAOArrayList.add(bankDeatilsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bankDeatilsDAOArrayList;
    }

    //centerPaser
    public ArrayList<CenterDAO> parseCenterList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                centerDAO = new CenterDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                centerDAO.setId(json_data.getString("id"));
                centerDAO.setBranch_name(json_data.getString("branch_name"));
                centerDAO.setAddress(json_data.getString("address"));
                centerDAO.setIsselected(json_data.getString("isselected"));
                centerDAO.setStart_latitude(json_data.getString("latitude"));
                centerDAO.setStart_longitude(json_data.getString("longitude"));
                centerDAOArrayList.add(centerDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return centerDAOArrayList;
    }

    //newcoursesPaser
    public ArrayList<NewCoursesDAO> parseNewCoursesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                newCoursesDAO = new NewCoursesDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                newCoursesDAO.setId(json_data.getString("id"));
                newCoursesDAO.setCourse_type_id(json_data.getString("course_type_id"));
                newCoursesDAO.setCourse_code(json_data.getString("course_code"));
                newCoursesDAO.setCourse_name(json_data.getString("course_name"));
                newCoursesDAO.setTime_duration(json_data.getString("time_duration"));
                newCoursesDAO.setPrerequisite(json_data.getString("prerequisite"));
                newCoursesDAO.setRecommonded(json_data.getString("recommonded"));
                newCoursesDAO.setFees(json_data.getString("fees"));
                newCoursesDAO.setSyllabuspath(json_data.getString("syllabuspath"));
                newCoursesDAO.setYou_tube_link(json_data.getString("you_tube_link"));
                newCoursesDAO.setIsselected(json_data.getString("isselected"));
                newCoursesDAOArrayList.add(newCoursesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newCoursesDAOArrayList;
    }

    //coursesPaser
    public ArrayList<CoursesDAO> parseCoursesList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                coursesDAO = new CoursesDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                coursesDAO.setId(json_data.getString("id"));
                coursesDAO.setCourse_type_id(json_data.getString("course_type_id"));
                coursesDAO.setCourse_code(json_data.getString("course_code"));
                coursesDAO.setCourse_name(json_data.getString("course_name"));
                coursesDAO.setTime_duration(json_data.getString("time_duration"));
                coursesDAO.setPrerequisite(json_data.getString("prerequisite"));
                coursesDAO.setRecommonded(json_data.getString("recommonded"));
                coursesDAO.setSyllabuspath(json_data.getString("syllabuspath"));
                coursesDAO.setYou_tube_link(json_data.getString("you_tube_link"));
                coursesDAO.setFees(json_data.getString("fees"));

                coursesDAOArrayList.add(coursesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return coursesDAOArrayList;
    }

    //batches
    public ArrayList<NewBatchesDAO> newBatchesDAOArrayList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                newBatchesDAO = new NewBatchesDAO();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                newBatchesDAO.setId(json_data.getString("id"));
                newBatchesDAO.setBatch_code(json_data.getString("batch_code"));
                newBatchesDAO.setStart_date(json_data.getString("start_date"));
                newBatchesDAO.setStart_time(json_data.getString("timings"));
                newBatchesDAO.setCourse_id(json_data.getString("course_id"));
                newBatchesDAO.setFirst_name(json_data.getString("first_name"));
                newBatchesDAO.setLast_name(json_data.getString("last_name"));
                newBatchesDAO.setCourse_code(json_data.getString("course_code"));
                newBatchesDAO.setCourse_name(json_data.getString("course_name"));
                newBatchesDAO.setBatchtype(json_data.getString("batchtype"));
                newBatchesDAO.setBranch_name(json_data.getString("branch_name"));
                newBatchesDAO.setIsbooked(json_data.getString("isbooked"));
                newBatchesDAO.setNotes(json_data.getString("notes"));
                newBatchesDAO.setFrequency(json_data.getString("frequency"));
                newBatchesDAO.setFees(json_data.getString("fees"));
                newBatchesDAO.setDuration(json_data.getString("duration"));
                newBatchesDAO.setIsstatus(json_data.getString("isstatus"));
                newBatchesDAO.setWa_invite_link(json_data.getString("wa_invite_link"));
                newBatchesDAOArrayList.add(newBatchesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newBatchesDAOArrayList;
    }

    //batches
    public ArrayList<UserClassesDAO> userClassesDAOArrayList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);
            temp=leadJsonObj.length();
            for (int i = 0; i < leadJsonObj.length(); i++) {
                userClassesDAO = new UserClassesDAO();
                String sequence = String.format("%02d", temp-i);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                userClassesDAO.setBranch_name(json_data.getString("branch_name"));
                userClassesDAO.setBatch_id(json_data.getString("batch_id"));
                userClassesDAO.setNext_class_date(json_data.getString("next_class_date"));
                userClassesDAO.setNext_class_topics(json_data.getString("next_class_topics"));
                userClassesDAO.setTodays_topics(json_data.getString("todays_topics"));
                userClassesDAO.setBatch_date(json_data.getString("batch_date"));
                userClassesDAO.setTimings(json_data.getString("timings"));
                userClassesDAO.setFrequency(json_data.getString("frequency"));
                userClassesDAO.setTrainer_name(json_data.getString("trainer_name"));
                userClassesDAO.setLecture_count(json_data.getString("lecture_count"));
                userClassesDAO.setLatitude(json_data.getString("latitude"));
                userClassesDAO.setLongitude(json_data.getString("longitude"));
                userClassesDAO.setPath(json_data.getString("path"));
                userClassesDAO.setFile_names(json_data.getString("file_names").replace(", ","\n\n").trim());
                userClassesDAO.setNumbers(sequence);
                userClassesDAOArrayList.add(userClassesDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userClassesDAOArrayList;
    }

    public ArrayList<FileNamesDAO> parseImagePathList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {
            JSONObject jsonObject = new JSONObject(ListResponse);

            if (!jsonObject.isNull("filesname")) {
                JSONArray jsonArray = jsonObject.getJSONArray("filesname");
                for (int i = 0; i < jsonArray.length(); i++) {
                    fileNamesDAO = new FileNamesDAO();
                    fileNamesDAO.setName(jsonArray.getString(i));
                    fileNamesDAOArrayList.add(fileNamesDAO);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileNamesDAOArrayList;
    }
}
