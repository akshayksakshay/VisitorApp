package com.syntax.visitorapp.Admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.syntax.visitorapp.R;
import com.syntax.visitorapp.Shop.ShopHome;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ReportCovid extends Fragment {
    EditText name,address,details,vititeddate;
    Button btnadd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_report_covid, container, false);
        name=root.findViewById(R.id.report_covid_name);
        address=root.findViewById(R.id.report_covid_address);
        details=root.findViewById(R.id.report_covid_details);
        vititeddate=root.findViewById(R.id.report_covid_date);
        btnadd=root.findViewById(R.id.report_covid_btnadd);
        vititeddate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popDatePicker();
                return false;
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("date",vititeddate.getText().toString());
                if (vititeddate.getText().toString().isEmpty()) {
                    vititeddate.requestFocus();
                    vititeddate.setError("enter date");
                } else if (name.getText().toString().isEmpty()) {
                    name.requestFocus();
                    name.setError("enter name");
                } else if (address.getText().toString().isEmpty()) {
                    address.requestFocus();
                    address.setError("enter address");
                }else if (details.getText().toString().isEmpty()) {
                    details.requestFocus();
                    details.setError("enter details");
                }
                else {
                    reportCovid();
                }
            }
        });
        return  root;
    }

  void  popDatePicker(){
      final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                vititeddate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }

        };
      new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

  }
    private void reportCovid(){
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.reportCovid("reportCovid",RetrofitClient.SID,name.getText().toString().trim(),address.getText().toString().trim(),details.getText().toString().trim(),vititeddate.getText().toString().trim());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (!response.body().equals("failed")) {
                        Toast.makeText(getContext(), " Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), AdminHome.class));

                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Throwable ",""+t);
                Toast.makeText(getContext(), "on fail"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}