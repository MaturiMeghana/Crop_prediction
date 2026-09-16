package com.example.cropprediction.model

data class CropResponse(
    val Crop_English: String,
    val Crop_Telugu: String,
    val Season: String,
    val Soil_Type: String,

    val Organic_Fertilizer_English: String,
    val Organic_Fertilizer_Telugu: String,

    val Chemical_Fertilizer_English: String,
    val Chemical_Fertilizer_Telugu: String,

    val Organic_Pesticide_English: String,
    val Organic_Pesticide_Telugu: String,

    val Chemical_Pesticide_English: String,
    val Chemical_Pesticide_Telugu: String
)
