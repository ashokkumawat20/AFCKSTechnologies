package in.afckstechnologies.mail.afckstechnologies.Activity;


import android.os.Bundle;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Adapter.NewCoursesListAdpter;
import in.afckstechnologies.mail.afckstechnologies.JsonParser.JsonHelper;
import in.afckstechnologies.mail.afckstechnologies.Models.NewCoursesDAO;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;
import in.afckstechnologies.mail.afckstechnologies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Enter_Level_Courses extends Activity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String center_id = "";

    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String courseListResponse = "";
    ProgressDialog mProgressDialog;


    private RecyclerView mleadList;
    //
    List<NewCoursesDAO> data;
    NewCoursesListAdpter coursesListAdpter;
    ArrayList<String> centerIdArrayList;
    Button btnSubmit;
    Boolean status;
    String msg;

    boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__enter__level__courses);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mleadList = (RecyclerView) findViewById(R.id.coursesList);

        new getCoursesList().execute();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = "";
                List<NewCoursesDAO> stList = ((NewCoursesListAdpter) coursesListAdpter).getSservicelist();

                for (int i = 0; i < stList.size(); i++) {
                    NewCoursesDAO singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {
                        if (data.equals("")) {
                            data = singleStudent.getId().toString();
                        } else {
                            data = data + "," + singleStudent.getId().toString();
                            Config.DATA_ENTERLEVEL_COURSES = data;
                        }


                    }
                }


                if (!data.equals("")) {


                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                        // dismiss();
                        if (click) {
                            prefEditor.putString("course_id", data);
                            prefEditor.commit();
                            Toast.makeText(Activity_Enter_Level_Courses.this, "Selected Courses: \n" + Config.DATA_ENTERLEVEL_COURSES, Toast.LENGTH_SHORT).show();
                            //  new submitData().execute();
                            // click = false;
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                } else if (preferences.getString("updatecourse", "").equals("newcourse")) {
                    finish();
                    Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Course_Details_Form.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Activity_Enter_Level_Courses.this, "Please select Courses", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public String getData() {
        String data = "";
        List<NewCoursesDAO> stList = ((NewCoursesListAdpter) coursesListAdpter).getSservicelist();
        System.out.print("stList-->" + stList.size());

        for (int i = 0; i < stList.size(); i++) {
            NewCoursesDAO singleStudent = stList.get(i);
            if (singleStudent.isSelected() == true) {
                if (data.equals("")) {
                    data = singleStudent.getId().toString();
                } else {
                    data = data + "," + singleStudent.getId().toString();
                    Config.DATA_ENTERLEVEL_COURSES = data;
                }


            }
        }

        return data;
    }

    //
    private class getCoursesList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_Enter_Level_Courses.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("user_id", "" + preferences.getInt("user_id", 0));
                        put("course_type_id", "1");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_COURSE_DETAILS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);
            // leadListDAO = new RawLeadListDAO();
            data = new ArrayList<>();
            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseNewCoursesList(courseListResponse);
                                jsonArray = new JSONArray(courseListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (courseListResponse.compareTo("") != 0) {

                coursesListAdpter = new NewCoursesListAdpter(Activity_Enter_Level_Courses.this, data);
                mleadList.setAdapter(coursesListAdpter);
                mleadList.setLayoutManager(new LinearLayoutManager(Activity_Enter_Level_Courses.this));
                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {

                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }
    //

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_Enter_Level_Courses.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("course_id", preferences.getString("course_id", ""));
                        put("user_id", "" + preferences.getInt("user_id", 0));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_SEND_DETAILS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);


            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                JSONObject jsonObject = new JSONObject(courseListResponse);
                                status = jsonObject.getBoolean("status");
                                msg = jsonObject.getString("message");
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (status) {
                prefEditor.putString("course_flag", "1");
                prefEditor.remove("course_id").commit();
                prefEditor.commit();
                click = true;

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Enter_Level_Courses.this);
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want add more courses")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new getCoursesList().execute();
                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                if (preferences.getString("updatecourse", "").equals("newcourse")) {
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Course_Details_Form.class);
                                    finish();
                                    startActivity(intent);
                                    // Close the progressdialog
                                    mProgressDialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Student_DashBoard.class);
                                    finish();
                                    startActivity(intent);
                                    // Close the progressdialog
                                    mProgressDialog.dismiss();
                                }
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Course Details");
                alert.show();







                /*if (preferences.getString("updatecourse", "").equals("newcourse")) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Course_Details_Form.class);
                    finish();
                    startActivity(intent);
                    // Close the progressdialog
                    mProgressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Student_DashBoard.class);
                    finish();
                    startActivity(intent);
                    // Close the progressdialog
                    mProgressDialog.dismiss();
                }*/

            } else {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
            }

            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(Activity_Enter_Level_Courses.this, Activity_Student_DashBoard.class);
        this.finish();
        startActivity(intent);
    }
}
