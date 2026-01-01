package com.example.gohealthy.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gohealthy.databinding.FragmentChatBinding
import com.example.gohealthy.viewModel.ChatVM
import com.example.gohealthy.viewModel.GroqVM
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private lateinit var chatAdapter: ChatAdapter
  private val chatVM: ChatVM by activityViewModels()
    private lateinit var binding: FragmentChatBinding

  private val groqVM: GroqVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


      binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)

      chatAdapter = ChatAdapter(chatVM.messages.value!!)
      binding.chatRecyclerView.adapter = chatAdapter

      chatVM.messages.observe(viewLifecycleOwner) {
        chatAdapter.notifyDataSetChanged()
        binding.chatRecyclerView.scrollToPosition(it.size - 1)
      }
        binding.chatRecyclerView.adapter = chatAdapter

       binding.inputMessage.requestFocus()


        binding.sendButton.setOnClickListener {
            val message = binding.inputMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.inputMessage.text?.clear()
            } else {
                Toast.makeText(context, "Please type a message", Toast.LENGTH_SHORT).show()
            }
        }
    }



  private fun sendMessage(message: String) {

    chatVM.addUserMessage(message)

    lifecycleScope.launch {
      val reply = groqVM.getResponse(message)
      chatVM.addBotMessage(reply)
    }
  }

}
