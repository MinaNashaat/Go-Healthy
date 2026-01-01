package com.example.gohealthy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatVM : ViewModel() {

  val messages = MutableLiveData<MutableList<Pair<String, Boolean>>>()

  init {
    messages.value = mutableListOf(
      Pair("Hi! I'm here to help you achieve your fitness goals! 💪✨ Feel free to ask anything about fitness!", false)
    )
  }

  fun addUserMessage(text: String) {
    messages.value?.add(Pair(text, true))
    messages.postValue(messages.value)
  }

  fun addBotMessage(text: String) {
    messages.value?.add(Pair(text, false))
    messages.postValue(messages.value)
  }
}
