package com.dreytech.clientdreymart.Retrofit;

import com.dreytech.clientdreymart.Model.Banner;
import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.Model.Category;
import com.dreytech.clientdreymart.Model.CheckUserResponse;
import com.dreytech.clientdreymart.Model.Order;
import com.dreytech.clientdreymart.Model.OrderResult;
import com.dreytech.clientdreymart.Model.Store;
import com.dreytech.clientdreymart.Model.Token;
import com.dreytech.clientdreymart.Model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IDreyMarketAPI {

    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birthdate") String birthdate );

    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone );

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("getbarang.php")
    Observable<List<Barang>> getBarang(@Field("menuid") String menuID );

    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone, @Part MultipartBody.Part file);

    @GET("getallbarangs.php")
    Observable<List<Barang>> getAllBarangs();

    @FormUrlEncoded
    @POST("submitorder.php")
    Call<OrderResult> submitOrder(@Field("price") float orderPrice,
                                  @Field("orderDetail")String orderDetail,
                                  @Field("comment")String comment,
                                  @Field("address")String address,
                                  @Field("phone")String phone,
                                  @Field("paymentMethod")String paymentMethod);

    @FormUrlEncoded
    @POST("braintree/checkout.php")
    Call<String> payment(@Field("nonce") String nonce,
                             @Field("amount")String amount);

    @FormUrlEncoded
    @POST("getorder.php")
    Observable<List<Order>> getOrder(@Field("userPhone") String userPhone,
                                        @Field("status") String status);

    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token,
                             @Field("isServerToken") String isServerToken);

    @FormUrlEncoded
    @POST("cancelorder.php")
    Call<String> cancelOrder(@Field("orderId") String orderId,
                             @Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("getnearbystore.php")
    Observable<List<Store>> getNearbyStore (@Field("lat") String lat,
                                            @Field("lng") String lng);
    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("isServerToken") String isServerToken);



}
