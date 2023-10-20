package com.syntax.visitorapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNoticeFragment extends Fragment {
    EditText subject,details;
    Button btnadd;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_notice, container, false);
        subject=root.findViewById(R.id.addnotice_subject);
        details=root.findViewById(R.id.addnotice_details);
        btnadd=root.findViewById(R.id.addnotice_btnadd);
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
                    AddNoticeAction();
                }
            }
        });
        return root;
    }

    private void AddNoticeAction() {
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.addNotice("add_notice",subject.getText().toString(),details.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (response.body().trim().equals("success")) {
                        Log.e("response.body()",response.body());
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),AdminHome.class));
                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}