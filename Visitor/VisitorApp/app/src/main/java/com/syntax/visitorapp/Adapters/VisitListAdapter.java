package com.syntax.visitorapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;

import java.util.List;

public class VisitListAdapter extends RecyclerView.Adapter<VisitListAdapter.ViewHolder> {
    private List<ShopPojo> shopPojos;
    Context context;
    public VisitListAdapter(List<ShopPojo> shopPojos, Context context) {
        this.shopPojos = shopPojos;
        this.context = context;
    }
    @NonNull
    @Override
    public VisitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_visitlist_row_item,parent,false);
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

    }

    @Override
    public int getItemCount() {
        return shopPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,location,email,address,phone,date,time;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.custom_visitlist_name);
            location=itemView.findViewById(R.id.custom_visitlist_location);
            email=itemView.findViewById(R.id.custom_visitlist_email);
            phone=itemView.findViewById(R.id.custom_visitlist_phone);
            address=itemView.findViewById(R.id.custom_visitlist_address);
            date=itemView.findViewById(R.id.custom_visitlist_date);
            time=itemView.findViewById(R.id.custom_visitlist_time);
        }

    }


}
