package com.example.demoapiapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.demoapiapplication.model.Album
import com.example.demoapiapplication.model.Constants
import com.example.demoapiapplication.model.MainResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val ran = (0..10).random()
    public var albumList: MutableList<Album> = ArrayList<Album>()

    var log: HttpLoggingInterceptor = HttpLoggingInterceptor()

    val client = OkHttpClient.Builder()
        .callTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(log)
        .build()

    var retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //apiCalling()
    }

    private fun apiCalling() {
        var aPI: API = retrofit.create(API::class.java)
        var call: Call<MainResponse> =
            if (ran < 3) {
                aPI.getFailureResponse()
            } else {
                aPI.getSuccessResponse()
            }
        log.setLevel(HttpLoggingInterceptor.Level.BODY)
        call.enqueue(object : Callback<MainResponse> {
            override fun onFailure(call: Call<MainResponse>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Failure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MainResponse>?,
                response: Response<MainResponse>?
            ) {
                if (response?.message().equals("Success")) {
                    albumList.addAll(response!!.body().albums!!.toMutableList())
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                    /*val bundle = Bundle()
                    bundle.putSerializable("album", response.body())
                    var frag: FirstFragment = FirstFragment()
                    frag.arguments = bundle*/
                }
            }
        })
    }
}
