package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.afckstechnologies.mail.afckstechnologies.Models.BatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.CourseDetailsDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;

public class DemoClassActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String courseResponse = "", course_id = "", onComingBatchResponse = "", batch_code = "", course_name = "";
    ArrayList<CourseDetailsDAO> courseDetailsDAOArrayList;
    ArrayList<BatchesDAO> batcheslist;
    Spinner displayCourseName, spnrUserType;
    LinearLayout layTrainerName;
    ProgressDialog mProgressDialog;
    String addStudentRespone = "", studentFeedBackCode = "", know_from = "", type = "";
    boolean status;

    String msg = "";
    static String sms_user = "";
    static String sms_pass = "";
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_class);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        sms_user = preferences.getString("sms_username", "");
        sms_pass = preferences.getString("sms_password", "");
        courseDetailsDAOArrayList = new ArrayList<>();
        batcheslist = new ArrayList<>();
        displayCourseName = (Spinner) findViewById(R.id.displayCourseName);
        layTrainerName = (LinearLayout) findViewById(R.id.layTrainerName);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = spnrUserType.getSelectedItem().toString();
                if (validate(type, course_name, batch_code)) {
                    new submitData().execute();
                }
            }
        });
        String ks[] = preferences.getString("know_source", "").split(";");
        int s = ks.length;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(DemoClassActivity.this, android.R.layout.simple_spinner_item);
        dataAdapter.add("How did you come to know about Class?");
        for (int i = 0; i < ks.length; i++) {
            dataAdapter.add(ks[i]);
        }
        /*dataAdapter.add("How did you come to know about Class?");
        dataAdapter.add("Friend");
        dataAdapter.add("Google search");
        dataAdapter.add("Facebook");
        dataAdapter.add("Instagram");
        dataAdapter.add("Whatsapp status");*/
        dataAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);

        spnrUserType = (Spinner) findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);

        new initCourseSpinner().execute();
    }

    public boolean validate(String type, String course, String batch_code) {
        boolean isValidate = false;
        if (type.equals("How did you come to know about Class?")) {
            Toast.makeText(DemoClassActivity.this, "Please select How did you come to know about Class?", Toast.LENGTH_LONG).show();
        } else if (course.equals("")) {
            Toast.makeText(DemoClassActivity.this, "Please select course", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (batch_code.equals("")) {
            Toast.makeText(DemoClassActivity.this, "Please select batch", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    private class initCourseSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //   mProgressDialog = new ProgressDialog(FixedAssetsActivity.this);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            courseResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCOURSES, jsonLeadObj);
            Log.i("resp", courseResponse);

            if (courseResponse.compareTo("") != 0) {
                if (isJSONValid(courseResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                courseDetailsDAOArrayList.add(new CourseDetailsDAO("0", "Select Course", "", ""));
                                JSONArray LeadSourceJsonObj = new JSONArray(courseResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    courseDetailsDAOArrayList.add(new CourseDetailsDAO(json_data.getString("id"), json_data.getString("course_name"), json_data.getString("course_type_id"), json_data.getString("course_code")));

                                }

                                jsonArray = new JSONArray(courseResponse);

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
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
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
            if (courseResponse.compareTo("") != 0) {

                // Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<CourseDetailsDAO> adapter = new ArrayAdapter<CourseDetailsDAO>(DemoClassActivity.this, R.layout.multiline_spinner_dropdown_item, courseDetailsDAOArrayList);
                // MyAdapter adapter = new MyAdapter(StudentsListActivity.this,R.layout.spinner_item,locationlist);
                displayCourseName.setAdapter(adapter);
                displayCourseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        CourseDetailsDAO LeadSource = (CourseDetailsDAO) parent.getSelectedItem();
                        //   Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getStatus(), Toast.LENGTH_SHORT).show();
                        course_id = LeadSource.getId();
                        batcheslist.clear();
                        if (!course_id.equals("0")) {
                            course_name = LeadSource.getCourse_name();
                            layTrainerName.setVisibility(View.VISIBLE);
                            new getOngoingBatchesList().execute();
                        } else {
                            layTrainerName.setVisibility(View.GONE);
                            batch_code = "";
                            course_name = "";
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                //  mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();
            }
        }
    }

    private class getOngoingBatchesList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("course_id", course_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            onComingBatchResponse = serviceAccess.SendHttpPost(Config.URL_GETALLCOURSESDEMO, jsonLeadObj);
            Log.i("resp", onComingBatchResponse);
            if (onComingBatchResponse.compareTo("") != 0) {
                if (isJSONValid(onComingBatchResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                batcheslist.add(new BatchesDAO("0", "", "", "Select Batch", "", "", "", "", "", "", "", "", ""));
                                JSONArray LeadSourceJsonObj = new JSONArray(onComingBatchResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    batcheslist.add(new BatchesDAO(json_data.getString("id"), json_data.getString("course_id"), json_data.getString("branch_id"), json_data.getString("batch_details"), json_data.getString("start_date"), json_data.getString("timings"), json_data.getString("Notes"), json_data.getString("frequency"), json_data.getString("fees"), json_data.getString("duration"), json_data.getString("course_name"), json_data.getString("branch_name"), json_data.getString("batchtype")));
                                }

                                jsonArray = new JSONArray(onComingBatchResponse);

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
            Spinner spinnerCustom = (Spinner) findViewById(R.id.displayTrainerName);
            ArrayAdapter<BatchesDAO> adapter = new ArrayAdapter<BatchesDAO>(DemoClassActivity.this, R.layout.multiline_spinner_dropdown_item, batcheslist);
            //  CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(StudentsListActivity.this,locationlist);
            spinnerCustom.setAdapter(adapter);
            spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                    BatchesDAO LeadSource = (BatchesDAO) parent.getSelectedItem();
                    if (!LeadSource.getId().equals("0")) {
                        batch_code = LeadSource.getId();
                    } else {
                        batch_code = "";
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }


            });

        }
    }

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DemoClassActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Registering...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("BatchID", batch_code);
                        put("UserID", preferences.getInt("user_id", 0));
                        put("know_from", type);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addStudentRespone = serviceAccess.SendHttpPost(Config.URL_ADDSTUDENTINBATCHDEMO, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addStudentRespone);


            if (addStudentRespone.compareTo("") != 0) {
                if (isJSONValid(addStudentRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addStudentRespone);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                        if (status) {

                            String msg = "Thank you for your interest in " + course_name + " Demo Batch." + System.getProperty("line.separator") + System.getProperty("line.separator") +
                                    " We would be updating you details one day before batch Start.";
                            String result = sendSms1(jsonObject.getString("mobile_no"), msg);
                            Log.d("sent sms---->", result);

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


                    Toast.makeText(DemoClassActivity.this, "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(DemoClassActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Close the progressdialog
            mProgressDialog.dismiss();
            if (status) {
                Toast.makeText(DemoClassActivity.this, msg, Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(DemoClassActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String sendSms1(String tempMobileNumber, String message) {
        String sResult = null;
        try {
// Construct data
            //String phonenumbers = "9657816221";
            String data = "user=" + URLEncoder.encode(sms_user, "UTF-8");
            data += "&password=" + URLEncoder.encode(sms_pass, "UTF-8");
            data += "&message=" + URLEncoder.encode(message, "UTF-8");
            data += "&sender=" + URLEncoder.encode("AFCKST", "UTF-8");
            data += "&mobile=" + URLEncoder.encode(tempMobileNumber, "UTF-8");
            data += "&type=" + URLEncoder.encode("3", "UTF-8");
// Send data
            URL url = new URL("http://login.bulksmsgateway.in/sendmessage.php?" + data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
// Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult1 = "";
            while ((line = rd.readLine()) != null) {
// Process line...
                sResult1 = sResult1 + line + " ";
            }
            wr.close();
            rd.close();
            return sResult1;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
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
}
