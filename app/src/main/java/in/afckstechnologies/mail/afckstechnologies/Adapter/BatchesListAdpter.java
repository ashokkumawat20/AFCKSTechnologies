package in.afckstechnologies.mail.afckstechnologies.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.afckstechnologies.mail.afckstechnologies.Activity.Activity_Full_Deatils_Batches;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;
import in.afckstechnologies.mail.afckstechnologies.View.StudentFeesEntryView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by admin on 12/20/2016.
 */

public class BatchesListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<NewBatchesDAO> data;
    NewBatchesDAO current;
    int currentPos = 0;
    int id;
    String centerId;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    // create constructor to innitilize context and data sent from MainActivity
    public BatchesListAdpter(Context context, List<NewBatchesDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_batch_schdule_list_view, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.view_batch_no.setText("Batch. No. " + current.getBatch_code());
        myHolder.view_batch_no.setTag(position);

        String batchtype = current.getBatchtype().toString().trim();

        Log.d("batch type", batchtype);
        if (batchtype.equalsIgnoreCase("1")) {
            myHolder.view_batch_no.setBackgroundColor(Color.parseColor("#70AD47"));
            myHolder.view_batch_no.setText("Batch. No. " + current.getBatch_code());
            myHolder.view_batch_no.setTag(position);
        } else if (batchtype.equalsIgnoreCase("2")) {
            myHolder.view_batch_no.setBackgroundColor(Color.parseColor("#556199"));
            myHolder.view_batch_no.setText("Batch. No. " + current.getBatch_code());
            myHolder.view_batch_no.setTag(position);
        }
        myHolder.view_course_name.setText(current.getCourse_name());
        myHolder.view_course_name.setTag(position);

        myHolder.view_frequency.setText(current.getFrequency());
        myHolder.view_frequency.setTag(position);

        myHolder.view_Location.setText(current.getBranch_name());
        myHolder.view_Location.setTag(position);

        myHolder.view_start_date.setText(current.getStart_date());
        myHolder.view_start_date.setTag(position);

        myHolder.whatsapp.setTag(position);
        myHolder.msg.setTag(position);
        myHolder.view_more.setTag(position);
        myHolder.view_Details.setTag(position);
        myHolder.bookingstatus.setTag(position);
        if (current.getIsbooked().equals("booked")) {
            myHolder.bookingstatus.setVisibility(View.VISIBLE);
        } else {
            myHolder.bookingstatus.setVisibility(View.GONE);
        }

        myHolder.view_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID = (Integer) view.getTag();
                current = data.get(ID);
                // Toast.makeText(context,""+current.getId()+""+preferences.getInt("user_id", 0),Toast.LENGTH_LONG).show();
                prefEditor.putString("trans_batch_id", current.getId());
                prefEditor.putString("trans_user_id", "" + preferences.getInt("user_id", 0));
                prefEditor.commit();
                StudentFeesEntryView studentFeesEntryView = new StudentFeesEntryView();
                studentFeesEntryView.show(((FragmentActivity) context).getSupportFragmentManager(), "studentFeesEntryView");


            }
        });

        myHolder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID = (Integer) view.getTag();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Calendar cal = Calendar.getInstance();
                final String date = format.format(cal.getTime());
                current = data.get(ID);
                prefEditor.putString("batch_id", current.getId());
                prefEditor.putString("course_id", current.getCourse_id());
                prefEditor.commit();
                String batchtype = current.getBatchtype().toString().trim();
                String status = "";

                if (batchtype.equalsIgnoreCase("1")) {
                    status = "Confirmed";

                } else if (batchtype.equalsIgnoreCase("2")) {
                    status = "Tentative";

                }
                String shareData = context.getResources().getString(R.string.Afcks) + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.course_name) + " : " + "*" + current.getCourse_name() + "*" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.start_date) + " : " + "*" + current.getStart_date() + "*" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Frequency) + " : " + current.getFrequency() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Status) + " : " + status + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.location) + " : " + current.getBranch_name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Fees) + " : " + current.getFees() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Trainer) + " : " + current.getFirst_name() + " " + current.getLast_name() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Timing) + " : " + current.getStart_time() + " " + current.getEnd_time() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Duration) + " : " + current.getDuration() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Notes) + " : " + current.getNotes() + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mobile) + " : " + "9762118718" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Mailid) + " : " + "mohammed.raza@afcks.com" + System.getProperty("line.separator")
                        + context.getResources().getString(R.string.Site) + " : " + "www.afckstechnologies.in" + System.getProperty("line.separator");
                Intent intent = new Intent(context, Activity_Full_Deatils_Batches.class);
                intent.putExtra("id", current.getId());
                intent.putExtra("start_date", current.getStart_date());
                intent.putExtra("start_time", current.getStart_time());
                intent.putExtra("trainer", current.getFirst_name() + " " + current.getLast_name());
                intent.putExtra("course_name", current.getCourse_name());
                intent.putExtra("location", current.getBranch_name());
                intent.putExtra("status", current.getBatchtype());
                intent.putExtra("notes", current.getNotes());
                intent.putExtra("isbooked", current.getIsbooked());
                intent.putExtra("frequency", current.getFrequency());
                intent.putExtra("fees", current.getFees());
                intent.putExtra("duration", current.getDuration());
                intent.putExtra("isstatus", current.getIsstatus());
                intent.putExtra("wa_invite_link", current.getWa_invite_link());
                intent.putExtra("shareData", shareData);
                ((Activity) context).finish();
                context.startActivity(intent);
            }
        });

    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView view_batch_no;
        TextView view_course_name;
        TextView view_frequency;
        TextView view_Location;
        ImageView whatsapp, msg, view_more;
        TextView view_start_date, view_Details;
        LinearLayout bookingstatus;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_batch_no = (TextView) itemView.findViewById(R.id.view_batch_no);
            view_course_name = (TextView) itemView.findViewById(R.id.view_course_name);
            view_frequency = (TextView) itemView.findViewById(R.id.view_frequency);
            view_Location = (TextView) itemView.findViewById(R.id.view_Location);
            whatsapp = (ImageView) itemView.findViewById(R.id.whatsapp);
            msg = (ImageView) itemView.findViewById(R.id.msg);
            view_more = (ImageView) itemView.findViewById(R.id.view_more);
            view_start_date = (TextView) itemView.findViewById(R.id.view_start_date);
            view_Details = (TextView) itemView.findViewById(R.id.view_Details);
            bookingstatus = (LinearLayout) itemView.findViewById(R.id.bookingstatus);
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ID = (Integer) view.getTag();
                    current = data.get(ID);
                    String text = "YOUR TEXT HERE";
                    String batchtype = current.getBatchtype().toString().trim();
                    String status = "";

                    if (batchtype.equalsIgnoreCase("1")) {
                        status = "Confirmed";

                    } else if (batchtype.equalsIgnoreCase("2")) {
                        status = "Tentative";

                    }

                    String shareData = context.getResources().getString(R.string.Afcksnew) + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.course_name) + " : " + current.getCourse_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.start_date) + " : " + current.getStart_date() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Frequency) + " : " + current.getFrequency() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Status) + " : " + status + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.location) + " : " + current.getBranch_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Fees) + " : " + current.getFees() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Trainer) + " : " + current.getFirst_name() + " " + current.getLast_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Timing) + " : " + current.getStart_time() + " " + current.getEnd_time() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Duration) + " : " + current.getDuration() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Notes) + " : " + current.getNotes() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Mobile) + " : " + "9762118718" + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Mailid) + " : " + "mohammed.raza@afcks.com" + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Site) + " : " + "www.afckstechnologies.in" + System.getProperty("line.separator");

                    Intent shareIntent = new Intent();
                    shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareData);
                    //shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/jpg");
                    context.startActivity(Intent.createChooser(shareIntent, "Share with"));

                }
            });
            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int ID = (Integer) view.getTag();
                    current = data.get(ID);

                    String batchtype = current.getBatchtype().toString().trim();
                    String status = "";

                    if (batchtype.equalsIgnoreCase("1")) {
                        status = "Confirmed";

                    } else if (batchtype.equalsIgnoreCase("2")) {
                        status = "Tentative";

                    }
                    String shareData = context.getResources().getString(R.string.Afcksnew) + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.course_name) + " : " + current.getCourse_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.start_date) + " : " + current.getStart_date() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Frequency) + " : " + current.getFrequency() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Status) + " : " + status + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.location) + " : " + current.getBranch_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Fees) + " : " + current.getFees() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Trainer) + " : " + current.getFirst_name() + " " + current.getLast_name() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Timing) + " : " + current.getStart_time() + " " + current.getEnd_time() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Duration) + " : " + current.getDuration() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Notes) + " : " + current.getNotes() + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Mobile) + " : " + "9762118718" + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Mailid) + " : " + "mohammed.raza@afcks.com" + System.getProperty("line.separator")
                            + context.getResources().getString(R.string.Site) + " : " + "www.afckstechnologies.in" + System.getProperty("line.separator");
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    sendIntent.putExtra("sms_body", shareData);
                    context.startActivity(sendIntent);


                }
            });


        }

    }


}
