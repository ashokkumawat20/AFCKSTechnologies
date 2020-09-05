package in.afckstechnologies.mail.afckstechnologies.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.FirebaseNotification.SharedPrefManager;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.SmsListener;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.SmsReceiver;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;

public class RegistrationView extends DialogFragment {

    Button placeBtn;
    private EditText nameEdtTxt;
    private EditText lastnameEdtTxt;
    private EditText phEdtTxt;
    private EditText emailEdtTxt;
    private EditText AddEdtTxt;
    private TextView dateEdtTxt, titleTxt, header;
    private TelephonyManager telephonyManager;
    private String deviceId = "";
    private Spinner spnrUserType;
    private Calendar myCalendar;
    Context context;
    SharedPreferences preferences;
    Editor prefEditor;
    DatePickerDialog dp = null;
    String loginResponse = "";
    JSONObject jsonObj;
    String device_id;
    Boolean status;
    int user_id;
    boolean click = true;
    int count = 0;

    //8-3-2017
    RadioGroup radioGroup;
    private RadioButton radioButton;
    View registerView;
    int pos;
    int pos1;
    String gender = "";
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        registerView = inflater.inflate(R.layout.dialog_registration, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nameEdtTxt = (EditText) registerView.findViewById(R.id.nameEdtTxt);
        lastnameEdtTxt = (EditText) registerView.findViewById(R.id.lastNameEdtTxt);
        phEdtTxt = (EditText) registerView.findViewById(R.id.phEdtTxt);
        emailEdtTxt = (EditText) registerView.findViewById(R.id.emailEdtTxt);
        titleTxt = (TextView) registerView.findViewById(R.id.titleTxt);
        header = (TextView) registerView.findViewById(R.id.header);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();
        // device_id = getDeviceId();
        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
//8-3-2017
        radioGroup = (RadioGroup) registerView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroup.indexOfChild(registerView.findViewById(checkedId));
                switch (pos) {
                    case 1:

                        gender = "Female";
                        // Toast.makeText(getActivity(), "You have Clicked RadioButton 1"+gender,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        gender = "Male";
                        // Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //The default selection is RadioButton 1
                        gender = "Female";
                        // Toast.makeText(getActivity(), gender,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        dataAdapter.add("Select gender");
        dataAdapter.add("He");
        dataAdapter.add("She");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnrUserType = (Spinner) registerView.findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#234e5e"));
        spnrUserType.setAdapter(dataAdapter);


        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        getDialog().setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //Hide your keyboard here!!!
                    Toast.makeText(getActivity(), "PLease enter your information to get us connected with you.", Toast.LENGTH_LONG).show();
                    return true; // pretend we've processed it
                } else
                    return false; // pass on to be processed as normal
            }
        });
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                String companyFlag = messageText.substring(12);
                Log.d("companyFlag--->", companyFlag);
                Toast.makeText(getActivity(), "Message: " + companyFlag, Toast.LENGTH_LONG).show();
                if (id.equals(companyFlag)) {
                    //new submitData().execute();
                } else {
                    // Toast.makeText(getActivity(), "Please enter your correct mobile no!"+"\n"+"This no should be same as your device mobile no.", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("This no should be same as your device mobile no.")
                            .setCancelable(false)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Please enter your correct mobile no!");
                    alert.show();
                }

            }
        });
        placeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = nameEdtTxt.getText().toString();
                String lastname = lastnameEdtTxt.getText().toString();
                String phoneno = phEdtTxt.getText().toString();
                String emailid = emailEdtTxt.getText().toString().trim();
                String spinnerSelected = spnrUserType.getSelectedItem().toString();


                if (validate(name, lastname, phoneno, emailid, gender)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {
                            //Toast.makeText(context, "Thank You! your information is added Successfully", Toast.LENGTH_LONG).show();
                            sendData(name, phoneno, emailid, lastname, gender);
                           /* Random random = new Random();
                            id = String.format("%06d", random.nextInt(1000000));
                            String message = "AFCKST Code " + id;
                            // Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                            Log.d("Random no---->", id);

                            String result = sendSms1("+91"+phoneno, message);
                            Log.d("sms--->",result);*/

                            click = false;
                        } else {
                            Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }


                } else {

                }

            }
        });
        return registerView;
    }


    public boolean validate(String name, String lastname, String phoneno, String emailid, String spinnerSelected) {
        boolean isValidate = false;
        if (spinnerSelected.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please select gender.", Toast.LENGTH_LONG).show();
        } else if (name.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  first name", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (lastname.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter  last name", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (phoneno.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (phoneno.trim().compareTo("") == 0 || phoneno.length() != 10) {
            Toast.makeText(getActivity(), "Please enter a 10 digit valid Mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (!validateEmail(emailid)) {
            if (!emailid.equals("")) {
                Toast.makeText(getActivity(), "Please enter valid Email Id.", Toast.LENGTH_LONG).show();
                isValidate = false;
            } else {
                isValidate = true;
            }
        } else {
            isValidate = true;
        }
        return isValidate;
    }

    /**
     * email validation
     */
    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

    public boolean validateEmail(String email) {
        if (!email.contains("@")) {
            return false;
        }
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public void sendData(final String name, final String phoneno, final String emailid, final String lastname, final String spinnerSelected) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        final String date = format.format(cal.getTime());
        final String token = SharedPrefManager.getInstance(getActivity()).getDeviceToken();
        jsonObj = new JSONObject() {
            {
                try {
                    put("first_name", name);
                    put("last_name", lastname);
                    put("mobile_no", phoneno);
                    put("email_id", emailid);
                    put("gender", spinnerSelected);
                    put("fcm_id", token);
                    put("created_at", date);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                Log.d("jsonObj", "" + jsonObj);

                loginResponse = serviceAccess.SendHttpPost(Config.URL_STUDENT_REGISTRATION, jsonObj);

                Log.i("loginResponse", "loginResponse" + loginResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (loginResponse.compareTo("") == 0) {
                                    Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(loginResponse);
                                        status = jObject.getBoolean("status");
                                        if (status) {
                                            prefEditor.putString("name", name + " " + lastname);
                                            prefEditor.putString("ufname", name);
                                            prefEditor.putString("umnumber", phoneno);
                                            prefEditor.putInt("user_id", jObject.getInt("user_id"));
                                            prefEditor.putString("location_flag", "1");
                                            prefEditor.commit();
                                            dismiss();
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN) {
                        Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_SHORT).show();
                        count++;
                        if (count >= 2) {
                            update();
                        }
                        return true;
                    } else {
                        //Hide your keyboard here!!!!!!
                        return true; // pretend we've processed it
                    }
                } else
                    return false; // pass on to be processed as normal
            }
        });
    }

    private void update() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public static String sendSms1(String tempMobileNumber, String message) {
        String sResult = null;
        try {
// Construct data
            //String phonenumbers = "9657816221";
            String data = "user=" + URLEncoder.encode("AfcksTechnologies", "UTF-8");
            data += "&password=" + URLEncoder.encode("skzq5sjd", "UTF-8");
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

    public String getDeviceId() {

        telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        Log.i("Registration", "" + deviceId);
        return deviceId;
    }

}