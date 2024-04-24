package com.traslator

data class TranslateState(
    val texToTranslate : String = "",
    val translateText : String = "",
    val isDownloading : Boolean = false
)
