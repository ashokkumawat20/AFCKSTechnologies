package in.afckstechnologies.mail.afckstechnologies.View;

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
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Activity.Activity_New_Batches;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookSeatView extends DialogFragment {

    Button placeBtn, zeroFeesBtn;
    private EditText transactionidEdtTxt;

    private EditText amountEdtTxt;
    private EditText dpicker;
    private EditText AddEdtTxt;
    private TextView dateEdtTxt, titleTxt, header;


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

    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View registerView = inflater.inflate(R.layout.dialog_book_seat, null);

        context = getActivity();
        Window window = getDialog().getWindow();

        // set "origin" to top left corner, so to speak
        window.setGravity(Gravity.CENTER | Gravity.CENTER);

        // after that, setting values for x and y works "naturally"
        WindowManager.LayoutParams params = window.getAttributes();

        params.y = 50;
        window.setAttributes(params);

        transactionidEdtTxt = (EditText) registerView.findViewById(R.id.transactionidEdtTxt);
        // nameEdtTxt.setTypeface(tf);
        dpicker = (EditText) registerView.findViewById(R.id.datePickerCal);
        //lastnameEdtTxt.setTypeface(tf);
        amountEdtTxt = (EditText) registerView.findViewById(R.id.amountEdtTxt);
        // phEdtTxt.setTypeface(tf);


        titleTxt = (TextView) registerView.findViewById(R.id.titleTxt);
        // titleTxt.setTypeface(tf);
        header = (TextView) registerView.findViewById(R.id.header);
        // header.setTypeface(tf);
        preferences = getActivity().getSharedPreferences("Prefrence", getActivity().MODE_PRIVATE);
        prefEditor = preferences.edit();

        placeBtn = (Button) registerView.findViewById(R.id.placeBtn);
        zeroFeesBtn = (Button) registerView.findViewById(R.id.zeroFeesBtn);
        dpicker.setOnClickListener(new OnClickListener() {
            private int mYear, mMonth, mDay;
            private SimpleDateFormat dateFormatter;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                dateFormatter = new SimpleDateFormat("d MMM yyyy", Locale.US);

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        mcurrentDate.set(selectedyear, selectedmonth, selectedday);
                        dpicker.setText(dateFormatter.format(mcurrentDate.getTime()));
                        date = format.format(mcurrentDate.getTime());

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
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

        placeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String transaction = transactionidEdtTxt.getText().toString();
                //  String date = dpicker.getText().toString();
                String amount = amountEdtTxt.getText().toString();


                if (validate(transaction, date, amount)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {

                            sendData(transaction, date, amount);
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

        zeroFeesBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String transaction = "Book seat with Zero fees";
                // String date = dpicker.getText().toString();
                String amount = "0";

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(c.getTime());

                if (validate(transaction, date, amount)) {

                    if (AppStatus.getInstance(context).isOnline()) {
                        // dismiss();
                        if (click) {

                            sendData(transaction, date, amount);
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

    public boolean validate(String transaction, String date, String amount) {
        boolean isValidate = false;
        if (transaction.trim().compareTo("") == 0 || date.trim().compareTo("") == 0 || amount.trim().compareTo("") == 0) {
            Toast.makeText(getActivity(), "Please enter all the values.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }


    public void sendData(final String transaction, final String date, final String amount) {
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       final String date = format.format(date1);
*/
        jsonObj = new JSONObject() {
            {
                try {
                    put("user_id", "" + preferences.getInt("user_id", 0));
                    put("batch_id", preferences.getString("batch_id", ""));
                    put("trans_id", transaction);
                    put("pay_date", date);
                    put("amount", amount);
                    put("status", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();

                Log.i("jsonObj", "jsonObj" + jsonObj);
                loginResponse = serviceAccess.SendHttpPost(Config.URL_BOOKING_BATCH, jsonObj);

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
                                           // Toast.makeText(context, "Thank You! your information are sent for approval", Toast.LENGTH_LONG).show();
                                            dismiss();
                                            Intent intent = new Intent(context, Activity_New_Batches.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(context, "Thank You! you have allready booked this course", Toast.LENGTH_LONG).show();
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
                        dismiss();
                        // Toast.makeText(getActivity(), "Your information is valuable for us and won't be misused.", Toast.LENGTH_LONG).show();
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


}