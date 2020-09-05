package in.afckstechnologies.mail.afckstechnologies.Adapter;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Activity.Activity_Enter_Level_Courses;
import in.afckstechnologies.mail.afckstechnologies.Models.NewCoursesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.ServiceCall.WebClient;
import in.afckstechnologies.mail.afckstechnologies.Utils.Config;
import in.afckstechnologies.mail.afckstechnologies.Utils.FileDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Created by admin on 12/20/2016.
 */

public class NewCoursesListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<NewCoursesDAO> data;
    NewCoursesDAO current;
    int currentPos = 0;
    String id, id1;

    int ID;


    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String courseListResponse = "";
    boolean status;
    String message = "";
    String msg = "";
    MyHolder myHolder;

    // create constructor to innitilize context and data sent from MainActivity
    public NewCoursesListAdpter(Context context, List<NewCoursesDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    public NewCoursesListAdpter(Activity_Enter_Level_Courses activity_enter_level_courses) {
        this.context = activity_enter_level_courses;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_new_course_details, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.view_code.setText(current.getCourse_code());
        myHolder.view_code.setTag(position);

        myHolder.view_courses_name.setText(current.getCourse_name());
        myHolder.view_courses_name.setTag(position);

        myHolder.view_course_duration.setText(current.getTime_duration());
        myHolder.view_course_duration.setTag(position);

        myHolder.view_Prerequisite.setText(current.getPrerequisite());
        myHolder.view_Prerequisite.setTag(position);

        myHolder.view_Recommended.setText(current.getRecommonded());
        myHolder.view_Recommended.setTag(position);

        myHolder.view_course_frequency.setText(current.getFrequency());
        myHolder.view_course_frequency.setTag(position);

        myHolder.view_fees.setText(current.getFees());
        myHolder.view_fees.setTag(position);
        myHolder.removeCourse.setTag(position);
        myHolder.chkSelected.setTag(position);
        myHolder.hide1.setTag(position);
        myHolder.hide2.setTag(position);
        myHolder.hide3.setTag(position);
        myHolder.hide4.setTag(position);
        myHolder.view_Syllabus.setTag(position);
        myHolder.view_YouTbueLink.setTag(position);
        myHolder.view_Pdf.setTag(position);

        if (!current.getPrerequisite().equals("-")) {
            myHolder.hide1.setVisibility(View.VISIBLE);
        } else {
            myHolder.hide1.setVisibility(View.GONE);
        }
        if (!current.getRecommonded().equals("-")) {
            myHolder.hide2.setVisibility(View.VISIBLE);
        } else {
            myHolder.hide2.setVisibility(View.GONE);
        }
        if (!current.getSyllabuspath().equals("-")) {
            myHolder.hide3.setVisibility(View.VISIBLE);
        } else {
            myHolder.hide3.setVisibility(View.GONE);
        }
        if (!current.getYou_tube_link().equals("-")) {
            myHolder.hide4.setVisibility(View.VISIBLE);
        } else {
            myHolder.hide4.setVisibility(View.GONE);
        }
        myHolder.chkSelected.setChecked(data.get(position).isSelected());
        myHolder.chkSelected.setTag(data.get(position));
        myHolder.viewchkSelected.setTag(data.get(position));

        if (current.getIsselected().equals("selected")) {
            myHolder.chkSelected.setChecked(true);
            // myHolder.viewchkSelected.setVisibility(View.VISIBLE);
            Config.VALUE.add(data.get(pos).getId());
        }

        myHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                NewCoursesDAO contact = (NewCoursesDAO) cb.getTag();
                contact.setSelected(cb.isChecked());
                data.get(pos).setSelected(cb.isChecked());

                // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked()+data.get(pos).getId(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getId(), Toast.LENGTH_LONG).show();
                    Config.VALUE.add(data.get(pos).getId());
                    id = data.get(pos).getId();
                    new submitData().execute();
                } else if (!cb.isChecked()) {
                    // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getId(), Toast.LENGTH_LONG).show();
                    id1 = data.get(pos).getId();
                    new deleteSale().execute();
                    Config.VALUE.remove(data.get(pos).getId());

                }

            }
        });
        myHolder.view_Syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                new DownloadFile().execute(current.getSyllabuspath(), current.getCourse_name() + ".pdf");
                current.setIsselected("1");


            }
        });
        if (current.getIsselected().equals("1")) {
            myHolder.view_Syllabus.setVisibility(View.GONE);
            myHolder.view_Pdf.setVisibility(View.VISIBLE);
        } else {
            myHolder.view_Syllabus.setVisibility(View.VISIBLE);
            myHolder.view_Pdf.setVisibility(View.GONE);
        }
        myHolder.view_Pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/AFCKS Syllabus/" + current.getCourse_name() + ".pdf");  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    context.startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myHolder.view_YouTbueLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getYou_tube_link()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(current.getYou_tube_link()));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView view_code;
        TextView view_courses_name;
        TextView view_fees;
        TextView view_course_duration;
        TextView view_Prerequisite;
        TextView view_status;
        TextView view_Recommended;
        LinearLayout hide1, hide2, hide3, hide4;
        ImageView removeCourse, view_Syllabus, view_YouTbueLink, view_Pdf;
        TextView view_course_frequency;
        public CheckBox chkSelected, viewchkSelected;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_code = (TextView) itemView.findViewById(R.id.view_code);
            view_courses_name = (TextView) itemView.findViewById(R.id.view_courses_name);
            view_course_duration = (TextView) itemView.findViewById(R.id.view_course_duration);
            view_fees = (TextView) itemView.findViewById(R.id.view_fees);
            view_Prerequisite = (TextView) itemView.findViewById(R.id.view_Prerequisite);
            view_Recommended = (TextView) itemView.findViewById(R.id.view_Recommended);
            view_course_frequency = (TextView) itemView.findViewById(R.id.view_course_frequency);
            removeCourse = (ImageView) itemView.findViewById(R.id.removeCourse);
            view_Syllabus = (ImageView) itemView.findViewById(R.id.view_Syllabus);
            view_YouTbueLink = (ImageView) itemView.findViewById(R.id.view_YouTbueLink);
            view_Pdf = (ImageView) itemView.findViewById(R.id.view_Pdf);
            hide1 = (LinearLayout) itemView.findViewById(R.id.hide1);
            hide2 = (LinearLayout) itemView.findViewById(R.id.hide2);
            hide3 = (LinearLayout) itemView.findViewById(R.id.hide3);
            hide4 = (LinearLayout) itemView.findViewById(R.id.hide4);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);

            viewchkSelected = (CheckBox) itemView.findViewById(R.id.viewchkSelected);

            removeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ID = (Integer) v.getTag();

                    current = data.get(ID);
                    id = current.getId();
                    //  Toast.makeText(context, "Delete sale" + current.getId(), Toast.LENGTH_SHORT).show();
                    new deleteSale().execute();


                }
            });


        }

    }

    // method to access in activity after updating selection
    public List<NewCoursesDAO> getSservicelist() {
        System.out.print("data in adapter--->" + data);
        return data;
    }

    //

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("course_id", id);
                        put("user_id", "" + preferences.getInt("user_id", 0));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_SEND_DETAILS, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);


            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {


                    try {

                        JSONObject jsonObject = new JSONObject(courseListResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(context, "Please check your webservice", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {

            if (status) {
                prefEditor.putString("course_flag", "1");
                prefEditor.remove("course_id").commit();
                prefEditor.commit();

                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }
    }

    //
    private class deleteSale extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
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
                        put("course_id", id1);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            courseListResponse = serviceAccess.SendHttpPost(Config.URL_DELETE_COURSE, jsonLeadObj);
            Log.i("resp", "leadListResponse" + courseListResponse);


            if (courseListResponse.compareTo("") != 0) {
                if (isJSONValid(courseListResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(courseListResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(courseListResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                // removeAt(ID);
                // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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

    public void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    private class DownloadFile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "AFCKS Syllabus");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = FileDownloader.downloadFile(fileUrl, pdfFile);

            return s;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid.equals("1")) {
                notifyDataSetChanged();
            } else {

            }
        }
    }

}
