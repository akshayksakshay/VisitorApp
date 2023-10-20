package com.syntax.visitorapp.Shop;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syntax.visitorapp.Adapters.NoticeListAdapter;
import com.syntax.visitorapp.Adapters.UsersAdapter;
import com.syntax.visitorapp.Models.NoticePojo;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ShopViewEmployeesFragment extends Fragment {
    List<ShopPojo> userpojo;
    RecyclerView emplist;
    UsersAdapter noticeListAdapter;


                             @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_shop_view_employees, container, false);
           emplist = root.findViewById(R.id.shop_employee_list);
           emplist.setLayoutManager(new LinearLayoutManager(getContext()));
          loadEmployees();
          return root;

    }

    private void loadEmployees() {
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<ShopPojo> >call = serviceAPI.getEmployeesList("getEmployeesList",UID);
        call.enqueue(new Callback<List<ShopPojo>>() {
            @Override
            public void onResponse(Call<List<ShopPojo>> call, Response<List<ShopPojo>> response) {
                Log.e("response.body()", String.valueOf(response.body()));
                if (!response.body().equals("failed")) {
                    userpojo = response.body();
                    setListData();
                }else{
                    Log.e("Error", "failed");
                }
            }

            @Override
            public void onFailure(Call<List<ShopPojo>> call, Throwable t) {

            }
        });
    }
    void setListData(){
        noticeListAdapter = new UsersAdapter(userpojo, getContext());
        noticeListAdapter.notifyDataSetChanged();
        emplist.setAdapter(noticeListAdapter);
    }


}
