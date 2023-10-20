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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<ShopPojo> empPojos;
    Context context;
    public UsersAdapter(List<ShopPojo> empPojos, Context context) {
        this.empPojos = empPojos;
        this.context = context;
    }
    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_users_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText("\uD83D\uDC77  "+empPojos.get(position).getName());
        holder.phone.setText("\uD83D\uDCDE  "+empPojos.get(position).getPhone());
        holder.email.setText("\uD83D\uDCE7  "+empPojos.get(position).getEmail());
        holder.address.setText("\uD83D\uDCC3  "+empPojos.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return empPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,address,phone;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.custom_users_name);
            phone=itemView.findViewById(R.id.custom_users_phone);
            email=itemView.findViewById(R.id.custom_users_email);
            address=itemView.findViewById(R.id.custom_users_address);
        }

    }


}
