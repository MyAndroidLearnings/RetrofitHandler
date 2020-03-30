package com.sysaxiom.apihandlerretrofit.handler

import com.sysaxiom.apihandlerretrofit.utils.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @POST(ApiConstants.postApi)
    @FormUrlEncoded
    fun samplePostCall(@Field(ApiConstants.postApiJsonName) mobile: String) : Call<ResponseBody>

    @GET(ApiConstants.getApi)
    fun sampleGetCall() : Call<ResponseBody>

    companion object{
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi{
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(ApiConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}
