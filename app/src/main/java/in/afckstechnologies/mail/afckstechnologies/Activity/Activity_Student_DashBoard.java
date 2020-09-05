package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.afckstechnologies.mail.afckstechnologies.FirebaseNotification.SharedPrefManager;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.AppStatus;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.Constant;
import in.afckstechnologies.mail.afckstechnologies.Utils.Utility;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.View.DisplayOptionsView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity_Student_DashBoard extends AppCompatActivity {
    LinearLayout takeProfile, takePreferences, takeCourses, takeLocation, takeBankDtails, takesBatches, takeClasses, takeJobs,editProfile;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    TextView txtuname;
    LinearLayout website;
    WebView webview;
    //Camera
    ImageView imageView1;
    // RoundImage roundedImage;
    String imageName,smspassResponse="";
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 3;

    //

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    boolean isImageFitToScreen;
    JSONObject jsonObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__student__dash_board);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        takeProfile = (LinearLayout) findViewById(R.id.takeProfile);
        takePreferences = (LinearLayout) findViewById(R.id.takePreferences);
        takeBankDtails = (LinearLayout) findViewById(R.id.takeBankDtails);
        takeCourses = (LinearLayout) findViewById(R.id.takeCourses);
        takeLocation = (LinearLayout) findViewById(R.id.takeLocation);
        takesBatches = (LinearLayout) findViewById(R.id.takesBatches);
        takeClasses = (LinearLayout) findViewById(R.id.takeClasses);
        takeJobs = (LinearLayout) findViewById(R.id.takeJobs);
        editProfile= (LinearLayout) findViewById(R.id.editProfile);
        txtuname = (TextView) findViewById(R.id.txtuname);
        website = (LinearLayout) findViewById(R.id.website);
        txtuname.setText(preferences.getString("name", ""));
        webview = new WebView(this);
        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        System.out.println("token--------->" + token);
        animation();
        takeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), DemoClassActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), Activity_User_Profile.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), User_PrecTabsActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), Activity_Tabls.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }

            }
        });
        takeBankDtails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), Activity_Bank_Details.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takesBatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), Activity_New_Batches.class);
                    intent.putExtra("course_name", "");
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  webview.loadUrl("https://www.afcks.com");

                Uri uri = Uri.parse("https://www.afckstechnologies.in/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        takeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(getApplicationContext(), Activity_Location_Details.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        takeJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    DisplayOptionsView displayOptionsView = new DisplayOptionsView();
                    displayOptionsView.show(getSupportFragmentManager(), "displayOptionsView");
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });

        takeClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Student_DashBoard.this, UpcomingClassDetailsActivity.class);
                startActivity(intent);
            }
        });

//for profile
        imageView1 = (ImageView) findViewById(R.id.profile_image);
        imageName = preferences.getString("imageName", "");
        Log.d("imageName", preferences.getString("imageName", ""));
        if (!imageName.equals("")) {
            //  Toast.makeText(getApplication(), "Helo" + imageName, Toast.LENGTH_LONG).show();
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AFCKS" + File.separator + imageName;

            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            String bmp1 = "" + bmp;
            Log.d("bytes", "" + bmp1);
            if (!bmp1.equals("null")) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                imageView1.setImageBitmap(bmp);
            } else {

                Log.d("bytes", "" + bmp);
            }
        }


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();

        if (AppStatus.getInstance(Activity_Student_DashBoard.this).isOnline()) {

            new smspassAvailable().execute();

        }


    }

    private void animation() {
        try {
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {

                public void run() {
                    takeProfile.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(100, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeProfile.startAnimation(slide);
                }
            }, 0);

            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {

                public void run() {
                    takePreferences.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takePreferences.startAnimation(slide);
                }
            }, 800);

            final Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {

                public void run() {
                    takeCourses.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeCourses.startAnimation(slide);
                }
            }, 1600);
            final Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {

                public void run() {
                    takeLocation.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeLocation.startAnimation(slide);
                }
            }, 2400);
            final Handler handler5 = new Handler();
            handler5.postDelayed(new Runnable() {

                public void run() {
                    takeBankDtails.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeBankDtails.startAnimation(slide);
                }
            }, 2800);
            final Handler handler6 = new Handler();
            handler6.postDelayed(new Runnable() {

                public void run() {
                    takesBatches.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takesBatches.startAnimation(slide);
                }
            }, 3200);

            final Handler handler7 = new Handler();
            handler7.postDelayed(new Runnable() {

                public void run() {
                    takeClasses.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeClasses.startAnimation(slide);
                }
            }, 3600);

            final Handler handler8 = new Handler();
            handler8.postDelayed(new Runnable() {

                public void run() {
                    takeJobs.setVisibility(View.VISIBLE);
                    TranslateAnimation slide = new TranslateAnimation(200, 0, 0, 0);
                    slide.setDuration(800);
                    slide.setFillAfter(true);
                    takeJobs.startAnimation(slide);
                }
            }, 4000);


        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Student_DashBoard.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Activity_Student_DashBoard.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        // Internal sdcard location
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "AFCKS");
        // Create the storage directory if it does not exist
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Log.d("TAG", "Oops! Failed create " + " directory");
                //return null;
            }
        }
        File destination = new File(folder.getPath(), System.currentTimeMillis() + ".jpg");

        prefEditor.putString("imageName", System.currentTimeMillis() + ".jpg");
        prefEditor.commit();

        // File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView1.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


                // Internal sdcard location
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "AFCKS");
                // Create the storage directory if it does not exist
                if (!folder.exists()) {
                    if (!folder.mkdir()) {
                        Log.d("TAG", "Oops! Failed create " + " directory");
                        //return null;
                    }
                }
                File destination = new File(folder.getPath(), System.currentTimeMillis() + ".jpg");
                prefEditor.putString("imageName", System.currentTimeMillis() + ".jpg");
                prefEditor.commit();
                //File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".jpg");


                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageView1.setImageBitmap(bm);
    }
    private class smspassAvailable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(TrainerVerfiyActivity.this);
            // Set progressdialog title
            // mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            // mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObj = new JSONObject() {
                {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            smspassResponse = serviceAccess.SendHttpPost(Config.URL_GETUSERNAMEPASSSMS, jsonObj);
            Log.i("resp", "smspassResponse" + smspassResponse);


            if (smspassResponse.compareTo("") != 0) {
                if (isJSONValid(smspassResponse)) {


                    try {

                        JSONArray introJsonArray = new JSONArray(smspassResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    // Toast.makeText(getActivity(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                //  Toast.makeText(getActivity(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                JSONArray introJsonArray = new JSONArray(smspassResponse);
                for (int i = 0; i < introJsonArray.length(); i++) {
                    JSONObject introJsonObject = introJsonArray.getJSONObject(i);
                    if (introJsonObject.getString("type").equals("sms")) {
                        prefEditor.putString("sms_username", introJsonObject.getString("sms_id"));
                        prefEditor.putString("sms_password", introJsonObject.getString("password"));
                        prefEditor.commit();
                    }

                    if (introJsonObject.getString("type").equals("AFCKS_email")) {
                        prefEditor.putString("mail_username", introJsonObject.getString("sms_id"));
                        prefEditor.putString("mail_password", introJsonObject.getString("password"));
                        prefEditor.commit();
                    }
                    if (introJsonObject.getString("type").equals("Demo_Source_Ref")) {
                        prefEditor.putString("know_source", introJsonObject.getString("plain_text"));
                        prefEditor.commit();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Close the progressdialog
            //  mProgressDialog.dismiss();

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
