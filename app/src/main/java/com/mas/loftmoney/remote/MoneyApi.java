package com.mas.loftmoney.remote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyApi {

    @GET("./items")
    Single<List<MoneyRemoteItem>> getMoneyItems(@Query("type") String type, @Query("auth-token") String token);


    @POST("./items/add")
    @FormUrlEncoded
    Completable addMoney(@Field("price") double price,
                         @Field("name") String name,
                         @Field("type") String type,
                         @Field("auth-token") String token);

    @POST("./items/remove")
    @FormUrlEncoded
    Completable deleteMoney(@Field("id") int id,
                            @Field("auth-token") String token);

    @GET("./balance")
    Single<BalanceResponse> getBalance(@Query("auth-token") String token);
}
