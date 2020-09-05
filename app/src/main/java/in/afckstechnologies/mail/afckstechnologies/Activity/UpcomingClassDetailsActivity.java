package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstechnologies.Adapter.BatchesListAdpter;
import in.afckstechnologies.mail.afckstechnologies.Adapter.UserClassesListAdpter;
import in.afckstechnologies.mail.afckstechnologies.JsonParser.JsonHelper;
import in.afckstechnologies.mail.afckstechnologies.Models.LocationDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.UserBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.UserClassesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;

public class UpcomingClassDetailsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String center_id = "";
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String batchesListResponse = "";
    String centerResponse = "";
    ProgressDialog mProgressDialog;
    ArrayList<UserBatchesDAO> userBatchesDAOArrayList;
    private RecyclerView mbatchesList;
    //
    List<UserClassesDAO> data;
    UserClassesListAdpter userClassesListAdpter;
    ArrayList<String> centerIdArrayList;
    public EditText search;
    String batch_code = "";
    ImageView serach_hide, clear;
    String course_name = "";
    TextView courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_class_details);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        mbatchesList = (RecyclerView) findViewById(R.id.batchesList);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        courseName = (TextView) findViewById(R.id.courseName);
        data = new ArrayList<>();

        new initBatchSpinner().execute();
        addTextListener();
        serach_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                clear.setVisibility(View.VISIBLE);
                serach_hide.setVisibility(View.GONE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                serach_hide.setVisibility(View.VISIBLE);
                clear.setVisibility(View.GONE);

            }
        });
    }

    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<UserClassesDAO> filteredList = new ArrayList<UserClassesDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String course_name = data.get(i).getTrainer_name().toLowerCase();
                            String batchtype = data.get(i).getBranch_name().toLowerCase();
                            String start_date = data.get(i).getFrequency().toLowerCase();
                            String branch_name = data.get(i).getBatch_id().toLowerCase();
                            String files_name = data.get(i).getFile_names().toLowerCase();
                            if (course_name.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (batchtype.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (start_date.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (branch_name.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                            else if (files_name.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                        }
                    }
                }

                mbatchesList.setLayoutManager(new LinearLayoutManager(UpcomingClassDetailsActivity.this));
                userClassesListAdpter = new UserClassesListAdpter(UpcomingClassDetailsActivity.this, filteredList);
                mbatchesList.setAdapter(userClassesListAdpter);
                userClassesListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    //
    private class initBatchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(UpcomingClassDetailsActivity.this);
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

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_GETALLUSERBTACH, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerResponse);

            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                userBatchesDAOArrayList = new ArrayList<>();
                                userBatchesDAOArrayList.add(new UserBatchesDAO(""+0,"All"));
                                JSONArray LeadSourceJsonObj = new JSONArray(centerResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    userBatchesDAOArrayList.add(new UserBatchesDAO("" + (i+1), json_data.getString("course_name")));
                                }

                                jsonArray = new JSONArray(centerResponse);

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
            if (centerResponse.compareTo("") != 0) {
                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBtach);
                ArrayAdapter<UserBatchesDAO> adapter = new ArrayAdapter<UserBatchesDAO>(UpcomingClassDetailsActivity.this, R.layout.multiline_spinner_dropdown_item, userBatchesDAOArrayList);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        UserBatchesDAO LeadSource = (UserBatchesDAO) parent.getSelectedItem();
                        // Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBatch_code(), Toast.LENGTH_SHORT).show();
                        batch_code = LeadSource.getCourse_name();
                        if (!batch_code.equals("")) {
                            new getClassList().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                Toast.makeText(getApplicationContext(), "Don't have any class details for you!", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        }
    }

    //
    private class getClassList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(UpcomingClassDetailsActivity.this);
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

                        put("batch_code", batch_code);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            batchesListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLUSERCLASSDETAILS, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + batchesListResponse);
            if (batchesListResponse.compareTo("") != 0) {
                if (isJSONValid(batchesListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.userClassesDAOArrayList(batchesListResponse);
                                jsonArray = new JSONArray(batchesListResponse);

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
            if (data.size() > 0) {
                userClassesListAdpter = new UserClassesListAdpter(UpcomingClassDetailsActivity.this, data);
                mbatchesList.setAdapter(userClassesListAdpter);
                mbatchesList.setLayoutManager(new LinearLayoutManager(UpcomingClassDetailsActivity.this));
                userClassesListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
                search.setText(course_name);
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    //
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
