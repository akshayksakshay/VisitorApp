package com.syntax.visitorapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Models.NoticePojo;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<NoticePojo> feedbacks;
    Context context;
    public FeedbackAdapter(List<NoticePojo> feedbacks, Context context) {
        this.feedbacks = feedbacks;
        this.context = context;
    }
    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_feedback_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(feedbacks.get(position).getName());
        holder.sub.setText(feedbacks.get(position).getSubject());
        holder.details.setText(feedbacks.get(position).getDetails());
        holder.date.setText(RetrofitClient.DateFormatter(feedbacks.get(position).getDate(), "dd MMM yyyy"));
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,sub,details,date;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.custom_feedback_name);
            sub=itemView.findViewById(R.id.custom_feedback_subject);
            details=itemView.findViewById(R.id.custom_feedback_details);
            date=itemView.findViewById(R.id.custom_feedback_submitdate);
        }

    }


}
