package com.syntax.visitorapp.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syntax.visitorapp.Adapters.NoticeListAdapter;
import com.syntax.visitorapp.Adapters.VisitListAdapter;
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

public class ViewNoticeFragment extends Fragment {
    List<NoticePojo> noticePojoList;
    RecyclerView noticelist;
    NoticeListAdapter noticeListAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        noticelist = root.findViewById(R.id.user_notifications_list);
        noticelist.setLayoutManager(new LinearLayoutManager(getContext()));
        loadNotificationList();
        return root;
    }
    private void loadNotificationList() {
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<NoticePojo>> call = serviceAPI.getNotificationList("getNotificationList");
        call.enqueue(new Callback<List<NoticePojo>>() {
            @Override
            public void onResponse(Call<List<NoticePojo>> call, Response<List<NoticePojo>> response) {
                Log.e("response.body()", String.valueOf(response.body()));
                if (!response.body().equals("failed")) {
                    noticePojoList = response.body();
                    setListData();
                }else{
                    Log.e("Error", "failed");
                }
            }
            @Override
            public void onFailure(Call<List<NoticePojo>> call, Throwable t) {
                Log.e("onFailure", "errormag");
            }
        });
    }
    void setListData(){
        noticeListAdapter = new NoticeListAdapter(noticePojoList, getContext());
        noticeListAdapter.notifyDataSetChanged();
        noticelist.setAdapter(noticeListAdapter);
    }

}
