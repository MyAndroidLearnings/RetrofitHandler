package com.sysaxiom.apihandlerretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sysaxiom.apihandlerretrofit.handler.MyApi
import com.sysaxiom.apihandlerretrofit.handler.NetworkConnectionInterceptor
import com.sysaxiom.apihandlerretrofit.utils.ApiConstants
import com.sysaxiom.apihandlerretrofit.utils.LogConstants
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleGetCall()
        samplePostCall(ApiConstants.postApiJsonValue)
    }

    //region Sample Get request
    fun sampleGetCall(){

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)

        var call : Call<ResponseBody> = api.sampleGetCall()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(LogConstants.TAG, t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(LogConstants.TAG, JSONObject(response.body()?.string()).toString())
            }
        })

    } //endregion

    //region Sample post request
    fun samplePostCall(jsonValue: String) {

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)

        var call : Call<ResponseBody> = api.samplePostCall(jsonValue)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(LogConstants.TAG, t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(LogConstants.TAG, JSONObject(response.body()?.string()).toString())
            }
        })

    }//endregion

}
