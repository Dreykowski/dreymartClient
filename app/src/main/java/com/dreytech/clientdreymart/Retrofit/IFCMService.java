package com.dreytech.clientdreymart.Retrofit;

import com.dreytech.clientdreymart.Model.DataMessage;
import com.dreytech.clientdreymart.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAZMNzG48:APA91bG-eIFXDSKAPB3gf4VW-dFjHprhh0xHxlzGF2s2ilxQ9d_2AgLplS8K6JWz5MsT34ldqF4qhk1sGZDUvWLZpNG7W8luDF3DCcvn35jFwD9-NV5Rpk_eQgwJ5trnGS7U-5pH4IGT"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
