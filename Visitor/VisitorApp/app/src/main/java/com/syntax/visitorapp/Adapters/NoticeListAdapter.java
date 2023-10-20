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

import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {
    private List<NoticePojo> noticePojos;
    Context context;
    public NoticeListAdapter(List<NoticePojo> noticePojos, Context context) {
        this.noticePojos = noticePojos;
        this.context = context;
    }
    @NonNull
    @Override
    public NoticeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_notice_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.subject.setText(noticePojos.get(position).getSubject());
        holder.details.setText(noticePojos.get(position).getDetails());
        holder.date.setText("\uD83D\uDCC5 "+noticePojos.get(position).getDate());
        holder.num.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return noticePojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject,details,date,num;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            subject=itemView.findViewById(R.id.custom_notificationlist_subject);
            details=itemView.findViewById(R.id.custom_notificationlist_details);
            date=itemView.findViewById(R.id.custom_notificationlist_date);
            num=itemView.findViewById(R.id.custom_notificationlist_num);
        }

    }


}
