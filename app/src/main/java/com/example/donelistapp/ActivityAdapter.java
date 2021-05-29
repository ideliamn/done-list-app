package com.example.donelistapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter
        .MyViewHolder> {

    static Context mContext;
    static ArrayList<ModelActivity> modelFeedArrayList = new ArrayList<>();

    public ActivityAdapter(Context context, ArrayList<ModelActivity>
                           modelFeedArrayList) {
        this.mContext = context;
        this.modelFeedArrayList = modelFeedArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity,
                parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelActivity modelActivity = modelFeedArrayList.get(position);
        holder.tv_activity.setText(modelActivity.getActivity());
    }

    @Override
    public int getItemCount() {
        return modelFeedArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_activity;
        Button btn_done;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity = (TextView)itemView.findViewById(R.id.tv_activity);
        }
    }

}
