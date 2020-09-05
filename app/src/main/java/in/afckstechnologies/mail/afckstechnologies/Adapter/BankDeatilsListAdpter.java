package in.afckstechnologies.mail.afckstechnologies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import in.afckstechnologies.mail.afckstechnologies.Activity.Activity_Full_Deatils_Batches;
import in.afckstechnologies.mail.afckstechnologies.Models.BankDeatilsDAO;
import in.afckstechnologies.mail.afckstechnologies.Models.NewBatchesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;


/**
 * Created by admin on 12/20/2016.
 */

public class BankDeatilsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<BankDeatilsDAO> data;
    BankDeatilsDAO current;
    int currentPos = 0;
    int id;
    String centerId;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;

    // create constructor to innitilize context and data sent from MainActivity
    public BankDeatilsListAdpter(Context context, List<BankDeatilsDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_bank_details, parent, false);
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

        myHolder.view_head.setText(current.getBank_name()+" ("+current.getBranch_name()+")");
        myHolder.view_head.setTag(position);


        myHolder.textUsername.setText(current.getAcc_holder_name());
        myHolder.textUsername.setTag(position);

        myHolder.textAccountno.setText(current.getAccount_no());
        myHolder.textAccountno.setTag(position);

        myHolder.textBank_name.setText(current.getBank_name());
        myHolder.textBank_name.setTag(position);

        myHolder.textBranchName.setText(current.getBranch_name());
        myHolder.textBranchName.setTag(position);

        myHolder.textAccounttype.setText(current.getAccount_type());
        myHolder.textAccounttype.setTag(position);

        myHolder.text_ifsc_code.setText(current.getIfsc_code());
        myHolder.text_ifsc_code.setTag(position);


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textUsername;
        TextView textAccountno;
        TextView textBank_name;
        TextView textBranchName;
        TextView textAccounttype;
        TextView text_ifsc_code;
        TextView view_head;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_head = (TextView) itemView.findViewById(R.id.view_head);
            textUsername = (TextView) itemView.findViewById(R.id.textUsername);
            textAccountno = (TextView) itemView.findViewById(R.id.textAccountno);
            textBank_name = (TextView) itemView.findViewById(R.id.textBank_name);
            textBranchName = (TextView) itemView.findViewById(R.id.textBranchName);
            textAccounttype = (TextView) itemView.findViewById(R.id.textAccounttype);
            text_ifsc_code = (TextView) itemView.findViewById(R.id.text_ifsc_code);
        }

    }


}
