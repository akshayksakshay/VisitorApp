package com.syntax.visitorapp.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


public class UserprofileFragment extends Fragment {
    EditText name,email,phone,address;
    TextView txtproname,txtphone;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_profile  , container, false);
        txtproname=root.findViewById(R.id.name);
        txtphone=root.findViewById(R.id.location);
        name=root.findViewById(R.id.profile_name);
        email=root.findViewById(R.id.profile_Email);
        phone=root.findViewById(R.id.profile_Phone);
        address=root.findViewById(R.id.profile_Address);
        Profile();
        return root;

    }
    public void Profile(){
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ShopPojo> call = serviceAPI.getProfileData("getProfileData",UID);
        call.enqueue(new Callback<ShopPojo>() {
            @Override
            public void onResponse(Call<ShopPojo> call, Response<ShopPojo> response) {
                ShopPojo data=response.body();
                Toast.makeText(getContext(), ""+data.getName(), Toast.LENGTH_SHORT).show();
                        txtproname.setText(data.getName());
                        txtphone.setText(data.getPhone());
                        name.setText(data.getName());
                        address.setText(data.getAddress());
                        phone.setText(data.getPhone());
                        email.setText(data.getEmail());
            }

            @Override
            public void onFailure(Call<ShopPojo> call, Throwable t) {
                Log.e("erre",t+"");
                Toast.makeText(getContext(), t+"", Toast.LENGTH_SHORT).show();
            }
        });

    }


}