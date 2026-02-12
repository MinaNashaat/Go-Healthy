package com.example.gohealthy.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class GroqVM : ViewModel() {

  private val client = OkHttpClient()
  private val apiKey = "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"

  suspend fun getResponse(message: String): String {

    if (!isAllowedTopic(message)) {
      return "I can only help with topics related to health and fitness such as:\n" +
        "- health\n" +
        "- healthy habits\n" +
        "- diet\n" +
        "- weight\n" +
        "- exercise / workout\n" +
        "- fitness\n" +
        "- gym\n" +
        "- calories\n" +
        "- food / meals\n" +
        "- protein / carbs / fat\n" +
        "- run / walk\n" +
        "- steps and activity tracking\n\n" +
        "Please ask something in these areas 😊"
    }

    return withContext(Dispatchers.IO) {
      try {

        val json = """
                {
                  "model": "llama-3.1-8b-instant",
                  "messages": [
                    {"role": "system", "content": "You are a health and nutrition assistant. Only answer questions about fitness, health, diet, calories, exercise."},
                    {"role": "user", "content": "$message"}
                  ],
                  "max_tokens": 150
                }
                """.trimIndent()

        val body = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
          .url("https://api.groq.com/openai/v1/chat/completions")
          .addHeader("Authorization", "Bearer $apiKey")
          .addHeader("Content-Type", "application/json")
          .post(body)
          .build()

        val response = client.newCall(request).execute()
        val responseText = response.body?.string() ?: return@withContext "Error"

        val obj = JSONObject(responseText)

        return@withContext obj
          .getJSONArray("choices")
          .getJSONObject(0)
          .getJSONObject("message")
          .getString("content")

      } catch (e: Exception) {
        e.printStackTrace()
        return@withContext "AI error: ${e.message}"
      }
    }
  }

  private fun isAllowedTopic(text: String): Boolean {
    val keywords = listOf(
      "health","healthy","diet","weight","exercise","fitness",
      "gym","calories","food","meal","protein","carbs",
      "fat","training","workout","steps", "run", "walk"
    )
    return keywords.any { text.lowercase().contains(it) }
  }
}
