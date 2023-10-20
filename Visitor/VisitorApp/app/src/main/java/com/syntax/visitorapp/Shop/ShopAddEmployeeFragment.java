package com.syntax.visitorapp.Shop;

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

import com.syntax.visitorapp.Login;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.User.UserRegitration;
import com.syntax.visitorapp.User.ViewNoticeFragment;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ShopAddEmployeeFragment extends Fragment {
    EditText name,phone,email,address;
    Button btnadd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_shop_add_employee, container, false);
        name=root.findViewById(R.id.shop_add_employee_name);
        phone=root.findViewById(R.id.shop_add_employee_phone);
        address=root.findViewById(R.id.shop_add_employee_address);
        email=root.findViewById(R.id.shop_add_employee_email);
        btnadd=root.findViewById(R.id.shop_add_employee_add_btn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.requestFocus();
                    name.setError("enter name");
                } else if (phone.getText().toString().isEmpty()) {
                    phone.requestFocus();
                    phone.setError("enter phone");
                } else if (address.getText().toString().isEmpty()) {
                    address.requestFocus();
                    address.setError("enter address");
                }else if (email.getText().toString().isEmpty()) {
                    email.requestFocus();
                    email.setError("enter email");
                }
                else {
                    AddEmployee();
                }
            }
        });

        return  root;
    }

    private void AddEmployee(){
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.addEmployee("AddEmployee",UID,name.getText().toString(),phone.getText().toString(),address.getText().toString(),email.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (!response.body().equals("failed")) {
                        Toast.makeText(getContext(), " Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), ShopHome.class));

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
