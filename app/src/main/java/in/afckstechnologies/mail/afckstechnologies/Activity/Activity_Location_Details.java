package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Adapter.CenterListAdpter;
import in.afckstechnologies.mail.afckstechnologies.JsonParser.JsonHelper;
import in.afckstechnologies.mail.afckstechnologies.Models.CenterDAO;

import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.View.RegistrationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.mail.afckstechnologies.R;

public class Activity_Location_Details extends FragmentActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    Button btnSearch;

    JSONObject jsonCenterObj, jsonobject, jsonLeadObj;
    JSONArray jsonarray;
    String centerResponse = "";

    String centerListResponse = "";
    ProgressDialog mProgressDialog;
    CenterDAO centerDAO;
    private RecyclerView mleadList;
    //
    List<CenterDAO> data;
    CenterListAdpter centerListAdpter;
    ArrayList<String> centerIdArrayList;

    Boolean status;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity__location__details);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        btnSearch = (Button) findViewById(R.id.btnSearch);
        if (preferences.getString("name", "").compareTo("") == 0) {
            RegistrationView registrationView = new RegistrationView();
            registrationView.show(getSupportFragmentManager(), "registrationView");
        }

        getCenterList();
        centerIdArrayList = new ArrayList<>();
        mleadList = (RecyclerView) findViewById(R.id.centerList);
        if (preferences.getString("location_flag", "").equals("1")) {
            btnSearch.setVisibility(View.GONE);
        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Config.VALUE.size() == 0) {
                    Toast.makeText(Activity_Location_Details.this, "Please select location", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Activity_Location_Details.this, Activity_Tabls.class);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }

    public void getCenterList() {
        jsonCenterObj = new JSONObject() {
            {
                try {
                    put("user_id", "" + preferences.getInt("user_id", 0));

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
                Log.i("json", "json" + jsonCenterObj);
                centerResponse = serviceAccess.SendHttpPost(Config.URL_CENTER_DETAILS, jsonCenterObj);
                Log.i("resp", "centerResponse" + centerResponse);
                centerDAO = new CenterDAO();
                data = new ArrayList<>();
                if (centerResponse.compareTo("") != 0) {
                    if (isJSONValid(centerResponse)) {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {

                                    JsonHelper jsonHelper = new JsonHelper();
                                    data = jsonHelper.parseCenterList(centerResponse);

                                    centerListAdpter = new CenterListAdpter(Activity_Location_Details.this, data);
                                    mleadList.setAdapter(centerListAdpter);
                                    mleadList.setLayoutManager(new LinearLayoutManager(Activity_Location_Details.this));

                                    jsonobject = new JSONObject(centerResponse);

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
                                // Toast.makeText(Activity_Location_Details.this, "Please check your network connection", Toast.LENGTH_LONG).show();
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

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_Location_Details.this);
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
                        put("branch_id", preferences.getString("center_id", ""));
                        put("user_id", "" + preferences.getInt("user_id", 0));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_SEND_LOCATION_DETAILS, jsonLeadObj);
            Log.i("resp", "centerListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                JSONObject jsonObject = new JSONObject(centerListResponse);
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
                prefEditor.putString("center_flag", "1");
                prefEditor.commit();
                if (preferences.getString("updatelocation", "").equals("newbranch")) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Location_Details.this, Activity_Course_Details_Form.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Location_Details.this, Activity_Tabls.class);
                    finish();
                    startActivity(intent);
                }
                // Close the progressdialog
                mProgressDialog.dismiss();
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
        Intent intent = new Intent(Activity_Location_Details.this, Activity_Student_DashBoard.class);
        this.finish();
        startActivity(intent);
    }

}

