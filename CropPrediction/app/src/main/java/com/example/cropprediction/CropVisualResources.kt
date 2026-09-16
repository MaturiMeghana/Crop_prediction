package com.example.cropprediction

import androidx.annotation.DrawableRes

/**
 * Maps API crop names (English) to illustration drawables for the result screen.
 */
object CropVisualResources {

    @DrawableRes
    fun cropImageRes(cropEnglish: String): Int {
        val key = cropEnglish.trim().lowercase()
        return when {
            key.contains("rice") -> R.drawable.ic_crop_rice
            key.contains("wheat") -> R.drawable.ic_crop_wheat
            key.contains("maize") || key.contains("corn") || key.contains("jowar") ||
                key.contains("sorghum") || key.contains("bajra") || key.contains("barley") -> R.drawable.ic_crop_maize
            key.contains("chickpea") || key.contains("gram") ||
                key.contains("lentil") || key.contains("bean") ||
                key.contains("pulses") || key.contains("pea") ||
                key.contains("mung") || key.contains("moth") ||
                key.contains("kidney") || key.contains("pigeon") -> R.drawable.ic_crop_legume
            key.contains("cotton") || key.contains("jute") ||
                key.contains("coffee") -> R.drawable.ic_crop_fiber
            key.contains("mango") || key.contains("banana") ||
                key.contains("grape") || key.contains("orange") ||
                key.contains("apple") || key.contains("papaya") ||
                key.contains("pomegranate") || key.contains("coconut") ||
                key.contains("watermelon") || key.contains("muskmelon") -> R.drawable.ic_crop_fruit
            else -> R.drawable.ic_crop_default
        }
    }
}
