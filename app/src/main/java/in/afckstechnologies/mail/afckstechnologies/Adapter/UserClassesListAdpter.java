package in.afckstechnologies.mail.afckstechnologies.Adapter;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


import in.afckstechnologies.mail.afckstechnologies.Models.UserClassesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.View.NotesNameDetailsView;


public class UserClassesListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<UserClassesDAO> data;
    UserClassesDAO current;
    int currentPos = 0;
    int id;
    String centerId;
    int  clickflag = 1;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    // create constructor to innitilize context and data sent from MainActivity
    public UserClassesListAdpter(Context context, List<UserClassesDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_classes_schdule_list_view, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.txtClassdate.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getBatch_date()));
        myHolder.txtClassdate.setTag(position);


        myHolder.txtNextClassdate.setText(formateDateFromstring("yyyy-MM-dd", "dd-MMM-yyyy", current.getNext_class_date()));
        myHolder.toDayTopic.setMovementMethod(LinkMovementMethod.getInstance());

        myHolder.toDayTopic.setText(current.getTodays_topics());
        myHolder.toDayTopic.setTag(position);
        myHolder.toDayTopic.setPaintFlags(myHolder.toDayTopic.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        myHolder.nextClassTopic.setText(current.getNext_class_topics());
        myHolder.nextClassTopic.setTag(position);

        myHolder.textClasstiming.setText(current.getTimings());
        myHolder.textClasstiming.setTag(position);

        myHolder.txtTrainer.setText(current.getTrainer_name());
        myHolder.txtTrainer.setTag(position);

        myHolder.textLocation.setText(current.getBranch_name());
        myHolder.textLocation.setTag(position);

        myHolder.totalLecture.setText(current.getLecture_count());
        myHolder.totalLecture.setTag(position);

        myHolder.countNo.setText(current.getNumbers());
        myHolder.countNo.setTag(position);

        myHolder.txtBatchCode.setText(current.getBatch_id());
        myHolder.txtBatchCode.setTag(position);

        myHolder.whatsapp.setTag(position);
        myHolder.msg.setTag(position);
        myHolder.textClassfrequency.setText(current.getFrequency());
        myHolder.textClassfrequency.setTag(position);

        myHolder.textFileNames.setText(current.getFile_names());
        myHolder.textFileNames.setTag(position);

        myHolder.location.setTag(position);


        myHolder.showHide.setTag(position);

        myHolder.toDayTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickflag == 1) {
                    clickflag = 2;
                    myHolder.lFileLayout.setVisibility(View.VISIBLE);
                    myHolder.lFileLayout.setTag(position);
                } else {
                    clickflag = 1;
                    myHolder.lFileLayout.setVisibility(View.GONE);
                    myHolder.lFileLayout.setTag(position);
                }
            }
        });

        myHolder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);

                Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:" + Double.parseDouble(current.getLatitude()) + "," + Double.parseDouble(current.getLongitude()) + " (" + "AFCKS Technologies" + ")"));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Only if initiating from a Broadcast Receiver
                String mapsPackageName = "com.google.android.apps.maps";

                i.setClassName(mapsPackageName, "com.google.android.maps.MapsActivity");
                i.setPackage(mapsPackageName);

                context.startActivity(i);
            }
        });

       /* myHolder.toDayTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                prefEditor.putString("path",current.getPath());
                prefEditor.commit();
                NotesNameDetailsView notesNameDetailsView = new NotesNameDetailsView();
                notesNameDetailsView.show(((FragmentActivity) context).getSupportFragmentManager(), "notesNameDetailsView");

            }
        });*/


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView txtTrainer, txtNextClassdate, toDayTopic, nextClassTopic, txtClassdate, textClasstiming, textLocation, totalLecture, countNo, textClassfrequency, txtBatchCode,textFileNames;
        ImageView whatsapp, msg, view_more, location;
        LinearLayout lFileLayout,showHide;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txtTrainer = (TextView) itemView.findViewById(R.id.txtTrainer);
            txtNextClassdate = (TextView) itemView.findViewById(R.id.txtNextClassdate);
            toDayTopic = (TextView) itemView.findViewById(R.id.toDayTopic);
            nextClassTopic = (TextView) itemView.findViewById(R.id.nextClassTopic);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            msg = (ImageView) itemView.findViewById(R.id.msg);
            view_more = (ImageView) itemView.findViewById(R.id.view_more);
            txtClassdate = (TextView) itemView.findViewById(R.id.txtClassdate);
            textClasstiming = (TextView) itemView.findViewById(R.id.textClasstiming);
            textLocation = (TextView) itemView.findViewById(R.id.textLocation);
            totalLecture = (TextView) itemView.findViewById(R.id.totalLecture);
            countNo = (TextView) itemView.findViewById(R.id.countNo);
            textClassfrequency = (TextView) itemView.findViewById(R.id.textClassfrequency);
            txtBatchCode = (TextView) itemView.findViewById(R.id.txtBatchCode);
            textFileNames= (TextView) itemView.findViewById(R.id.textFileNames);
            location = (ImageView) itemView.findViewById(R.id.location);
            lFileLayout=(LinearLayout)itemView.findViewById(R.id.lFileLayout);
            showHide=(LinearLayout)itemView.findViewById(R.id.showHide);
        }

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }


}
