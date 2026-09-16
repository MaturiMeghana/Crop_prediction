package com.example.cropprediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cropprediction.R
import com.example.cropprediction.ResultActivity

import com.example.cropprediction.api.RetrofitClient
import com.example.cropprediction.model.CropResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPredict = findViewById<Button>(R.id.btnPredict)

        val etN = findViewById<EditText>(R.id.etN)
        val etP = findViewById<EditText>(R.id.etP)
        val etK = findViewById<EditText>(R.id.etK)
        val etTemp = findViewById<EditText>(R.id.etTemp)
        val etHumidity = findViewById<EditText>(R.id.etHumidity)
        val etPH = findViewById<EditText>(R.id.etPH)
        val etRainfall = findViewById<EditText>(R.id.etRainfall)

        btnPredict.setOnClickListener {

            if (
                etN.text.isNullOrBlank() ||
                etP.text.isNullOrBlank() ||
                etK.text.isNullOrBlank() ||
                etTemp.text.isNullOrBlank() ||
                etHumidity.text.isNullOrBlank() ||
                etPH.text.isNullOrBlank() ||
                etRainfall.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    this,
                    "Please fill all input fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val input = mapOf(
                "N" to etN.text.toString().toFloat(),
                "P" to etP.text.toString().toFloat(),
                "K" to etK.text.toString().toFloat(),
                "Temperature" to etTemp.text.toString().toFloat(),
                "Humidity" to etHumidity.text.toString().toFloat(),
                "pH" to etPH.text.toString().toFloat(),
                "Rainfall" to etRainfall.text.toString().toFloat()
            )

            RetrofitClient.api.predictCrop(input)
                .enqueue(object : Callback<CropResponse> {

                    override fun onResponse(
                        call: Call<CropResponse>,
                        response: Response<CropResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {

                            val r = response.body()!!

                            val intent = Intent(
                                this@MainActivity,
                                ResultActivity::class.java
                            )
                            intent.putExtra("crop_english", r.Crop_English)
                            intent.putExtra(
                                "crop",
                                "${r.Crop_English} (${r.Crop_Telugu})"
                            )
                            intent.putExtra("season", r.Season)
                            intent.putExtra("soil_type", r.Soil_Type)
                            intent.putExtra("organic_fertilizer_en", r.Organic_Fertilizer_English)
                            intent.putExtra("organic_fertilizer_te", r.Organic_Fertilizer_Telugu)
                            intent.putExtra("chemical_fertilizer_en", r.Chemical_Fertilizer_English)
                            intent.putExtra("chemical_fertilizer_te", r.Chemical_Fertilizer_Telugu)
                            intent.putExtra("organic_pesticide_en", r.Organic_Pesticide_English)
                            intent.putExtra("organic_pesticide_te", r.Organic_Pesticide_Telugu)
                            intent.putExtra("chemical_pesticide_en", r.Chemical_Pesticide_English)
                            intent.putExtra("chemical_pesticide_te", r.Chemical_Pesticide_Telugu)

                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Server returned an error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<CropResponse>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to connect to server",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}
