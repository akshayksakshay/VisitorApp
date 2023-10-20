package com.syntax.visitorapp.Shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.syntax.visitorapp.Login;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopRegister extends AppCompatActivity {
    EditText name,phone,email,password,address,location;
    ImageView btnreg;
    TextView log;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        img=findViewById(R.id.shop_img);
        name=findViewById(R.id.shop_name);
        phone=findViewById(R.id.shop_phone);
        address=findViewById(R.id.shop_address);
        email=findViewById(R.id.shop_email);
        location=findViewById(R.id.shop_location);
        password=findViewById(R.id.shop_password);
        btnreg=findViewById(R.id.shop_btnreg);
        log=findViewById(R.id.shop_login);
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
                }else if (location.getText().toString().isEmpty()) {
                    location.requestFocus();
                    location.setError("enter location");
                }else if(email.getText().toString().trim().isEmpty() || !email.getText().toString().trim().matches("[a-z0-9._-]+@[a-z]+\\.+[a-z]+")){                    email.requestFocus();
                    email.setError("enter email");
                }else if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("enter password");
                }else {
                    regAction();
                }
            }
        });
    }

    private void regAction(){
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.registerShop("shop_reg",name.getText().toString(),phone.getText().toString(),address.getText().toString(),email.getText().toString(),password.getText().toString(),location.getText().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if (response.body().trim().equals("already")) {
                        Toast.makeText(getApplicationContext(), "Email ID Already Exist", Toast.LENGTH_SHORT).show();
                    }else if (response.body().trim().equals("failed")) {
                        Toast.makeText(ShopRegister.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.e("resposnce", response.body());
                        Toast.makeText(ShopRegister.this, "Success", Toast.LENGTH_SHORT).show();
                        genarateQR(response.body().trim());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Throwable ",""+t);
                Toast.makeText(ShopRegister.this, "on fail"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void genarateQR(String sid)  {

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = null;
            bitmap = barcodeEncoder.encodeBitmap("VISISTOR SHOP NO : "+sid.trim(), BarcodeFormat.QR_CODE, 400, 400);
            img.setImageBitmap(bitmap);
//            String savePath = Environment.getExternalStorageDirectory().getPath() + "/VisitorApp/";
          String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/VisitorApp/").toString();
            save(savePath,"visitor_shop"+sid,bitmap,".png");
            startActivity(new Intent(getApplicationContext(),Login.class));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    boolean save(String saveLocation, String imageName, Bitmap bitmap, String imageFormat) throws WriterException {

        boolean success = false;
        String imageDetail = saveLocation + imageName + imageFormat;
        Log.e("path",imageDetail);
        FileOutputStream outStream;
        File file = new File(saveLocation);
        if (!file.exists()) {
            Log.v("QRGSaver", "No Folder Exists");
            file.mkdir();
        } else {
            Log.v("QRGSaver", "Folder Exists");
        }
        try {
            outStream = new FileOutputStream(imageDetail);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            Log.d("QRGSaver", e.toString());
        }

        return success;
    }


}
