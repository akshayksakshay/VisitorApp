package com.syntax.visitorapp.WebService;

import com.syntax.visitorapp.Models.NoticePojo;
import com.syntax.visitorapp.Models.ShopPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    //http://localhost:8080/VisitorServer/Android/ServerController.jsp
    //http://localhost:8080/ica_web_app/Android/AndroidController.jsp

    @POST("ServerController.jsp")//login
     Call<String> loginUser(@Query("key") String key, @Query("username") String name, @Query("password") String password);

    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> registerUser(@Field("key") String key, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("email") String email,
                              @Field("password") String password);
    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> registerShop(@Field("key") String key, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("email") String email,
                              @Field("password") String password, @Field("location") String location);
    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> addNotice(@Field("key") String key, @Field("subject") String subject, @Field("details") String details);

    //Feedback (User)
    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> addfeedback(@Field("key") String key,@Field("uid") String uid, @Field("subject") String subject, @Field("details") String details);

    //getShopRequestList
    @POST("ServerController.jsp")
    Call<List<ShopPojo>> getShopRequestList(@Query("key") String key);

    //AdminRequsetAction
    @POST("ServerController.jsp")
    Call<String> AdminRequsetAction(@Query("key") String key,@Query("sid") String sid,@Query("action") String type);

    //getShopList
    @POST("ServerController.jsp")
    Call<List<ShopPojo>> getShopList(@Query("key") String key);

    //user getShopDataById
    @POST("ServerController.jsp")
    Call<ShopPojo> getShopDataById(@Query("key") String key,@Query("sid") String sid);

    //user markShopEntry
    @POST("ServerController.jsp")
    Call<String> markShopEntry(@Query("key") String key,@Query("uid") String uid,@Query("sid") String sid);

    //user getNotificationList
    @POST("ServerController.jsp")
    Call<List<NoticePojo>> getNotificationList(@Query("key") String key);

    //user getVisitedList
    @POST("ServerController.jsp")
    Call<List<ShopPojo>> getVisitedList(@Query("key") String key,@Query("uid") String uid);

    //user getProfileData
    @POST("ServerController.jsp")
    Call<ShopPojo> getProfileData(@Query("key") String key,@Query("uid") String uid);

    //user getEmployeesList
    @POST("ServerController.jsp")
    Call<List<ShopPojo>> getEmployeesList(@Query("key") String key,@Query("sid") String sid);

    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> addEmployee(@Field("key") String key,@Field("sid") String sid, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("email") String email);

    //user reportCovid
    @FormUrlEncoded
    @POST("ServerController.jsp")
    Call<String> reportCovid(@Field("key") String key,@Field("sid") String sid,@Field("name") String name,@Field("address") String address,@Field("details") String details,@Field("date") String date);

    //user getVisitedList
    @POST("ServerController.jsp")
    Call<List<ShopPojo>> getReportedList(@Query("key") String key,@Query("uid") String uid);

    //user getFeedbacks
    @POST("ServerController.jsp")
    Call<List<NoticePojo>> getFeedbacks(@Query("key") String key);

}
