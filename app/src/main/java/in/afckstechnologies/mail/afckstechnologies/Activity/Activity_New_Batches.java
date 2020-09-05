package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import in.afckstechnologies.mail.afckstechnologies.Adapter.BatchesListAdpter;
import in.afckstechnologies.mail.afckstechnologies.JsonParser.JsonHelper;
import in.afckstechnologies.mail.afckstechnologies.Models.LocationDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_New_Batches extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    String center_id = "";
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String batchesListResponse = "";
    String centerResponse = "";
    ProgressDialog mProgressDialog;
    ArrayList<LocationDAO> locationlist;
    private RecyclerView mbatchesList;
    //
    List<NewBatchesDAO> data;
    BatchesListAdpter batchesListAdpter;
    ArrayList<String> centerIdArrayList;
    public EditText search;
    String flag = "0";
    ImageView serach_hide, clear;
    String course_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new__batches);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        mbatchesList = (RecyclerView) findViewById(R.id.batchesList);
        search = (EditText) findViewById(R.id.search);
        serach_hide = (ImageView) findViewById(R.id.serach_hide);
        clear = (ImageView) findViewById(R.id.clear);
        // initBranchSpinner();
        new initBranchSpinner().execute();
        addTextListener();
        // new getBatchList().execute();
       /* Intent intent = getIntent();
        course_name = intent.getStringExtra("course_name");
        if (!course_name.equals("")) {
            clear.setVisibility(View.VISIBLE);
            serach_hide.setVisibility(View.GONE);
        }*/
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
    private void initBranchSpinner() {

        jsonLeadObj = new JSONObject() {
            {
                try {
                    // put("id", preferences.getString("u_id", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("json exception", "json exception" + e);
                }
            }
        };
        Thread objectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                WebClient serviceAccess = new WebClient();

                //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
                Log.i("json", "json" + jsonLeadObj);
                centerResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_CENTER, jsonLeadObj);
                Log.i("resp", "leadListResponse" + centerResponse);

                if (centerResponse.compareTo("") != 0) {
                    if (isJSONValid(centerResponse)) {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                try {

                                    locationlist = new ArrayList<>();
                                    locationlist.add(new LocationDAO("0", "All"));
                                    JSONArray LeadSourceJsonObj = new JSONArray(centerResponse);
                                    for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                        JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                        locationlist.add(new LocationDAO(json_data.getString("id"), json_data.getString("branch_name")));
                                    }
                                    Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                                    ArrayAdapter<LocationDAO> adapter = new ArrayAdapter<LocationDAO>(Activity_New_Batches.this, android.R.layout.simple_spinner_dropdown_item, locationlist);
                                    spinnerCustom.setAdapter(adapter);
                                    spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                                            LocationDAO LeadSource = (LocationDAO) parent.getSelectedItem();
                                            //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                                            flag = LeadSource.getId();
                                            new getBatchList().execute();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }


                                    });
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

                        return;
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }
            }
        });

        objectThread.start();


    }

    //
    private class initBranchSpinner extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_New_Batches.this);
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
                        //  put("user_id", "" + preferences.getInt("user_id", 0));
                        // put("branch_id", flag);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/lms/api/lead/showlead";
            Log.i("json", "json" + jsonLeadObj);
            centerResponse = serviceAccess.SendHttpPost(Config.URL_DISPLAY_CENTER, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerResponse);

            if (centerResponse.compareTo("") != 0) {
                if (isJSONValid(centerResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                locationlist = new ArrayList<>();
                                locationlist.add(new LocationDAO("0", "All"));
                                JSONArray LeadSourceJsonObj = new JSONArray(centerResponse);
                                for (int i = 0; i < LeadSourceJsonObj.length(); i++) {
                                    JSONObject json_data = LeadSourceJsonObj.getJSONObject(i);
                                    locationlist.add(new LocationDAO(json_data.getString("id"), json_data.getString("branch_name")));
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
                Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerBranch);
                ArrayAdapter<LocationDAO> adapter = new ArrayAdapter<LocationDAO>(Activity_New_Batches.this, android.R.layout.simple_spinner_dropdown_item, locationlist);
                spinnerCustom.setAdapter(adapter);
                spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#1c5fab"));
                        LocationDAO LeadSource = (LocationDAO) parent.getSelectedItem();
                        //  Toast.makeText(getApplicationContext(), "Source ID: " + LeadSource.getId() + ",  Source Name : " + LeadSource.getBranch_name(), Toast.LENGTH_SHORT).show();
                        flag = LeadSource.getId();
                        if (!flag.equals("0")) {
                            course_name = "";
                            serach_hide.setVisibility(View.VISIBLE);
                            clear.setVisibility(View.GONE);

                        }
                        new getBatchList().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }


                });
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }


    //
    private class getBatchList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_New_Batches.this);
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
                        put("branch_id", flag);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonLeadObj);
            batchesListResponse = serviceAccess.SendHttpPost(Config.URL_BATCHES_DETAILS, jsonLeadObj);
            Log.i("resp", "batchesListResponse" + batchesListResponse);
            if (batchesListResponse.compareTo("") != 0) {
                if (isJSONValid(batchesListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.newBatchesDAOArrayList(batchesListResponse);
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
            if (batchesListResponse.compareTo("") != 0) {
                batchesListAdpter = new BatchesListAdpter(Activity_New_Batches.this, data);
                mbatchesList.setAdapter(batchesListAdpter);
                mbatchesList.setLayoutManager(new LinearLayoutManager(Activity_New_Batches.this));
                batchesListAdpter.notifyDataSetChanged();
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

    //
    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<NewBatchesDAO> filteredList = new ArrayList<NewBatchesDAO>();
                if (data != null) {
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {

                            String course_name = data.get(i).getCourse_name().toLowerCase();
                            String batchtype = data.get(i).getBatchtype().toLowerCase();
                            String start_date = data.get(i).getStart_date().toLowerCase();
                            String branch_name = data.get(i).getBranch_name().toLowerCase();
                            String first_name = data.get(i).getFirst_name().toLowerCase();
                            String fer = data.get(i).getFrequency().toLowerCase();
                            if (course_name.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (batchtype.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (start_date.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (branch_name.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (first_name.contains(query)) {

                                filteredList.add(data.get(i));
                            } else if (fer.contains(query)) {

                                filteredList.add(data.get(i));
                            }
                        }
                    }
                }

                mbatchesList.setLayoutManager(new LinearLayoutManager(Activity_New_Batches.this));
                batchesListAdpter = new BatchesListAdpter(Activity_New_Batches.this, filteredList);
                mbatchesList.setAdapter(batchesListAdpter);
                batchesListAdpter.notifyDataSetChanged();  // data set changed
            }
        });
    }

}
