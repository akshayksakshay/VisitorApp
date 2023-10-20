package com.syntax.visitorapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Adapters.ShopListAdapter;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.User.UserHome;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopRequestFragment extends Fragment{
    List<ShopPojo> shopPojoList;
    RecyclerView shoprequest_list;
    ShopListAdapter shopListAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop_request, container, false);
        shoprequest_list = root.findViewById(R.id.shoprequest_list);
        shoprequest_list.setLayoutManager(new LinearLayoutManager(getContext()));
        loadRequestData();
        return root;
    }

    void loadRequestData(){
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<ShopPojo>> call = serviceAPI.getShopRequestList("ShopRequestList");
        call.enqueue(new Callback<List<ShopPojo>>() {
            @Override
            public void onResponse(Call<List<ShopPojo>> call, Response<List<ShopPojo>> response) {
                Log.e("response.body()", String.valueOf(response.body()));
                if (!response.body().equals("failed")) {
                    shopPojoList = response.body();
                    setListData();
                }else{
                    Log.e("Error", "failed");
                }
            }
            @Override
            public void onFailure(Call<List<ShopPojo>> call, Throwable t) {
                Log.e("onFailure", "errormag");
            }
        });

    }
   void setListData(){
        shopListAdapter = new ShopListAdapter(shopPojoList, getContext(),"request_list");
        shopListAdapter.notifyDataSetChanged();
        shoprequest_list.setAdapter(shopListAdapter);
    }
    public void AdminRequsetAction(String ACTION, String SID, final int position){
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.AdminRequsetAction("AdminRequsetAction",SID,ACTION);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("response.body()", String.valueOf(response.body()));
                if (!response.body().equals("failed")) {

//                    loadRequestData();
//                    shopPojoList.remove(position);
//                    shoprequest_list.removeViewAt(position);
//                    shopListAdapter.notifyItemRemoved(position);
//                    shopListAdapter.notifyItemRangeChanged(position, shopPojoList.size());

                }else{
                    Log.e("Error", "failed");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", "errormag"+t);
            }
        });


    }


}
