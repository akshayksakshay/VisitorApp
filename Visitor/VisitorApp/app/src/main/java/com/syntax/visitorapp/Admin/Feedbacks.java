package com.syntax.visitorapp.Admin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syntax.visitorapp.Adapters.FeedbackAdapter;
import com.syntax.visitorapp.Adapters.ReportedListAdapter;
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

public class Feedbacks extends Fragment {
    List<NoticePojo> feedbackPojoList;
    RecyclerView feedbacklist;
    FeedbackAdapter feedbackAdapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_feedbacks, container, false);

        feedbacklist = root.findViewById(R.id.feedback_list);
        feedbacklist.setLayoutManager(new LinearLayoutManager(getContext()));
        loadFeedBackList();
        return root;
    }


    private void loadFeedBackList() {
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<List<NoticePojo>> call = serviceAPI.getFeedbacks("getFeedbacks");
        call.enqueue(new Callback<List<NoticePojo>>() {
            @Override
            public void onResponse(Call<List<NoticePojo>> call, Response<List<NoticePojo>> response) {
                if (!response.body().equals("failed")) {
                    feedbackPojoList = response.body();
                    setListData();
                }else{
                    Log.e("Error", "failed");
                }
            }

            @Override
            public void onFailure(Call<List<NoticePojo>> call, Throwable t) {

            }
        });
    }
    void setListData(){
        feedbackAdapter = new FeedbackAdapter(feedbackPojoList, getContext());
        feedbackAdapter.notifyDataSetChanged();
        feedbacklist.setAdapter(feedbackAdapter);
    }

}
