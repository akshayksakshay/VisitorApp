package com.syntax.visitorapp.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Admin.AddNoticeFragment;
import com.syntax.visitorapp.Admin.ReportCovid;
import com.syntax.visitorapp.Admin.ShopListFragment;
import com.syntax.visitorapp.Admin.ShopRequestFragment;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.User.UserHome;
import com.syntax.visitorapp.WebService.RetrofitClient;

import java.util.List;
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
    private List<ShopPojo> shopPojos;
    Context context;
    String frgmnt;
    public ShopListAdapter(List<ShopPojo> shopPojos, Context context,String frgmnt) {
        this.shopPojos = shopPojos;
        this.context = context;
        this.frgmnt=frgmnt;
    }
    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_shoplist_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopListAdapter.ViewHolder holder, final int position) {
        holder.name.setText(shopPojos.get(position).getName());
        holder.location.setText(shopPojos.get(position).getLocation());
        holder.email.setText("\uD83D\uDCE7 "+shopPojos.get(position).getEmail());
        holder.phone.setText("\uD83D\uDCF1 "+shopPojos.get(position).getPhone());
        holder.address.setText(shopPojos.get(position).getAddress());
        holder.leftname.setText("\uD83C\uDFEA \n"+shopPojos.get(position).getName());
        int num =position+1;
        holder.num.setText(""+num);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frgmnt.trim().equals("request_list")){
                    showCustomDialog(holder.getAdapterPosition(),v);
                }else{
                    RetrofitClient.SID=shopPojos.get(holder.getAdapterPosition()).getSid();
                    showDialoge(holder.getAdapterPosition(),v);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,location,email,num,address,phone,leftname;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.custom_shoplist_name);
            location=itemView.findViewById(R.id.custom_shoplist_location);
            email=itemView.findViewById(R.id.custom_shoplist_email);
            num=itemView.findViewById(R.id.custom_shoplist_left_num);
            phone=itemView.findViewById(R.id.custom_shoplist_phone);
            address=itemView.findViewById(R.id.custom_shoplist_address);
            leftname=itemView.findViewById(R.id.custom_shoplist_left_shopname);
        }

    }

//    .........
protected void showCustomDialog(final int pos, final View view) {
    // TODO Auto-generated method stub
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.custom_admin_action);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    final FrameLayout approve=dialog.findViewById(R.id.cust_admin_action_approve);
    final FrameLayout reject = dialog.findViewById(R.id.cust_admin_action_reject);
    final TextView text = dialog.findViewById(R.id.cust_admin_action_text);
    text.setText(shopPojos.get(pos).getName()+" Request. Make action \nApprove 'Ok' & Reject shop 'No'?");
    final String sid=shopPojos.get(pos).getSid();
    approve.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             new ShopRequestFragment().AdminRequsetAction("approve",sid,pos);
            loadFragment(view,new ShopRequestFragment());
            dialog.dismiss();
        }
    });
    reject.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ShopRequestFragment().AdminRequsetAction("reject",sid,pos);
            loadFragment(view,new ShopRequestFragment());
            dialog.dismiss();
        }
    });
    dialog.show();
}

    void showDialoge(int pos,View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
//       builder.setTitle(Html.fromHtml("<font color='#ff0000'><h3>Confirm Shop Entry !</h3></font>",Html.FROM_HTML_MODE_LEGACY));
        builder.setTitle("Report Covid User Entry");
        String MSG="reporting a new covid positive user visited at "+shopPojos.get(pos).getName().trim()+" .this could be marked to Visiter Repoted List \nPlease Confirm shop entry";
        builder.setMessage(MSG);
        builder.setIcon(R.drawable.ic_marktolist);
        builder.setPositiveButton("Report",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadFragment(v,new ReportCovid());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



 public  void loadFragment(View view,Fragment myFra){
     AppCompatActivity activity = (AppCompatActivity) view.getContext();
     Fragment myFragment=myFra;
     activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();

 }

}
