package com.example.demoapiapplication

import com.example.demoapiapplication.model.Constants
import com.example.demoapiapplication.model.MainResponse
import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET(Constants.SUCCESS)
    fun getSuccessResponse(): Call<MainResponse>

    @GET(Constants.FAILURE)
    fun getFailureResponse(): Call<MainResponse>

}