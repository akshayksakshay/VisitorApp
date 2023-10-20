package com.syntax.visitorapp.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.syntax.visitorapp.Models.ShopPojo;
import com.syntax.visitorapp.R;
import com.syntax.visitorapp.WebService.RetrofitClient;
import com.syntax.visitorapp.WebService.ServiceAPI;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ScanQRFragment extends Fragment {
    ImageView scanimg;
    private IntentIntegrator qrScan;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_scanqr, container, false);
        scanimg = root.findViewById(R.id.scan_img);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        scanimg.startAnimation(animation);
        qrScan = IntentIntegrator.forSupportFragment(ScanQRFragment.this);
        qrScan.setOrientationLocked(true);
        scanimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    qrScan.initiateScan();
                }catch (Exception e){
                    Log.e("initiateScan",""+e);
                }
            }
        });

        return root;
    }
    private void getShopDataById(String ID) {
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ShopPojo> call = serviceAPI.getShopDataById("getShopDataById",ID);
        call.enqueue(new Callback<ShopPojo>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ShopPojo> call, Response<ShopPojo> response) {
                Log.e("response.body()",""+response);
                ShopPojo shop =response.body();
                showDialoge(shop);
            }

            @Override
            public void onFailure(Call<ShopPojo> call, Throwable t) {

            }
        });
    }

    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String iddata[]=result.getContents().trim().split(":");
                getShopDataById(iddata[1]);
//                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    // name location  phone email
    void showDialoge(ShopPojo shop){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
//       builder.setTitle(Html.fromHtml("<font color='#ff0000'><h3>Confirm Shop Entry !</h3></font>",Html.FROM_HTML_MODE_LEGACY));
        builder.setTitle("'"+shop.getName().trim()+"' Confirm Entry");
        String MSG="\uD83C\uDFE6 "+shop.getName().trim()+"\n\uD83D\uDCDE "+shop.getPhone().trim()+"\nShop Entry could be marked to Visiter List \nPlease Confirm shop entry";
        builder.setMessage(MSG);
        builder.setIcon(R.drawable.qrimg);
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markShopEntry(shop.getSid());
            }
        });
        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void markShopEntry(String SID) {
        SharedPreferences prefs = getActivity().getSharedPreferences("SharedData", MODE_PRIVATE);
        String UID = prefs.getString("logid","noid");
        Retrofit retrofit = new RetrofitClient().getRetrofit();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<String> call = serviceAPI.markShopEntry("markShopEntry",UID,SID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.body().trim().equals("failed")) {
                    Log.e("response.body()",response.body());
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
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
