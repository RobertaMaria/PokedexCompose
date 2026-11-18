package com.example.pokedex.details.data.translator

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface PokemonTranslator {
    suspend fun translate(text: String): String
}

class PokemonTranslatorImpl : PokemonTranslator {

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.PORTUGUESE)
        .build()

    private val translator: Translator by lazy { Translation.getClient(options) }

    override suspend fun translate(text: String): String {
        val cleanText = text
            .replace("\n", " ")
            .replace("\u000C", " ")
            .replace("\r", " ")
            .trim()

        if (cleanText.isBlank()) return cleanText

        return try {
            val downloadSuccess = downloadModelIfNeeded()
            if (downloadSuccess) {
                translateText(cleanText)
            } else {
                cleanText
            }
        } catch (_: Exception) {
            cleanText
        }
    }

    private suspend fun downloadModelIfNeeded(): Boolean = suspendCancellableCoroutine { continuation ->
        val conditions = DownloadConditions.Builder().build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                if (continuation.isActive) continuation.resume(value = true)
            }
            .addOnFailureListener {
                if (continuation.isActive) continuation.resume(value = false)
            }
    }

    private suspend fun translateText(text: String): String = suspendCancellableCoroutine { continuation ->
        translator.translate(text)
            .addOnSuccessListener { translatedText ->
                if (continuation.isActive) continuation.resume(value = translatedText)
            }
            .addOnFailureListener {
                if (continuation.isActive) continuation.resume(value = text)
            }
    }
}
