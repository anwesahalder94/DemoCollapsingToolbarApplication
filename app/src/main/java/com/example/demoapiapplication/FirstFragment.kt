package com.example.demoapiapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapiapplication.adapter.AlbumAdapter
import com.example.demoapiapplication.model.Album
import com.example.demoapiapplication.model.Constants
import com.example.demoapiapplication.model.MainResponse
import kotlinx.android.synthetic.main.fragment_first.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FirstFragment: Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var albumAdapter: AlbumAdapter
    lateinit var navController: NavController
    val ran = (0..10).random()

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        init()
        apiCalling()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list?.setHasFixedSize(true)
        rv_list?.layoutManager = layoutManager
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
                Toast.makeText(activity, "Failure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MainResponse>?,
                response: Response<MainResponse>?
            ) {
                if (response?.body()?.message.equals("Success")) {
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    albumAdapter = AlbumAdapter(
                        response?.body()?.albums!!,
                        activity!!,
                        navController
                    )
                    rv_list.adapter = albumAdapter

                }else if(response?.body()?.status?.equals(0)!!){
                    Toast.makeText(activity, "Please try again later", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}