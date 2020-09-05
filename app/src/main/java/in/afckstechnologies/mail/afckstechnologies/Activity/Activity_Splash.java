package in.afckstechnologies.mail.afckstechnologies.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import in.afckstechnologies.mail.afckstechnologies.R;

public class Activity_Splash extends AppCompatActivity {
    String msg = "Android : ";
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__splash);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        init();


    }


    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
        init();

    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
        init();

    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");

    }

    /**
     * Called when the activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");

    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d(msg, "The onDestroy() event");

    }

    public void init() {
        if (preferences.getString("name", "").compareTo("") == 0 && preferences.getInt("user_id", 0) == 0) {
            prefEditor.putString("location_flag", "0");
            prefEditor.commit();
            Intent intent = new Intent(Activity_Splash.this, Activity_Location_Details.class);
            startActivity(intent);
        }
       /* else if (preferences.getString("center_flag", "").compareTo("") == 0) {
            Intent intent = new Intent(Activity_Splash.this, Activity_Location_Details.class);
            startActivity(intent);
        }
        else if (preferences.getString("course_flag", "").compareTo("") == 0) {
            Intent intent = new Intent(Activity_Splash.this, Activity_Tabls.class);
            startActivity(intent);
        }*/
        else {
            prefEditor.putString("location_flag", "1");
            prefEditor.commit();
            Intent intent = new Intent(Activity_Splash.this, Activity_Student_DashBoard.class);

            startActivity(intent);
        }

    }

}
