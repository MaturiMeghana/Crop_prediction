package com.example.cropprediction.api

import com.example.cropprediction.model.CropResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("predict")
    fun predictCrop(
        @Body input: Map<String, Float>
    ): Call<CropResponse>
}
