# Api Handler Retrofit

## Introduction:

This project is created in the intention to understand the Api hanlders for android using retrofit library and make it as 
readily usable component to integrate it with any projects.

## Installation:

Step 1: Add the retrofit and gson library dependency to the app build.gradle

    implementation 'com.squareup.retrofit2:retrofit:2.6.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.0'

Step 2: Add the internet and network permission in the AndroidManifest.xml file.

        <uses-permission android:name="android.permission.INTERNET"/>
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Step 3: If you are using http protocal for api's, then add the below configuration in the android studio project.

   
       Step 3.1: Add the below configuration inside the application tag in AndroidManifest.xml file.

            android:networkSecurityConfig="@xml/network_security_config"
            android:usesCleartextTraffic="true"

   
       Step 3.2:  Add the below content inside network_security_config.xml file and put it inside a res/xml path.

            <?xml version="1.0" encoding="utf-8"?>
            <network-security-config>
                <base-config cleartextTrafficPermitted="true">
                    <trust-anchors>
                        <certificates src="system" />
                    </trust-anchors>
                </base-config>
            </network-security-config>

## Handler Part:

A static or companion object is implemented to invoke without object instantiation.

**(1) Invoke** - Retrofit base setup, here we register the base url, NetworkConnectionInterceptor and GsonConverterFactory

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

**(2) NetworkInterceptor** - NetworkInterceptor is a barrier which allows the api to retrive/fetch data only if internet connection
                             is there in android devices.


    class NetworkConnectionInterceptor(context: Context) : Interceptor {
        private val applicationContext = context.applicationContext
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!isInternetAvailable())
                throw NoInternetException("Make sure you have an active data connection")
            return chain.proceed(chain.request())
        }
        private fun isInternetAvailable(): Boolean {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.activeNetworkInfo.also {
                return it != null && it.isConnected
            }
        }
    }
    
**(3) Get** - to get data from server through get api call.

    @GET(ApiConstants.getApi)
    fun sampleGetCall() : Call<ResponseBody>

**(4) Post** - to post data to server through post api call.

    @POST(ApiConstants.postApi)
    @FormUrlEncoded
    fun samplePostCall(@Field(ApiConstants.postApiJsonName) mobile: String) : Call<ResponseBody>

## Usage / Example:

Sample get and post call is given as regions where you can try/implement by invoking each function and seeing it in Console.

