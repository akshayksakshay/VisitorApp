package com.syntax.visitorapp.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.syntax.visitorapp.R;
import com.syntax.visitorapp.Shop.ShopHome;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


public class AddFeedbackFragment extends Fragment {
    EditText subject,details;
    Button btnadd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_feedback, container, false);
        subject = root.findViewById(R.id.addnotice_subject);
        details = root.findViewById(R.id.addnotice_details);
        btnadd = root.findViewById(R.id.addnotice_btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject.getText().toString().isEmpty()) {
                    subject.requestFocus();
                    subject.setError("enter Subject");
                } else if (details.getText().toString().isEmpty()) {
                    details.requestFocus();
                    details.setError("enter Details");
                } else {
                    feedbackAction();
                }
            }
        });
        return root;
    }

    private void feedbackAction() {
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.addfeedback("add_feedback",UID,subject.getText().toString(),details.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.body().trim().equals("failed")) {
                    Log.e("response.body()",response.body());
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), UserHome.class));
                } else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}