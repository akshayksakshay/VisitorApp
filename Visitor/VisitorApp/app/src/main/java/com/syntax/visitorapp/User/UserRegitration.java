package com.syntax.visitorapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.syntax.visitorapp.Login;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRegitration extends AppCompatActivity {
    EditText name,phone,email,password,address,confirmpassword;
    ImageView btnreg;
    TextView log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_user_regitration);
        name=findViewById(R.id.register_name);
        phone=findViewById(R.id.register_phone);
        address=findViewById(R.id.register_address);
        email=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);
        confirmpassword=findViewById(R.id.et_confirm_password);
        btnreg=findViewById(R.id.register_btnreg);
        log=findViewById(R.id.register_login);
        log.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                return false;
            }
        });


        btnreg.setOnClickListener(new View.OnClickListener() {
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
//                }else if (email.getText().toString().isEmpty()) {
                }else if(email.getText().toString().trim().isEmpty() || !email.getText().toString().trim().matches("[a-z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    email.requestFocus();
                    email.setError("enter email");
                }else if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("enter password");
                }else if(confirmpassword.getText().toString().isEmpty()) {
                    confirmpassword.requestFocus();
                    confirmpassword.setError("enter confirm password..");
                } else  if (!confirmpassword.getText().toString().trim().equals(password.getText().toString().trim())){
                    confirmpassword.requestFocus();
                    confirmpassword.setError("password does not match");

                }
                else {
                    regAction();
                }
            }
        });
    }

    private void regAction(){
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.registerUser("user_reg",name.getText().toString(),phone.getText().toString(),address.getText().toString(),email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (response.body().trim().equals("success")) {
                        Toast.makeText(UserRegitration.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }else if (response.body().equals("already")) {
                        Toast.makeText(UserRegitration.this, "Email ID Already Exist", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(UserRegitration.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Throwable ",""+t);
                Toast.makeText(UserRegitration.this, "on fail"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
