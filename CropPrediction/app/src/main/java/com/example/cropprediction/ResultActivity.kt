package com.example.cropprediction

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ResultActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var speechText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        val ivCrop = findViewById<ImageView>(R.id.ivCrop)
        val tvCrop = findViewById<TextView>(R.id.tvCrop)
        val tvSeasonSoil = findViewById<TextView>(R.id.tvSeasonSoil)
        val tvOrganicFertilizer = findViewById<TextView>(R.id.tvOrganicFertilizer)
        val tvChemicalFertilizer = findViewById<TextView>(R.id.tvChemicalFertilizer)
        val tvOrganicPesticide = findViewById<TextView>(R.id.tvOrganicPesticide)
        val tvChemicalPesticide = findViewById<TextView>(R.id.tvChemicalPesticide)
        val btnSpeak = findViewById<Button>(R.id.btnSpeak)

        tts = TextToSpeech(this, this)

        val cropEnglish = intent.getStringExtra("crop_english").orEmpty()
        val crop = intent.getStringExtra("crop") ?: "No crop"
        val season = intent.getStringExtra("season").orEmpty()
        val soilType = intent.getStringExtra("soil_type").orEmpty()

        ivCrop.setImageResource(CropVisualResources.cropImageRes(cropEnglish))
        tvCrop.text = crop

        tvSeasonSoil.text = buildString {
            append("🌱 Season: ")
            append(season)
            append("\n🌾 Soil type: ")
            append(soilType)
        }

        tvOrganicFertilizer.text = formatBilingual(
            intent.getStringExtra("organic_fertilizer_en"),
            intent.getStringExtra("organic_fertilizer_te")
        )
        tvChemicalFertilizer.text = formatBilingual(
            intent.getStringExtra("chemical_fertilizer_en"),
            intent.getStringExtra("chemical_fertilizer_te")
        )
        tvOrganicPesticide.text = formatBilingual(
            intent.getStringExtra("organic_pesticide_en"),
            intent.getStringExtra("organic_pesticide_te")
        )
        tvChemicalPesticide.text = formatBilingual(
            intent.getStringExtra("chemical_pesticide_en"),
            intent.getStringExtra("chemical_pesticide_te")
        )

        speechText = buildString {
            append("Recommended crop is ")
            append(crop)
            append(". Season ")
            append(season)
            append(". Soil type ")
            append(soilType)
            append(". ")
            append(tvOrganicFertilizer.text)
            append(". ")
            append(tvChemicalFertilizer.text)
            append(". ")
            append(tvOrganicPesticide.text)
            append(". ")
            append(tvChemicalPesticide.text)
        }

        btnSpeak.setOnClickListener {
            tts.speak(speechText, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun formatBilingual(en: String?, te: String?): String {
        val a = en?.trim().orEmpty()
        val b = te?.trim().orEmpty()
        return when {
            a.isNotEmpty() && b.isNotEmpty() -> "$a\n$b"
            a.isNotEmpty() -> a
            b.isNotEmpty() -> b
            else -> "—"
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
