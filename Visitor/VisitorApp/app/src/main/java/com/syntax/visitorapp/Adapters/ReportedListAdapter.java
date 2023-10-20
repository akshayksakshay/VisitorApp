package com.syntax.visitorapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;

import java.util.List;

public class ReportedListAdapter extends RecyclerView.Adapter<ReportedListAdapter.ViewHolder> {
    private List<ShopPojo> shopPojos;
    Context context;
    public ReportedListAdapter(List<ShopPojo> shopPojos, Context context) {
        this.shopPojos = shopPojos;
        this.context = context;
    }
    @NonNull
    @Override
    public ReportedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_reported_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(shopPojos.get(position).getName());
        holder.location.setText(shopPojos.get(position).getLocation());
        holder.email.setText("\uD83D\uDCE7 "+shopPojos.get(position).getEmail());
        holder.phone.setText("\uD83D\uDCF1 "+shopPojos.get(position).getPhone());
        holder.address.setText(shopPojos.get(position).getAddress());
        holder.date.setText("\uD83D\uDCC5"+shopPojos.get(position).getDate());
        holder.time.setText("⏲"+shopPojos.get(position).getTime());

        holder.rname.setText("\uD83D\uDC77 "+shopPojos.get(position).getRp_name());
        holder.rdetails.setText(shopPojos.get(position).getRp_details());
        holder.raddress.setText(shopPojos.get(position).getRp_address());
//        holder.rdate.setText("✒ "+shopPojos.get(position).getRp_postdate());
        holder.rdate.setText("✒ "+ RetrofitClient.DateFormatter(shopPojos.get(position).getRp_postdate(), "dd MMM yyyy"));

    }

    @Override
    public int getItemCount() {
        return shopPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,location,email,address,phone,date,time,rname,raddress,rdetails,rdate;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.custom_reported_name);
            location=itemView.findViewById(R.id.custom_reported_location);
            email=itemView.findViewById(R.id.custom_reported_email);
            phone=itemView.findViewById(R.id.custom_reported_phone);
            address=itemView.findViewById(R.id.custom_reported_address);
            date=itemView.findViewById(R.id.custom_reported_date);
            time=itemView.findViewById(R.id.custom_reported_time);

            rname=itemView.findViewById(R.id.custom_reported_username);
            raddress=itemView.findViewById(R.id.custom_reported_useraddress);
            rdetails=itemView.findViewById(R.id.custom_reported_alert);
            rdate=itemView.findViewById(R.id.custom_reported_submitdate);
        }

    }


}
