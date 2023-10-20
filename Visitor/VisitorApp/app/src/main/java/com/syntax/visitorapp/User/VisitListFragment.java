package com.syntax.visitorapp.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Adapters.ShopListAdapter;
import com.syntax.visitorapp.Adapters.VisitListAdapter;
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

public class VisitListFragment extends Fragment {
    List<ShopPojo> shopPojoList;
    RecyclerView visitlist;
    VisitListAdapter visitListAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_visit_list, container, false);
        visitlist = root.findViewById(R.id.user_visitlist_list);
        visitlist.setLayoutManager(new LinearLayoutManager(getContext()));
        loadVisitedShopsList();
        return root;
    }
    private void loadVisitedShopsList() {
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<ShopPojo>> call = serviceAPI.getVisitedList("UserGetVisitedList",UID);
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
        visitListAdapter = new VisitListAdapter(shopPojoList, getContext());
        visitListAdapter.notifyDataSetChanged();
        visitlist.setAdapter(visitListAdapter);
    }

}
