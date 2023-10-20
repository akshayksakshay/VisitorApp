package com.syntax.visitorapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.syntax.visitorapp.Admin.AdminHome;
import com.syntax.visitorapp.Shop.ShopHome;
import com.syntax.visitorapp.Shop.ShopRegister;
import com.syntax.visitorapp.User.UserHome;
import com.syntax.visitorapp.User.UserRegitration;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText username, password;
    ImageView btnlog;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,

        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_pass);
        btnlog = findViewById(R.id.login_btn);
        reg = findViewById(R.id.login_reg);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NAME = username.getText().toString();
                if (NAME.isEmpty()) {

                }
                if (username.getText().toString().isEmpty()) {
                    username.requestFocus();
                    username.setError("enter username");
                } else if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("enter password");
                } else {
                    loginAction();
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
    }

    protected void showCustomDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_reg_option);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //final String pid=Pid;

        final Button user = dialog.findViewById(R.id.cus_reg_user);
        final Button bus = dialog.findViewById(R.id.cus_reg_bus);


        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(getApplicationContext(), UserRegitration.class));
                dialog.cancel();
                return false;
            }
        });
        bus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(getApplicationContext(), ShopRegister.class));
                dialog.cancel();
                return false;
            }
        });
        dialog.show();
    }

    void logAction() {
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> ob = serviceAPI.loginUser("login", username.getText().toString(), password.getText().toString());
        ob.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void loginAction() {
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.loginUser("signin", username.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (!response.body().trim().equals("failed")) {
                        Log.e("response.body()", response.body());
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("SharedData", MODE_PRIVATE).edit();
                        String arr[] = response.body().trim().split("&");

                        if (arr[0].trim().equals("admin")) {
                            editor.putString("type", arr[0]);
                            editor.putString("logid", arr[1]);
                            editor.commit();
                            startActivity(new Intent(Login.this, AdminHome.class));
                        } else if (arr[0].trim().equals("user")) {
                            editor.putString("type", arr[0]);
                            editor.putString("logid", arr[1]);
                            editor.commit();
                            startActivity(new Intent(Login.this, UserHome.class));
                        } else if (arr[0].trim().equals("shop")) {
                            editor.putString("type", arr[0]);
                            editor.putString("logid", arr[1]);
                            editor.commit();
                            startActivity(new Intent(Login.this, ShopHome.class));
                        }

                    } else {
                        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Login.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}