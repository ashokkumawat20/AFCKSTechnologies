package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.afckstechnologies.mail.afckstechnologies.R;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;
import in.afckstechnologies.mail.afckstechnologies.View.BookSeatView;

public class Activity_Full_Deatils_Batches extends AppCompatActivity {
    TextView textstartDate, textendDate, textTimings, textCourse, text_Notes, textTrainer, textstatus, textlocation, textfrequency, textfee, textDuration;
    Button bankDetails, bookedSeat, pending;
    String id = "";
    ImageView bookSeat;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String centerListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    String shareData = "", wa_invite_link = "";
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__full__deatils__batches);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        textstartDate = (TextView) findViewById(R.id.textstartDate);
        textendDate = (TextView) findViewById(R.id.textendDate);
        textTimings = (TextView) findViewById(R.id.textTimings);
        textCourse = (TextView) findViewById(R.id.textCourse);
        text_Notes = (TextView) findViewById(R.id.text_Notes);
        textTrainer = (TextView) findViewById(R.id.textTrainer);
        textstatus = (TextView) findViewById(R.id.textstatus);
        textfrequency = (TextView) findViewById(R.id.textfrequency);
        textfee = (TextView) findViewById(R.id.textfee);
        textDuration = (TextView) findViewById(R.id.textDuration);
        textlocation = (TextView) findViewById(R.id.textlocation);
        bookSeat = (ImageView) findViewById(R.id.bookSeat);
        bankDetails = (Button) findViewById(R.id.bankDetails);
        bookedSeat = (Button) findViewById(R.id.bookedSeat);
        pending = (Button) findViewById(R.id.pending);
        Intent intent = getIntent();
        textstartDate.setText(intent.getStringExtra("start_date"));
        textendDate.setText(intent.getStringExtra("end_date"));
        textTimings.setText(intent.getStringExtra("start_time"));
        textCourse.setText(intent.getStringExtra("course_name"));
        textTrainer.setText(intent.getStringExtra("trainer"));
        text_Notes.setText(intent.getStringExtra("notes"));
        id = intent.getStringExtra("id");
        wa_invite_link = intent.getStringExtra("wa_invite_link");
        shareData = intent.getStringExtra("shareData");
        if (intent.getStringExtra("status").equals("1")) {
            textstatus.setText("Confirmed");

        } else if (intent.getStringExtra("status").equals("2")) {

            textstatus.setText("Tentative");
        }
        textlocation.setText(intent.getStringExtra("location"));
        textfrequency.setText(intent.getStringExtra("frequency"));
        textfee.setText(intent.getStringExtra("fees"));
        textDuration.setText(intent.getStringExtra("duration"));


        bookSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BookSeatView bookSeatView = new BookSeatView();
                //bookSeatView.show(getSupportFragmentManager(), "registrationView");
                PackageManager pm = getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    //waIntent.setPackage("com.whatsapp.w4b");
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, shareData);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Full_Deatils_Batches.this, Activity_Bank_Details.class);
                startActivity(intent);
            }
        });

        bookedSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    if (!wa_invite_link.equals("")) {
                        try {
                            Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                            intentWhatsapp.setData(Uri.parse(wa_invite_link));

                            intentWhatsapp.setPackage("com.whatsapp");
                          //intentWhatsapp.setPackage("com.whatsapp.w4b");
                            startActivity(intentWhatsapp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Whatsapp Group link not available", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*bookedSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Full_Deatils_Batches.this);
                builder.setMessage("Do you want to delete booked batch ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {

                                    new deleteSale().execute();

                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                                }
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Deleting Booked Batch");
                alert.show();
            }
        });*/

    }

    //
    private class deleteSale extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Activity_Full_Deatils_Batches.this);
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
                        put("batch_id", id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            centerListResponse = serviceAccess.SendHttpPost(Config.URL_DELETE_STBATCH_BOOKING, jsonLeadObj);
            Log.i("resp", "leadListResponse" + centerListResponse);


            if (centerListResponse.compareTo("") != 0) {
                if (isJSONValid(centerListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(centerListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(centerListResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                //  removeAt(ID);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(Activity_Full_Deatils_Batches.this, Activity_New_Batches.class);
                startActivity(intent);
                // Close the progressdialog
                mProgressDialog.dismiss();


            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
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
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(Activity_Full_Deatils_Batches.this, Activity_New_Batches.class);
        this.finish();
        startActivity(intent);
    }
}
