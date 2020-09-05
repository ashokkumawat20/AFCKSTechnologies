package in.afckstechnologies.mail.afckstechnologies.Adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import in.afckstechnologies.mail.afckstechnologies.Models.FileNamesDAO;
import in.afckstechnologies.mail.afckstechnologies.R;


/**
 * Created by admin on 3/18/2017.
 */

public class FileNameDetailsListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<FileNamesDAO> data;
    FileNamesDAO current;


    // create constructor to innitilize context and data sent from MainActivity
    public FileNameDetailsListAdpter(Context context, List<FileNamesDAO> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_filenames_details, parent, false);
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
        String chapterNumber = current.getName();
        if(chapterNumber.contains(".")) {
            int index = chapterNumber.indexOf(".");
            myHolder.filename.setText(chapterNumber.substring(0, index));
            myHolder.filename.setTag(position);
        }
        else
        {
        }


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView filename;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            filename = (TextView) itemView.findViewById(R.id.filename);


        }

    }


}
