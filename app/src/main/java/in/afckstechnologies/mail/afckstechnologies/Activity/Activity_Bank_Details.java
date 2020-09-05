package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Adapter.BankDeatilsListAdpter;
import in.afckstechnologies.mail.afckstechnologies.Adapter.BatchesListAdpter;
import in.afckstechnologies.mail.afckstechnologies.JsonParser.JsonHelper;
import in.afckstechnologies.mail.afckstechnologies.Models.BankDeatilsDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Bank_Details extends AppCompatActivity {
    TextView textUsername, textAccountno, textBank_name, textBranchName, text_ifsc_code,textAccounttype;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String bankDetailsResponse = "";
    ProgressDialog mProgressDialog;
    Boolean status;
    String msg;

    String name="";
    String account="";
    String bankname="";
    String branchname="";
    String account_type="";
    String ifsccode="";


    private RecyclerView mbankDetailsList;
    //
    List<BankDeatilsDAO> data;
    BankDeatilsListAdpter bankDeatilsListAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__bank__details);
        mbankDetailsList = (RecyclerView) findViewById(R.id.bankDetailsList);

        textUsername = (TextView) findViewById(R.id.textUsername);
        textAccountno = (TextView) findViewById(R.id.textAccountno);
        textBank_name = (TextView) findViewById(R.id.textBank_name);
        textBranchName = (TextView) findViewById(R.id.textBranchName);
        text_ifsc_code = (TextView) findViewById(R.id.text_ifsc_code);
        textAccounttype = (TextView) findViewById(R.id.textAccounttype);

        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

            new getData().execute();

        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

    }


    private class getData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_Bank_Details.this);
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


            Log.i("json", "json" + jsonLeadObj);
            bankDetailsResponse = serviceAccess.SendHttpPost(Config.URL_BANK_DETAILS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + bankDetailsResponse);


            if (bankDetailsResponse.compareTo("") != 0) {
                if (isJSONValid(bankDetailsResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseBankList(bankDetailsResponse);
                                jsonArray = new JSONArray(bankDetailsResponse);

                             /*   JSONObject jsonObject = new JSONObject(bankDetailsResponse);
                                name = jsonObject.getString("acc_holder_name");
                                account = jsonObject.getString("account_no");
                                bankname = jsonObject.getString("bank_name");
                                branchname = jsonObject.getString("branch_name");
                                ifsccode = jsonObject.getString("ifsc_code");
                                account_type= jsonObject.getString("account_type");*/
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
          /*  textUsername.setText(name);
            textAccountno.setText(account);
            textBank_name.setText(bankname);
            textBranchName.setText(branchname);
            text_ifsc_code.setText(ifsccode);
            textAccounttype.setText(account_type);
*/
            if (bankDetailsResponse.compareTo("") != 0) {
                bankDeatilsListAdpter = new BankDeatilsListAdpter(Activity_Bank_Details.this, data);
                mbankDetailsList.setAdapter(bankDeatilsListAdpter);
                mbankDetailsList.setLayoutManager(new LinearLayoutManager(Activity_Bank_Details.this));
                bankDeatilsListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
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
